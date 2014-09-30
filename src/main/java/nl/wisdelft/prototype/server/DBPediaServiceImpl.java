package nl.wisdelft.prototype.server;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.DBPediaService;
import nl.wisdelft.prototype.client.shared.Property;
import nl.wisdelft.prototype.client.shared.RDFResource;

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
public class DBPediaServiceImpl implements DBPediaService {

	@Inject
	Logger logger;

	final String ENDPOINT = "http://dbpedia.org/sparql";

	public RDFResource getDBPediaResource(String resource) {
		//@formatter:off
		String sparql = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "SELECT ?label ?wikiurl ?abstract ?thumbnail "
				+ "WHERE {"
				+ "  <%1$s> rdfs:label ?label ;"
				+ "    dbpedia-owl:abstract ?abstract ;"
				+ "    dbpedia-owl:thumbnail ?thumbnail ."
				+ "  ?wikiurl foaf:primaryTopic <%1$s> ."
				+ "FILTER langMatches( lang(?label), \"en\" ) "
				+ "FILTER langMatches(lang(?abstract), \"en\")"
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);

		ResultSet results = qexec.execSelect();
		QuerySolution solution = results.next();
		if (solution == null)
			return null;

		String label = solution.getLiteral("label").getString();
		String wikiurl = solution.getResource("wikiurl").getURI();
		String summary = solution.getLiteral("abstract").getString();
		String thumbnail = solution.getResource("thumbnail").getURI();

		RDFResource page = new RDFResource(resource);
		page.setLabel(label);
		page.setWikipediaUrl(wikiurl);
		page.setSummary(summary);
		qexec.close();
		page.setTypes(getTypes(resource));
		page.setProperties(getProperties(resource));
		page.setThumbnail(thumbnail);
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
				+ "SELECT DISTINCT ?property ?label ?value WHERE {"
				+ "	<%s> ?property ?value ."
				+ " ?property rdfs:label ?label"
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
			RDFNode valueNode = solution.get("value");
			String value = null;
			if (valueNode.isURIResource()) {
				value = valueNode.asResource().getURI();
			} else if (valueNode.isLiteral()) {
				value = valueNode.asLiteral().toString();
			}
			properties.add(new Property(r, value, l));
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
				+ "SELECT DISTINCT ?p ?z ?v WHERE {"
				+ "	?x dcterms:subject ?type ."
				+ "	?x ?p ?v . "
				+ " ?p rdfs:label ?z"
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
			RDFNode valueNode = solution.get("v");
			String value = null;
			if (valueNode.isURIResource()) {
				value = valueNode.asResource().getURI();
			} else if (valueNode.isLiteral()) {
				value = valueNode.asLiteral().toString();
			}
			properties.add(new Property(resource, value, label));
		}
		qexec.close();
		return properties;
	}

	private List<Property> getTypes(String resource) {
		List<Property> types = new ArrayList<Property>();
		//@formatter:off
		String sparql = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX dbpedia-owl: 	<http://dbpedia.org/ontology/> "
				+ "PREFIX dcterms: <http://purl.org/dc/terms/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "SELECT DISTINCT ?resource ?label WHERE {"
				+ "	<%1$s> dcterms:subject ?resource ."
				+ "	?resource rdfs:label ?label"
				+ "	FILTER langMatches(lang(?label), \"en\") ."
				//+ " FILTER (?property IN (dcterms:subject, dbpedia-owl:type, rdf:type)"
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
			String l = solution.getLiteral("label").getString();
			String p = "http://purl.org/dc/terms/subject";
			types.add(new Property(p, r, l));
		}
		qexec.close();
		return types;
	}
}