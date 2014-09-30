/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 */
@Templated("SelectTypes.html#app-template")
public class SelectTypesWidget extends Composite implements StepWidget {
	@Inject
	EntityManager em;

	@Inject
	@AutoBound
	DataBinder<CurationConfiguration> config;

	@Inject
	private CurationConfiguration c;

	@Inject
	@DataField
	ListBox typeAll;

	@Inject
	@DataField
	ListBox typeSelected;

	@Inject
	@DataField
	Button btnLeftType;

	@Inject
	@DataField
	Button btnRightType;

	@Inject
	Event<CurationConfiguration> configChanged;

	@PostConstruct
	private void loadData() {
		config.setModel(c);
		for (Property p : c.getSelectedTypes()) {
			typeSelected.addItem(p.getLabel());
		}
		for (Property p : c.getPossibleTypes()) {
			if (!c.getSelectedTypes().contains(p)) {
				typeAll.addItem(p.getLabel());
			}
		}
	}

	@EventHandler("btnRightType")
	private void moveRight(ClickEvent e) {
		moveSelected(typeAll, typeSelected);
		updateConfiguration();
	}

	@EventHandler("btnLeftType")
	private void moveLeft(ClickEvent e) {
		moveSelected(typeSelected, typeAll);
		updateConfiguration();
	}

	private void updateConfiguration() {
		List<Property> selectedTypes = new ArrayList<Property>();
		for (int i = 0; i < typeSelected.getItemCount(); i++) {
			selectedTypes.add(c.getTypeWithLabel(typeSelected.getItemText(i)));
		}
		// Update the sessionscoped object
		c.setSelectedTypes(selectedTypes);
		// remove the possible properties, because that could have changed by
		// changing the types
		c.getPossibleProperties().clear();
		configChanged.fire(c);

	}

	private void moveSelected(ListBox from, ListBox to) {
		int indexSelected;
		while ((indexSelected = from.getSelectedIndex()) != -1) {
			to.addItem(from.getItemText(indexSelected));
			from.removeItem(indexSelected);
		}
	}

}
