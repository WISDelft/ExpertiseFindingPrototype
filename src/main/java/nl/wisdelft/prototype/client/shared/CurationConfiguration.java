/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@ApplicationScoped
public class CurationConfiguration {
	@Id
	private String resourceUrl;

	private String imageUrl;

	private String queryString;

	String title;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> selectedTypes = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> possibleTypes = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> selectedProperties = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> possibleProperties = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> resourceProperties = new ArrayList<Property>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Property> knowledgeModelProperties = new ArrayList<Property>();

	public CurationConfiguration() {
		// TODO Auto-generated constructor stub
	}

	public CurationConfiguration(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setConfigFromPage(RDFResource page) {
		setTitle(page.getLabel());
		setImageUrl(page.getThumbnail());
		setResourceUrl(page.getResourceUrl());
		setPossibleTypes(page.getTypes());
		setResourceProperties(page.getProperties());
		getSelectedProperties().clear();
		getSelectedTypes().clear();
		getKnowledgeModelProperties().clear();
	}

	public Property getTypeWithLabel(String label) {
		if (label == null) {
			return null;
		}
		for (Property type : possibleTypes) {
			if (label.equals(type.getLabel())) {
				return type;
			}
		}
		return null;
	}

	public Property getTypeWithKey(String key) {
		if (key == null) {
			return null;
		}
		for (Property type : possibleTypes) {
			if (key.equals(type.getKey())) {
				return type;
			}
		}
		return null;
	}

	public Property getPropertyWithLabel(String label) {
		if (label == null) {
			return null;
		}
		for (Property type : possibleProperties) {
			if (label.equals(type.getLabel())) {
				return type;
			}
		}
		return null;
	}

	public Property getPropertyWithKey(String key) {
		if (key == null) {
			return null;
		}
		for (Property type : possibleProperties) {
			if (key.equals(type.getKey())) {
				return type;
			}
		}
		return null;
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

	public List<Property> getResourceProperties() {
		return resourceProperties;
	}

	public void setResourceProperties(List<Property> resourceProperties) {
		this.resourceProperties = resourceProperties;
	}

	public List<Property> getKnowledgeModelProperties() {
		return knowledgeModelProperties;
	}

	public void setKnowledgeModelProperties(List<Property> knowledgeModelProperties) {
		this.knowledgeModelProperties = knowledgeModelProperties;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
