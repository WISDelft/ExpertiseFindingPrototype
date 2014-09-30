/**
 * 
 */
package nl.wisdelft.prototype.client.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

/**
 * @author oosterman
 */
@Bindable
@Portable
@Entity
public class Property {
	private String key;
	private String value;
	private String label;
	private String comment;
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Property() {
	}

	public Property(String value) {
		this.value = value;
		this.key = this.value;
	}

	public Property(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Property(String key, String value, String label) {
		this(key, value);
		this.label = label;
	}

	public Property(String key, String value, String label, String comment, String description) {
		this(key, value, label);
		this.comment = comment;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (other.id != null && other.id.equals(this.id))
			return true;
		if (this.key == null || other.key == null)
			return false;
		return this.key.equals(other.key)
				&& ((this.value == null && other.value == null) || this.value.equals(other.value));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (key + value + label).hashCode();
		return result;
	}
}
