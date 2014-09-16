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

}
