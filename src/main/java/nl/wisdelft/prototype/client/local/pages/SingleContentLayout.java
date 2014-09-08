/**
 * 
 */
package nl.wisdelft.prototype.client.local.pages;

import javax.inject.Inject;
import nl.wisdelft.prototype.client.local.widgets.FooterWidget;
import nl.wisdelft.prototype.client.local.widgets.HeaderWidget;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author oosterman
 */
@Templated("SingleContentLayout.html#app-template")
public class SingleContentLayout extends Composite {
	@Inject
	@DataField
	private HeaderWidget header;

	@Inject
	@DataField
	private FooterWidget footer;
}
 