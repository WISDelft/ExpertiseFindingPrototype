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

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author oosterman
 *
 */
@Templated("SelectContributors.html#app-template")
public class SelectContributorsWidget extends Composite implements StepWidget {

	private String[] platforms = new String[] { "Reddit", "Twitter" };
	private String[] extractionStrategies = new String[] { "Subreddit First", "User First" };
	private String[] identificationStrategies = new String[] { "Term occurence", "Plants domain matching",
			"Ornithology domain matching" };

	@Inject
	@DataField
	private ListBox ddlPlatform, ddlIdentificationStrategy, ddlExtractionStrategy;

	@Inject
	Logger logger;

	@DataField
	Element propertyList = DOM.createDiv();

	@DataField
	Element relatedPropertyList = DOM.createDiv();

	@DataField
	Element kbPropertyList = DOM.createDiv();

	@Inject
	@DataField
	InlineLabel lblQuery;

	@Inject
	@DataField
	Button btnSearch, btnAddTerms, btnItemStrategy, btnKBStrategy, btnRelatedItemStrategy;

	@Inject
	@DataField
	PropertyListWidget pnlProperties;

	@Inject
	@DataField
	ListBox lbKnowledgeBase;

	@DataField
	Element pnlContributors1 = DOM.createDiv();

	@DataField
	Element pnlContributors2 = DOM.createDiv();

	@DataField
	Element pnlContributors3 = DOM.createDiv();

	@DataField
	Element pnlContributors4 = DOM.createDiv();

	@Inject
	Event<CurationConfiguration> configChanged;

	@Inject
	CurationConfiguration config;

	@Inject
	Caller<KnowledgeRepoService> repo;

	private JavaScriptObject searchSelectize;
	private JavaScriptObject wordnetSelectize;

	private void setButtonSelection(Button b, boolean select) {
		if (select) {
			b.removeStyleName("btn-default");
			b.addStyleName("btn-primary");
		} else {
			b.removeStyleName("btn-primary");
			b.addStyleName("btn-default");
		}
	}

	private boolean isButtonSelected(Button b) {
		return b.getStyleName().contains("btn-primary");
	}

	private void setElementVisible(Element e, boolean visible) {
		if (visible) {
			e.removeClassName("hidden");
		} else {
			e.addClassName("hidden");
		}
	}

	public void loadWordnet() {
		repo.call(new RemoteCallback<Set<String>>() {

			@Override
			public void callback(Set<String> response) {
				for (String term : response) {
					lbKnowledgeBase.addItem(term);
				}
				wordnetSelectize = enableSelectize("#lbKnowledgeBase", false);
			}
		}).getDomains();
	}

	@EventHandler("btnItemStrategy")
	public void btnItemStrategy_clicked(ClickEvent e) {
		setButtonSelection(btnKBStrategy, false);
		setButtonSelection(btnRelatedItemStrategy, false);
		setButtonSelection(btnItemStrategy, true);
		setElementVisible(kbPropertyList, false);
		setElementVisible(relatedPropertyList, false);
		setElementVisible(propertyList, true);
	}

	@EventHandler("btnKBStrategy")
	public void btnKBStrategy_clicked(ClickEvent e) {
		setButtonSelection(btnKBStrategy, true);
		setButtonSelection(btnRelatedItemStrategy, false);
		setButtonSelection(btnItemStrategy, false);
		setElementVisible(kbPropertyList, true);
		setElementVisible(relatedPropertyList, false);
		setElementVisible(propertyList, false);
	}

	@EventHandler("btnRelatedItemStrategy")
	public void btnRelatedItemStrategy_clicked(ClickEvent e) {
		setButtonSelection(btnKBStrategy, false);
		setButtonSelection(btnRelatedItemStrategy, true);
		setButtonSelection(btnItemStrategy, false);
		setElementVisible(kbPropertyList, false);
		setElementVisible(relatedPropertyList, true);
		setElementVisible(propertyList, false);
	}

	@EventHandler("btnSearch")
	public void btnSearch_clicked(ClickEvent e) {
		DOM.setElementProperty(btnSearch.getElement(), "disabled", "true");
		btnSearch.addStyleName("active");
		Timer t = new Timer() {
			@Override
			public void run() {
				List<String> terms = getValues("#ddlSearch");

				if (terms != null && terms.size() > 0) {
					String query = "";
					for (String term : terms) {
						query += term + ", ";
					}
					query = query.substring(0, query.length() - 2);
					config.setQueryString(query);
					configChanged.fire(config);

					if (query.startsWith("bird")) {
						setElementVisible(pnlContributors1, true);
						setElementVisible(pnlContributors2, false);
						setElementVisible(pnlContributors3, false);
						setElementVisible(pnlContributors4, false);
					} else if (query.contains("Bairei") && ddlPlatform.getSelectedIndex() == 0) {
						setElementVisible(pnlContributors1, false);
						setElementVisible(pnlContributors2, true);
						setElementVisible(pnlContributors3, false);
						setElementVisible(pnlContributors4, false);
						lblQuery.setText(query);
					} else if (query.contains("Bairei") && ddlPlatform.getSelectedIndex() == 1) {
						setElementVisible(pnlContributors1, false);
						setElementVisible(pnlContributors2, false);
						setElementVisible(pnlContributors3, true);
						setElementVisible(pnlContributors4, false);
					} else if (terms.size() > 10) {
						setElementVisible(pnlContributors1, false);
						setElementVisible(pnlContributors2, false);
						setElementVisible(pnlContributors3, false);
						setElementVisible(pnlContributors4, true);
					} else {
						setElementVisible(pnlContributors1, false);
						setElementVisible(pnlContributors2, true);
						setElementVisible(pnlContributors3, false);
						setElementVisible(pnlContributors4, false);
						lblQuery.setText(query);
					}
				}
				DOM.removeElementAttribute(btnSearch.getElement(), "disabled");
				btnSearch.removeStyleName("active");
			}
		};
		t.schedule(1500);

	}

	@EventHandler("btnAddTerms")
	public void btnAddTerms_clicked(ClickEvent e) {
		List<String> values = new ArrayList<String>();
		if (isButtonSelected(btnItemStrategy)) {
			// get the selected values
			values = pnlProperties.getSelectedValues();
			// add the values to the selectize box
			for (String value : values) {
				addOptionAndAdd(value, value);
			}
		} else if (isButtonSelected(btnKBStrategy)) {
			List<String> domains = getValues("#lbKnowledgeBase");
			// get random 10 words for each of the domains
			repo.call(new RemoteCallback<List<String>>() {
				@Override
				public void callback(List<String> response) {
					for (String value : response) {
						addOptionAndAdd(value, value);
					}
					addOptionAndAdd("...", "...");
				}
			}).getRandomDomainWords(domains, 20);
		}

	}

	@PostConstruct
	private void setData() {
		for (String platform : platforms) {
			ddlPlatform.addItem(platform);
		}

		for (String strategy : extractionStrategies) {
			ddlExtractionStrategy.addItem(strategy);
		}

		for (String strategy : identificationStrategies) {
			ddlIdentificationStrategy.addItem(strategy);
		}
		pnlProperties.setItems(config.getResourceProperties());

	}

	public void loadSelectize() {
		searchSelectize = enableSelectize("#ddlSearch", true);
	}

	private native JavaScriptObject enableSelectize(String elem, boolean b_create) /*-{
		var $select = $wnd.jQuery(elem).selectize({
			create : b_create,
			sortField : 'text',
		});
		var selectize = $select[0].selectize;
		return selectize;
	}-*/;

	private List<String> getValues(String element) {
		JsArrayString js = getNativeValues(element);
		List<String> values = new ArrayList<String>();
		for (int i = 0; i < js.length(); i++) {
			values.add(js.get(i));
		}
		return values;
	}

	private native JsArrayString getNativeValues(String elem)/*-{
		return $wnd.jQuery(elem).val();
	}-*/;

	private native void addOption(String t, String v)/*-{
		selectize = this.@nl.wisdelft.prototype.client.local.widgets.SelectContributorsWidget::searchSelectize;
		selectize.addOption({
			text : t,
			value : v
		});
		selectize.refreshOptions();
	}-*/;

	private native void addOptionAndAdd(String t, String v)/*-{
		selectize = this.@nl.wisdelft.prototype.client.local.widgets.SelectContributorsWidget::searchSelectize;
		obj = {
			text : t,
			value : v
		}
		selectize.addOption(obj);
		selectize.refreshOptions();
		var option = selectize.getOption(v);
		selectize.addItem(v);
	}-*/;

}
