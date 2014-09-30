package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.local.widgets.DashboardWidget;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

@Page(path = "dashboard")
@Templated("SingleContentLayout.html#app-template")
public class DashboardPage extends SingleContentLayout {
	@Inject
	@DataField
	private DashboardWidget content;

}
