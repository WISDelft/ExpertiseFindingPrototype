package nl.wisdelft.prototype.client.local.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.local.widgets.SelectResourceWidget;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;

@Page(role = DefaultPage.class, path = "SelectResource")
@Templated("CurationContentLayout.html#app-template")
@Default
public class SelectResourcePage extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	CurationConfiguration configuration;

	@Inject
	@DataField("stepContent")
	private SelectResourceWidget step;

	@Inject
	@DataField
	TransitionAnchor<SelectResourcePage> previousStep;

	@Inject
	@DataField
	TransitionAnchor<SelectContributorsPage> nextStep;

	@PostConstruct
	public void onLoad() {
		previousStep.setEnabled(false);
	}

	@EventHandler("nextStep")
	private void nextStep(ClickEvent e) {

	}

}
