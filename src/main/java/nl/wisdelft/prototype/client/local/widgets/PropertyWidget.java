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
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author oosterman
 *
 */
@Templated("SummaryWidget.html#property")
public class PropertyWidget extends Composite implements HasModel<Property>{
  @Inject
  @AutoBound
  private DataBinder<Property> property;
  
  @Bound(property="value")
  @DataField(value="property")
  private final Element li = DOM.createElement("li");
  
	@Override
	public Property getModel() {
		return property.getModel();
	}

	@Override
	public void setModel(Property model) {
		property.setModel(model);		
	}

}
