package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.local.widgets.SelectContributorsWidget;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

@Page(path = "SelectContributors")
@Templated("CurationContentLayout.html")
public class SelectContributorsPage extends CurationContentLayout {
	@Inject
	@DataField("stepContent")
	private SelectContributorsWidget step;

	@Inject
	@DataField
	TransitionAnchor<SelectResourcePage> previousStep;

	@Inject
	@DataField
	TransitionAnchor<DashboardPage> nextStep;

	@Inject
	Logger logger;

	@PageShown
	public void test() {
		// disableElements();
		step.loadSelectize();
		step.loadWordnet();
	}

	public static native void disableElements() /*-{
		$wnd.jQuery("select option:last-child").prop("disabled", "disabled");
	}-*/;
}
