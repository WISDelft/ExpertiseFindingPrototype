package nl.wisdelft.prototype.client.shared;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface DBPediaService {
	public DBPediaPage getDBPediaPage(String resource);
}
