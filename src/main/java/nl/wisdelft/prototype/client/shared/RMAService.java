package nl.wisdelft.prototype.client.shared;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface RMAService {
	public List<Property> getIconClassDomains();

	public RDFResource getRMAResource(String resourse);

	public List<Property> getProperties(List<Property> types);
}
