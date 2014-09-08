/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.Property;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 */
@Templated("Step1.html#app-template")
public class Step1Widget extends Composite implements StepWidget {
	@Inject
	EntityManager em;
	
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
	CurationConfiguration config;

	@PostConstruct
	private void loadData() {
		typeAll.addItem("test");
		typeAll.addItem("test2");
		typeAll.addItem("test3");
	}

	@EventHandler("btnRightType")
	private void moveRight(ClickEvent e) {
		moveSelected(typeAll, typeSelected);
		List<Property> selectedItems = new ArrayList<Property>();
		for (int i = 0; i < typeSelected.getItemCount(); i++) {
			selectedItems.add(new Property(typeSelected.getItemText(i)));
		}
		config.setSelectedTypes(selectedItems);
		GWT.log("Config ID: "+config.getId());
		em.persist(config);
		em.flush();
		
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
