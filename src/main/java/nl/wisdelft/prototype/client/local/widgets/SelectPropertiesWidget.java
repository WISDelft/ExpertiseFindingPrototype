/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.DBPediaService;
import nl.wisdelft.prototype.client.shared.Property;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 *
 */
@Templated("SelectProperties.html#app-template")
public class SelectPropertiesWidget extends Composite implements StepWidget {

	@Inject
	CurationConfiguration config;

	@Inject
	private Caller<DBPediaService> dbpedia;

	@Inject
	Logger logger;

	@Inject
	@DataField
	ListBox all;

	@Inject
	@DataField
	ListBox selected;

	@Inject
	@DataField
	Button btnLeft;

	@Inject
	@DataField
	Button btnRight;

	@Inject
	Event<CurationConfiguration> configChanged;

	@PostConstruct
	public void loadData() {
		// check if the properties already exist in storage
		if (config.getPossibleProperties().size() > 0) {
			setData();
			return;
		}
		// Needed because Errai encapsulates lists that are used in databinding
		List<Property> types = new ArrayList<Property>();
		for (Property type : config.getSelectedTypes()) {
			types.add(type);
		}
		dbpedia.call(new RemoteCallback<List<Property>>() {
			@Override
			public void callback(List<Property> response) {
				logger.info("We found " + response.size() + " properties for similar resources");
				config.setPossibleProperties(response);
				setData();
			}
		}).getProperties(types);

	}

	private void setData() {
		List<Property> unmatched = new ArrayList<Property>();
		for (Property p : config.getPossibleProperties()) {
			if (!config.getResourceProperties().contains(p)) {
				unmatched.add(p);
			}
		}
		// clear the list
		all.clear();
		// add the unmatched properties
		for (Property p : unmatched) {
			all.addItem(p.getKey());
		}
	}

	@EventHandler("btnRight")
	private void moveRight(ClickEvent e) {
		moveSelected(all, selected);
		updateConfiguration();
	}

	@EventHandler("btnLeft")
	private void moveLeft(ClickEvent e) {
		moveSelected(selected, all);
		updateConfiguration();
	}

	private void moveSelected(ListBox from, ListBox to) {
		int indexSelected;
		while ((indexSelected = from.getSelectedIndex()) != -1) {
			to.addItem(from.getItemText(indexSelected));
			from.removeItem(indexSelected);
		}
	}

	private void updateConfiguration() {
		List<Property> selectedProperties = new ArrayList<Property>();
		for (int i = 0; i < selected.getItemCount(); i++) {
			selectedProperties.add(config.getPropertyWithKey(selected.getItemText(i)));
		}
		// Update the sessionscoped object
		config.setSelectedProperties(selectedProperties);
		configChanged.fire(config);
	}
}