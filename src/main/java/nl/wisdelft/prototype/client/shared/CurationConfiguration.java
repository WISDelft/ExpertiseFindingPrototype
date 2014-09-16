/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

/**
 * @author oosterman
 */
@NamedQuery(name = "config", query = "SELECT c FROM CurationConfiguration c")
@Bindable
@Portable
@Entity
@Singleton
public class CurationConfiguration {
	@Id
	private String resourceUrl;

	String title;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> selectedTypes = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> possibleTypes = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> selectedProperties = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Property> possibleProperties = new ArrayList<Property>();

	public CurationConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public CurationConfiguration(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setConfigFromPage(DBPediaPage page) {
		setTitle(page.getLabel());
		setResourceUrl(page.getResourceUrl());
		setPossibleTypes(page.getTypes());
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
		CurationConfiguration other = (CurationConfiguration) obj;
		if (resourceUrl == null)
			return false;
		return resourceUrl.equals(other.resourceUrl);
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public List<Property> getSelectedTypes() {
		return selectedTypes;
	}

	public void setSelectedTypes(List<Property> selectedTypes) {
		this.selectedTypes = selectedTypes;
	}

	public List<Property> getSelectedProperties() {
		return selectedProperties;
	}

	public void setSelectedProperties(List<Property> selectedProperties) {
		this.selectedProperties = selectedProperties;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Property> getPossibleTypes() {
		return possibleTypes;
	}

	public void setPossibleTypes(List<Property> possibleTypes) {
		this.possibleTypes = possibleTypes;
	}

	public List<Property> getPossibleProperties() {
		return possibleProperties;
	}

	public void setPossibleProperties(List<Property> possibleProperties) {
		this.possibleProperties = possibleProperties;
	}

}
