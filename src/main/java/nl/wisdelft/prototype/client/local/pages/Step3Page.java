package nl.wisdelft.prototype.client.local.pages;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import nl.wisdelft.prototype.client.local.widgets.Step3Widget;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Page(path = "step3")
@Templated("CurationContentLayout.html")
@Alternative
public class Step3Page extends CurationContentLayout {
	@Inject
	@DataField("stepContent")
	private Step3Widget step;

	@Inject
	@DataField
	TransitionAnchor<Step2Page> previousStep;

	@Inject
	@DataField
	TransitionAnchor<Step4Page> nextStep;
}
