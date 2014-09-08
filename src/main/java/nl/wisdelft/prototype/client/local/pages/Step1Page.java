package nl.wisdelft.prototype.client.local.pages;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import nl.wisdelft.prototype.client.local.widgets.Step1Widget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.event.dom.client.ClickEvent;

@Page(path = "step1")
@Templated("CurationContentLayout.html#app-template")
@Default
public class Step1Page extends CurationContentLayout {
	@Inject
	EntityManager em;

	@Inject
	@DataField("stepContent")
	private Step1Widget step;
	
	@Inject
	@DataField
	TransitionAnchor<Step1Page> previousStep;

	@Inject
	@DataField
	TransitionAnchor<Step2Page> nextStep;
	
	@EventHandler("nextStep")
	private void nextStep(ClickEvent e){
		
	}
	
		
}
