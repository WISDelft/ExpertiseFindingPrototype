/**
 * 
 */
package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;
import nl.wisdelft.prototype.client.local.widgets.FooterWidget;
import nl.wisdelft.prototype.client.local.widgets.HeaderWidget;
import nl.wisdelft.prototype.client.local.widgets.StepWidget;
import nl.wisdelft.prototype.client.local.widgets.SummaryWidget;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author oosterman
 */
@Templated("CurationContentLayout.html#app-template")
public class CurationContentLayout extends Composite {
	@Inject
	@DataField
	private HeaderWidget header;

	@Inject
	@DataField
	private FooterWidget footer;

	@Inject
	@DataField
	private TransitionAnchor<Step1Page> step1;

	@Inject
	@DataField
	private TransitionAnchor<Step2Page> step2;

	@Inject
	@DataField
	private TransitionAnchor<Step3Page> step3;

	@Inject
	@DataField
	private TransitionAnchor<Step4Page> step4;

	@Inject
	@DataField("contentRight")
	private SummaryWidget summary;


}
