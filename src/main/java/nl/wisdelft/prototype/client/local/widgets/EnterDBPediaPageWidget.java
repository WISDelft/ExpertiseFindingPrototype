package nl.wisdelft.prototype.client.local.widgets;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.DBPediaPage;
import nl.wisdelft.prototype.client.shared.DBPediaService;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

@Templated("EnterDBPediaPage.html")
public class EnterDBPediaPageWidget extends Composite implements StepWidget {

	@Inject
	@AutoBound
	private DataBinder<DBPediaPage> page;

	@Inject
	CurationConfiguration config;

	@Inject
	EntityManager em;

	@Inject
	@DataField
	Button btnFindResource;

	@Inject
	@DataField
	TextBox tbUrl;

	@Inject
	@DataField
	@Bound(property = "label")
	Label pageTitle;

	@Inject
	@DataField
	@Bound(property = "summary")
	Label pageAbstract;

	@Inject
	Logger logger;

	@Inject
	private Caller<DBPediaService> dbpedia;

	@Inject
	private Event<CurationConfiguration> configUpdated;

	private void pageFound(DBPediaPage page) {
		this.page.setModel(page);
		config.setConfigFromPage(page);
		configUpdated.fire(config);
	}

	@EventHandler("btnFindResource")
	public void findResource(ClickEvent e) {
		String url = tbUrl.getText();
		// check if the local storage already contains this resource
		DBPediaPage storedPage = em.find(DBPediaPage.class, url);
		if (storedPage != null) {
			pageFound(storedPage);
		} else {
			// disable the button
			DOM.setElementProperty(btnFindResource.getElement(), "disabled", "true");
			dbpedia.call(new RemoteCallback<DBPediaPage>() {

				@Override
				public void callback(DBPediaPage result) {
					pageFound(result);
					// em.persist(result);
					DOM.removeElementAttribute(btnFindResource.getElement(), "disabled");
				}
			}).getDBPediaPage(url);
		}
	}
}
