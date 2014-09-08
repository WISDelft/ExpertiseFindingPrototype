/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import nl.wisdelft.prototype.client.shared.Property;
import org.jboss.errai.ui.client.widget.ListWidget;

/**
 * @author oosterman
 *
 */
public class PropertyListWidget extends ListWidget<Property, PropertyWidget > {

	@Override
	protected Class<PropertyWidget> getItemWidgetType() {
		return PropertyWidget.class;
	}

}
