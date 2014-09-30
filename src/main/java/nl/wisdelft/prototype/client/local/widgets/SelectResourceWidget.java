package nl.wisdelft.prototype.client.local.widgets;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import nl.wisdelft.prototype.client.shared.DBPediaService;
import nl.wisdelft.prototype.client.shared.KnowledgeRepoService;
import nl.wisdelft.prototype.client.shared.RDFResource;
import nl.wisdelft.prototype.client.shared.RMAService;

import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

@Templated("SelectResource.html#app-template")
public class SelectResourceWidget extends Composite implements StepWidget {

	@Inject
	@AutoBound
	private DataBinder<RDFResource> page;

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
	@DataField
	Image imgResource;

	@Inject
	@DataField
	Anchor lnkExample1, lnkExample2, lnkExample3, lnkExample4;

	@Inject
	Logger logger;

	@Inject
	private Caller<DBPediaService> dbpedia;

	@Inject
	private Caller<KnowledgeRepoService> repo;

	@Inject
	private Caller<RMAService> rma;

	@EventHandler({ "lnkExample1", "lnkExample2", "lnkExample3", "lnkExample4" })
	private void exampleLink_clicked(ClickEvent e) {
		Element lnk = Element.as(e.getNativeEvent().getEventTarget());
		String url = lnk.getPropertyString("href");
		tbUrl.setText(url);
		e.preventDefault();
	}

	// @PostConstruct
	// public void test() {
	//
	// }

	@Inject
	private Event<CurationConfiguration> configUpdated;

	private void pageFound(RDFResource page) {
		this.page.setModel(page);
		imgResource.setUrl(page.getThumbnail());
		config.setConfigFromPage(page);
		configUpdated.fire(config);
		DOM.removeElementAttribute(btnFindResource.getElement(), "disabled");
		btnFindResource.removeStyleName("active");
	}

	@EventHandler("btnFindResource")
	public void findResource(ClickEvent e) {
		// disable the button and set enable searching spinner
		DOM.setElementProperty(btnFindResource.getElement(), "disabled", "true");
		btnFindResource.addStyleName("active");

		String url = tbUrl.getText();
		// check if the local storage already contains this resource
		RDFResource storedPage = em.find(RDFResource.class, url);
		if (storedPage != null) {
			logger.info("Retrieved info from resource " + url + " from storage.");
			pageFound(storedPage);
		} else {
			if (url.startsWith("http://dbpedia.org")) {
				dbpedia.call(new RemoteCallback<RDFResource>() {
					@Override
					public void callback(RDFResource result) {
						em.persist(result);
						em.flush();
						pageFound(result);
					}
				}).getDBPediaResource(url);
			} else if (url.startsWith("http://purl.org/collections/nl/rma/")) {
				rma.call(new RemoteCallback<RDFResource>() {

					@Override
					public void callback(RDFResource result) {
						em.persist(result);
						em.flush();
						pageFound(result);
					}
				}).getRMAResource(url);
			}
		}
	}
}
