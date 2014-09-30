/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

/**
 * @author oosterman
 */
@Bindable
@Portable
@Entity
public class RDFResource {
	@Id
	private String resourceUrl;
	private String label;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> types = new ArrayList<Property>();
	private String wikipediaUrl;
	private String summary;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> properties = new ArrayList<Property>();
	private String thumbnail;

	/**
	 * Needed for ORM
	 */
	public RDFResource() {

	}

	public RDFResource(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getWikipediaUrl() {
		return wikipediaUrl;
	}

	public void setWikipediaUrl(String wikipediaUrl) {
		this.wikipediaUrl = wikipediaUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Property> getTypes() {
		return types;
	}

	public void setTypes(List<Property> types) {
		this.types = types;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RDFResource other = (RDFResource) obj;
		if (resourceUrl == null)
			return false;
		return resourceUrl.equals(other.resourceUrl);
	}

}
