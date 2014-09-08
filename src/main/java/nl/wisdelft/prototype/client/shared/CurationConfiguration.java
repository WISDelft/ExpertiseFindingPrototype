/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	private String pageName;
	private String pageURL;
	
	@OneToMany(cascade=CascadeType.ALL)	
	private List<Property> selectedTypes = new ArrayList<Property>();
	
	@OneToMany(cascade=CascadeType.ALL)	
	private List<Property> selectedProperties = new ArrayList<Property>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public String getPageName() {
		
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageURL() {
		return pageURL;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CurationConfiguration other = (CurationConfiguration) obj;
		if (id == null) {
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public List<Property> getSelectedTypes() {
		return selectedTypes;
	}

	public void setSelectedTypes(List<Property> selectedTypes) {
		this.selectedTypes = selectedTypes;
	}

}
