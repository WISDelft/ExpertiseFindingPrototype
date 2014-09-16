/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 */
@Templated("Step1.html#app-template")
public class Step1Widget extends Composite implements StepWidget {
	@Inject
	EntityManager em;

	@Inject
	@AutoBound
	DataBinder<CurationConfiguration> config;

	@Inject
	private CurationConfiguration c;

	@Inject
	@Bound(property = "title")
	@DataField
	Label pageTitle;

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

	@AfterInitialization
	private void loadData() {
		config.setModel(c);
		for (Property p : config.getModel().getPossibleTypes()) {
			typeAll.addItem(p.getKey());
		}
	}

	@EventHandler("btnRightType")
	private void moveRight(ClickEvent e) {
		moveSelected(typeAll, typeSelected);
		List<Property> selectedTypes = new ArrayList<Property>();
		for (int i = 0; i < typeSelected.getItemCount(); i++) {
			selectedTypes.add(new Property(typeSelected.getItemText(i)));
		}
		config.getModel().setSelectedTypes(selectedTypes);

	}

	@EventHandler("btnLeftType")
	private void moveLeft(ClickEvent e) {
		moveSelected(typeSelected, typeAll);
	}

	private void moveSelected(ListBox from, ListBox to) {
		int indexSelected;
		while ((indexSelected = from.getSelectedIndex()) != -1) {
			to.addItem(from.getItemText(indexSelected));
			from.removeItem(indexSelected);
		}
	}

}
