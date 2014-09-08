package nl.wisdelft.prototype.client.local.pages;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import nl.wisdelft.prototype.client.local.widgets.Step2Widget;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;

@Page(path = "step2")
@Templated("CurationContentLayout.html")
@Alternative
public class Step2Page extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	@DataField("stepContent")
	private Step2Widget step;

	@Inject
	private CurationConfiguration configuration;

	@Inject
	@DataField
	TransitionAnchor<Step1Page> previousStep;

	@Inject
	@DataField
	TransitionAnchor<Step3Page> nextStep;
	
	@EventHandler("nextStep")
	private void next(ClickEvent e){
		configuration.setPageName("test");
		GWT.log("ConfigID: " +configuration.getId());
		em.flush();
	}
}
