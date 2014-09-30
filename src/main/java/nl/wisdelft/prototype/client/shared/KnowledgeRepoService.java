package nl.wisdelft.prototype.client.shared;

import java.util.List;
import java.util.Set;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface KnowledgeRepoService {
	public Set<String> getDomains();

	public Set<String> getDomainWords(String domain);

	public List<String> getRandomDomainWords(List<String> domains, int amountPerDomain);
}
