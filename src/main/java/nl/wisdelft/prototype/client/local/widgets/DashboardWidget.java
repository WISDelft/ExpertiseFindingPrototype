/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author oosterman
 *
 */
@Templated("Dashboard.html#app-template")
public class DashboardWidget extends Composite {

	@Inject
	CurationConfiguration config;

	@PostConstruct
	private void loadData() {

	}
}
