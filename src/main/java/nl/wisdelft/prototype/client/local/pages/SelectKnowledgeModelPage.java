package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.local.widgets.SelectKnowledgeModelWidget;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;

@Page(path = "NeedSpecification")
@Templated("CurationContentLayout.html#app-template")
public class SelectKnowledgeModelPage extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	@DataField("stepContent")
	private SelectKnowledgeModelWidget step;

	@Inject
	@DataField
	TransitionAnchor<SelectPropertiesPage> previousStep;

	@Inject
	@DataField
	TransitionAnchor<SelectContributorsPage> nextStep;

	@EventHandler("nextStep")
	private void nextStep(ClickEvent e) {

	}

}
