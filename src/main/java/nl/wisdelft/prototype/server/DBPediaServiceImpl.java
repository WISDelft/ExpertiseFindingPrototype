package nl.wisdelft.prototype.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.DBPediaPage;
import nl.wisdelft.prototype.client.shared.DBPediaService;
import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.bus.server.annotations.Service;
import org.slf4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Service
public class DBPediaServiceImpl implements DBPediaService {

	@Inject
	Logger logger;

	public DBPediaPage getDBPediaPage(String resource) {
		//@formatter:off
		String sparql = "PREFIX dbont: <http://dbpedia.org/ontology/> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "SELECT ?label ?wikiurl ?abstract "
				+ "WHERE {"
				+ "  <%1$s> rdfs:label ?label ;"
				+ "    dbont:abstract ?abstract ."
				+ "  ?wikiurl foaf:primaryTopic <%1$s> ."
				+ "FILTER langMatches( lang(?label), \"en\" ) "
				+ "FILTER langMatches(lang(?abstract), \"en\")"
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

		ResultSet results = qexec.execSelect();
		QuerySolution solution = results.next();
		if (solution == null)
			return null;

		String label = solution.getLiteral("label").getString();
		String wikiurl = solution.getResource("wikiurl").getURI();
		String summary = solution.getLiteral("abstract").getString();

		DBPediaPage page = new DBPediaPage(resource);
		page.setLabel(label);
		page.setWikipediaUrl(wikiurl);
		page.setSummary(summary);
		qexec.close();
		page.setTypes(getTypes(resource));

		return page;

	}

	private Set<String> getSubjects(String resource) {
		//@formatter:off
		String sparql = "PREFIX dbont: <http://dbpedia.org/ontology/> "
				+ "SELECT ?label ?wikiurl ?abstract "
				+ "WHERE {"
				+ "  <http://dbpedia.org/resource/Delft_University_of_Technology> rdfs:label ?label ;"
				+ "    dbont:abstract ?abstract ."
				+ "  ?wikiurl foaf:primaryTopic <http://dbpedia.org/resource/Delft_University_of_Technology> ."
				+ "FILTER langMatches( lang(?label), \"en\" ) "
				+ "FILTER langMatches(lang(?abstract), \"en\")"
				+ "}";
		//@formatter:on
		return null;
	}

	private List<Property> getTypes(String resource) {
		List<Property> types = new ArrayList<Property>();
		//@formatter:off
		String sparql = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX dbpedia-owl: 	<http://dbpedia.org/ontology/> "
				+ "PREFIX dcterms: <http://purl.org/dc/terms/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "SELECT DISTINCT ?resource ?property ?label WHERE {"
				+ "	<http://dbpedia.org/resource/Delft_University_of_Technology> ?property ?resource ."
				+ "	?resource rdfs:label ?label"
				+ "	FILTER langMatches(lang(?label), \"en\") ."
				+ " FILTER (?property IN (dcterms:subject, dbpedia-owl:type, rdf:type))"
				+ "}";
		//@formatter:on
		String q = String.format(sparql, resource);
		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

		ResultSet results = qexec.execSelect();
		QuerySolution solution = null;
		while (results.hasNext()) {
			solution = results.next();
			String r = solution.getResource("resource").getURI();
			String l = solution.getLiteral("label").getString();
			String p = solution.getResource("property").getURI();
			types.add(new Property(p, r, l));
		}

		return types;
	}
}