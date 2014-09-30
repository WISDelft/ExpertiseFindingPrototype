package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import nl.wisdelft.prototype.client.local.widgets.SelectPropertiesWidget;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;

@Page(path = "SelectProperties")
@Templated("CurationContentLayout.html")
public class SelectPropertiesPage extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	@DataField("stepContent")
	private SelectPropertiesWidget step;

	@Inject
	@DataField
	TransitionAnchor<SelectTypesPage> previousStep;

	@Inject
	@DataField
	TransitionAnchor<SelectKnowledgeModelPage> nextStep;

	@EventHandler("nextStep")
	private void next(ClickEvent e) {

	}
}
