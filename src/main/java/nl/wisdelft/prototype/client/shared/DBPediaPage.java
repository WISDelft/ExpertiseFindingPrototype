/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;

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
public class DBPediaPage {
	@Id
	private String resourceUrl;
	private String label;
	@OneToMany
	private List<Property> types = new ArrayList<Property>();
	private String wikipediaUrl;
	private String summary;
	@OneToMany
	private List<Property> subjects = new ArrayList<Property>();

	/**
	 * Needed for ORM
	 */
	public DBPediaPage() {

	}

	public DBPediaPage(String resourceUrl) {
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

	public List<Property> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Property> subjects) {
		this.subjects = subjects;
	}

	public void addSubject(Property subject) {
		this.subjects.add(subject);
	}

	public void addType(Property type) {
		this.types.add(type);
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
		DBPediaPage other = (DBPediaPage) obj;
		if (resourceUrl == null)
			return false;
		return resourceUrl.equals(other.resourceUrl);
	}

}
