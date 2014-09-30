/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.KnowledgeRepoService;
import nl.wisdelft.prototype.client.shared.Property;
import nl.wisdelft.prototype.client.shared.RMAService;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 *
 */
@Templated("SelectKnowledgeModel.html#app-template")
public class SelectKnowledgeModelWidget extends Composite implements StepWidget {

	@Inject
	CurationConfiguration config;

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
	@DataField
	ListBox ddlKnowledgeModel;

	@Inject
	@DataField
	Label icDescription;

	@Inject
	@DataField
	Label wnDescription;

	@Inject
	Event<CurationConfiguration> configChanged;
	@Inject
	Caller<KnowledgeRepoService> repo;

	@Inject
	Caller<RMAService> rma;

	Set<String> wnDomains = null;
	List<Property> icDomains = null;

	@PostConstruct
	public void loadData() {
		ddlKnowledgeModel.addItem("Wordnet Domains");
		ddlKnowledgeModel.addItem("IconClass");
		loadDomains();
		toggleDescriptions();
	}

	private void loadDomains() {
		if (wnDomains == null) {
			repo.call(new RemoteCallback<Set<String>>() {

				@Override
				public void callback(Set<String> response) {
					wnDomains = response;
					loadSelectedData();
				}
			}).getDomains();
		}
		// if (icDomains == null) {
		// rma.call(new RemoteCallback<List<Property>>() {
		//
		// @Override
		// public void callback(List<Property> response) {
		// icDomains = response;
		// }
		// }).getIconClassDomains();
		// }
	}

	public native JavaScriptObject enableSelectize() /*-{
		var $select = $wnd.jQuery("#all").selectize({
			create : true,
			sortField : 'text',
		});
		var selectize = $select[0].selectize;
		return selectize;
	}-*/;

	public native void addOption(String text, JavaScriptObject selectize)/*-{
		console.log(selectize);
		selectize.addOption("{text:\"" + text + "\",value:\"" + text + "\"}");
	}-*/;

	public native void refresh(JavaScriptObject selectize)/*-{
		selectize.refreshOptions();
	}-*/;

	private void toggleDescriptions() {
		int selectedIndex = ddlKnowledgeModel.getSelectedIndex();
		wnDescription.setVisible(selectedIndex == 0);
		icDescription.setVisible(!wnDescription.isVisible());
	}

	private void loadSelectedData() {
		all.clear();
		selected.clear();
		int selectedIndex = ddlKnowledgeModel.getSelectedIndex();
		if (selectedIndex == 0) {
			for (String domain : wnDomains) {
				all.addItem(domain);
			}
		}
		// else {
		// for (Property domain : icDomains) {
		// all.addItem(domain.getLabel());
		// }
		// }
	}

	@EventHandler("ddlKnowledgeModel")
	private void ddlKnowledgeModel_selectionChanged(ChangeEvent e) {
		// remove current data from the boxes
		loadSelectedData();
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
			String text = selected.getItemText(i);
			selectedProperties.add(new Property(text, text, text));
		}
		// Update the sessionscoped object
		config.setKnowledgeModelProperties(selectedProperties);
		configChanged.fire(config);
	}
}