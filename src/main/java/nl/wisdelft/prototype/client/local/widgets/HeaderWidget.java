/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.inject.Inject;

import nl.wisdelft.prototype.client.local.pages.SelectResourcePage;

import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

@Templated("header.html")
public class HeaderWidget extends Composite {
	@Inject
	@DataField
	TransitionAnchor<SelectResourcePage> startCuration;
}
