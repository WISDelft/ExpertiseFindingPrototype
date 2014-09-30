package nl.wisdelft.prototype.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.KnowledgeRepoService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.errai.bus.server.annotations.Service;
import org.slf4j.Logger;

@Service
public class KnowledgeRepoServiceImpl implements KnowledgeRepoService {

	@Inject
	Logger logger;

	final String WORDNET_DOMAINS_FILE = "domain-words.csv";

	static Map<String, Set<String>> domainWords = new HashMap<String, Set<String>>();
	static Set<String> domains = new HashSet<String>();

	private void readDomainWordsFromFile() {
		Reader in;
		try {
			in = new InputStreamReader(KnowledgeRepoServiceImpl.class.getClassLoader().getResourceAsStream(
					WORDNET_DOMAINS_FILE));
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
			for (CSVRecord record : records) {
				if (!domainWords.containsKey(record.get(0))) {
					domainWords.put(record.get(0), new HashSet<String>());
				}
				domainWords.get(record.get(0)).add(record.get(1));
				domains.add(record.get(0));
			}
			// add the demo terms
			Set<String> birdTerms = new HashSet<String>();
			String[] birdWords = new String[] { "water birds", "owl", "predatory birds", "shell", "skull", "feathers",
					"beak", "flying", "young", "chirping", "woodpecker" };
			for (String word : birdWords) {
				birdTerms.add(word);
			}
			domains.add("ornithology");
			domainWords.put("ornithology", birdTerms);

			logger.info("Succesfully read in " + domainWords.size() + " domains");
		} catch (IOException e) {
			logger.error("The Wordnet domain cannot be loaded", e);
		}

	}

	@Override
	public Set<String> getDomains() {
		if (domainWords.size() == 0)
			readDomainWordsFromFile();
		return domains;
	}

	@Override
	public Set<String> getDomainWords(String domain) {
		if (domainWords.size() == 0)
			readDomainWordsFromFile();
		return domainWords.get(domain);
	}

	@Override
	public List<String> getRandomDomainWords(List<String> domains, int amountPerDomain) {
		if (domainWords.size() == 0)
			readDomainWordsFromFile();
		List<String> words = new ArrayList<String>();
		for (String domain : domains) {
			int i = 0;
			List<String> dw = new ArrayList<String>();
			dw.addAll(domainWords.get(domain));
			Collections.shuffle(dw);
			for (String word : dw) {
				if (i == domainWords.get(domain).size() || i == amountPerDomain) {
					break;
				}
				words.add(word);
				i++;
			}
		}
		return words;
	}
}