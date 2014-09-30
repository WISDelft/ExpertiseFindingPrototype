/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;

import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.ui.client.widget.ListWidget;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * @author oosterman
 *
 */
public class PropertyListWidget extends ListWidget<Property, PropertyWidget> {

	public PropertyListWidget() {
		super(new HTMLPanel("tbody", ""));
	}

	@Override
	protected Class<PropertyWidget> getItemWidgetType() {
		return PropertyWidget.class;
	}

	public List<String> getSelectedValues() {
		List<String> values = new ArrayList<String>();
		// pass the widgets and get the selected values;

		for (int i = 0; i < this.getValue().size(); i++) {
			PropertyWidget p = this.getWidget(i);
			if (p.isSelected()) {
				String value = p.getModel().getValue();
				values.add(value);
			}
		}
		return values;
	}
}
