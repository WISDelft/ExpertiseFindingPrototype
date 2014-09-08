package nl.wisdelft.prototype.client.local.pages;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import nl.wisdelft.prototype.client.local.widgets.Step4Widget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Page(path = "step4")
@Templated("CurationContentLayout.html")
@Alternative
public class Step4Page extends CurationContentLayout {
	@Inject
	@DataField("stepContent")
	private Step4Widget step;

	@Inject
	@DataField
	TransitionAnchor<Step3Page> previousStep;
	
	@Inject
	@DataField
	TransitionAnchor<Step4Page> nextStep;
}
