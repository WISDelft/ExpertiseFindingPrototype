/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.client.widget.HasModel;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * @author oosterman
 *
 */
@Templated("PropertyWidget.html#property")
public class PropertyWidget extends Composite implements HasModel<Property> {

	@Inject
	@AutoBound
	private DataBinder<Property> property;

	private boolean selected = false;

	@Inject
	@DataField
	Button btnSelected;

	@Inject
	@DataField
	@Bound(property = "label")
	Label lblName;

	@Inject
	@DataField
	@Bound(property = "description")
	Label lblDescription;

	@Inject
	@DataField
	@Bound(property = "value")
	Label lblValue;

	@EventHandler("btnSelected")
	public void btnSelected_clicked(ClickEvent e) {
		selected = !selected;
		if (selected) {
			btnSelected.removeStyleName("btn-default");
			btnSelected.addStyleName("btn-primary");
			btnSelected.setText("Selected");
		} else {
			btnSelected.removeStyleName("btn-primary");
			btnSelected.addStyleName("btn-default");
			btnSelected.setText("Select");
		}
	}

	@Override
	public Property getModel() {
		return property.getModel();
	}

	@Override
	public void setModel(Property model) {
		property.setModel(model);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
