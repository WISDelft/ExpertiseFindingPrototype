package nl.wisdelft.prototype.client.shared;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface DBPediaService {
	public RDFResource getDBPediaResource(String resource);

	public List<Property> getProperties(List<Property> types);
}
