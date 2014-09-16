package nl.wisdelft.prototype.client.local.pages;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.local.widgets.EnterDBPediaPageWidget;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;

@Page(path = "SelectPage")
@Templated("CurationContentLayout.html#app-template")
@Default
public class EnterDBPediaPage extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	CurationConfiguration configuration;

	@Inject
	@DataField("stepContent")
	private EnterDBPediaPageWidget step;

	@Inject
	@DataField
	TransitionAnchor<EnterDBPediaPage> previousStep;

	@Inject
	@DataField
	TransitionAnchor<Step1Page> nextStep;

	@EventHandler("nextStep")
	private void nextStep(ClickEvent e) {

	}

}
