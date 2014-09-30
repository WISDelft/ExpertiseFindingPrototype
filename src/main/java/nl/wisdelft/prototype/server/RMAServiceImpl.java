package nl.wisdelft.prototype.server;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.Property;
import nl.wisdelft.prototype.client.shared.RDFResource;
import nl.wisdelft.prototype.client.shared.RMAService;

import org.jboss.errai.bus.server.annotations.Service;
import org.slf4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

@Service
public class RMAServiceImpl implements RMAService {

	@Inject
	Logger logger;

	final String ENDPOINT = "http://sealinc.ops.few.vu.nl/accurator/sparql/";

	static List<Property> domains = new ArrayList<Property>();

	@Override
	public List<Property> getIconClassDomains() {
		if (domains.size() > 0) {
			return domains;
		}
		//@formatter:off
		String sparql = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
				+ "SELECT ?resource ?label WHERE {"
				+ "	?y skos:broader <http://iconclass.org/ICONCLASS> ."
				+ "	?y skos:narrower / skos:narrower ?resource ."
				+ "	?resource skos:prefLabel ?label ."
				+ "	FILTER langMatches(lang(?label),\"en\")"
				+ "}";
		//@formatter:on
		Query query = QueryFactory.create(sparql);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);
		ResultSet results = qexec.execSelect();
		QuerySolution solution = null;
		while (results.hasNext()) {
			solution = results.next();
			String resource = solution.getResource("resource").getURI();
			String label = solution.getLiteral("label").getString();
			domains.add(new Property(resource, resource, label));
		}
		qexec.close();
		return domains;
	}

	@Override
	public RDFResource getRMAResource(String resource) {

		//@formatter:off
		String sparql = "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX edm: <http://www.europeana.eu/schemas/edm/>"
				+ "SELECT ?title ?description ?image ?site "
				+ "WHERE {"
				+ "		<http://purl.org/collections/nl/rma/RP-P-2004-508A-9> rdf:type edm:ProvidedCHO ;"
				+ "          dc:title ?title ;"
				+ "          dc:description ?description .        "
				+ "		?aggregation edm:aggregatedCHO <http://purl.org/collections/nl/rma/RP-P-2004-508A-9>  ;"
				+ "		     edm:isShownBy ?image ;"
				+ "          edm:isShownAt ?site ."
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);

		ResultSet results = qexec.execSelect();
		RDFResource page = null;
		while (results.hasNext()) {
			page = new RDFResource(resource);
			QuerySolution solution = results.next();
			String label = solution.getLiteral("title").getString();
			String wikiurl = solution.getResource("site").getURI();
			String summary = solution.getLiteral("description").getString();
			String thumbnail = solution.getResource("image").getURI();

			page.setLabel(label);
			page.setWikipediaUrl(wikiurl);
			page.setSummary(summary);
			page.setTypes(getTypes(resource));
			page.setProperties(getProperties(resource));
			page.setThumbnail(thumbnail);
		}
		qexec.close();

		return page;
	}

	private List<Property> getProperties(String resource) {
		List<Property> properties = new ArrayList<Property>();
		if (resource == null)
			return properties;
		//@formatter:off
		String sparql = "PREFIX dcterms: <http://purl.org/dc/terms/>"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
				+ "SELECT DISTINCT ?property ?label ?value ?comment ?description ?valuelabel WHERE {"
				+ "	<%s> ?property ?value . "
				+ " ?property rdfs:label ?label ."
				+ " ?property rdfs:comment ?comment ."
				+ " ?property dcterms:description ?description ."
				+ "OPTIONAL {?value skos:prefLabel ?valuelabel }"
				
				+ " FILTER (langMatches(lang(?label),\"en\"))"
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);
		ResultSet results = qexec.execSelect();
		QuerySolution solution = null;
		while (results.hasNext()) {
			solution = results.next();
			String r = solution.getResource("property").getURI();
			String l = solution.getLiteral("label").getString();
			String c = solution.getLiteral("comment").getString();
			String d = solution.getLiteral("description").getString();
			String value = null;
			if (solution.get("valuelabel") == null) {
				RDFNode valueNode = solution.get("value");
				if (valueNode.isURIResource()) {
					value = valueNode.asResource().getURI();
				} else if (valueNode.isLiteral()) {
					value = valueNode.asLiteral().toString();
				}
			} else {
				value = solution.getLiteral("valuelabel").getString();
			}
			properties.add(new Property(r, value, l, c, d));
		}
		qexec.close();
		return properties;
	}

	public List<Property> getProperties(List<Property> types) {
		List<Property> properties = new ArrayList<Property>();
		if (types == null || types.size() == 0)
			return properties;
		//@formatter:off
		String sparql = "PREFIX dcterms: <http://purl.org/dc/terms/>"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "SELECT DISTINCT ?p ?z ?v ?comment ?description WHERE {"
				+ "	?x dc:type ?type ."
				+ "	?x ?p ?v . "
				+ " ?p rdfs:label ?z ."
				+ " ?p rdfs:comment ?comment ."
				+ " ?p dcterms:description ?description"
				+ "	FILTER (?type IN (%s))"
				//+ "	FILTER (?property IN (dcterms:subject, dbpedia-owl:type))"
				+ " FILTER (langMatches(lang(?z),\"en\"))"
				+ "}";
		//@formatter:on
		String typesFormatted = "";
		for (Property type : types) {
			typesFormatted += "<" + type.getValue() + ">,";
		}
		typesFormatted = typesFormatted.substring(0, typesFormatted.length() - 1);
		String q = String.format(sparql, typesFormatted);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);
		ResultSet results = qexec.execSelect();
		QuerySolution solution = null;
		while (results.hasNext()) {
			solution = results.next();
			String resource = solution.getResource("p").getURI();
			String label = solution.getLiteral("z").getString();
			String comment = solution.getLiteral("comment").getString();
			String description = solution.getLiteral("description").getString();
			RDFNode valueNode = solution.get("v");
			String value = null;
			if (valueNode.isURIResource()) {
				value = valueNode.asResource().getURI();
			} else if (valueNode.isLiteral()) {
				value = valueNode.asLiteral().toString();
			}
			properties.add(new Property(resource, value, label, comment, description));
		}
		qexec.close();
		return properties;
	}

	private List<Property> getTypes(String resource) {
		List<Property> types = new ArrayList<Property>();
		//@formatter:off
		String sparql = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "SELECT DISTINCT ?resource ?label WHERE {"
				+ "	<%1$s> dc:type ?resource . "
				+ "	OPTIONAL { "
				+ "		?resource rdfs:label ?label  ."
				+ "   	FILTER langMatches(lang(?label), \"en\") . "
				+"  }"
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);

		ResultSet results = qexec.execSelect();
		QuerySolution solution = null;
		while (results.hasNext()) {
			solution = results.next();
			String r = solution.getResource("resource").getURI();
			String l = solution.getLiteral("label") == null ? null : solution.getLiteral("label").getString();
			String p = "http://purl.org/dc/elements/1.1/type";
			types.add(new Property(p, r, l));
		}
		qexec.close();
		return types;
	}
}