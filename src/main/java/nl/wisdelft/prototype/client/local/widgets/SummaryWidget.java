/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * @author oosterman
 */
@Templated("SummaryWidget.html#app-template")
public class SummaryWidget extends Composite {

	@Inject
	@AutoBound
	DataBinder<CurationConfiguration> config;

	@Inject
	CurationConfiguration c;

	@Inject
	Logger logger;

	@Inject
	@Bound(property = "title")
	@DataField
	private Label pageName;

	@Inject
	@Bound(property = "selectedTypes")
	@DataField(value = "summaryProperties")
	private PropertyListWidget summaryProperties;

	@Inject
	@DataField
	Button btnTest;

	@AfterInitialization
	public void init() {
		config.setModel(c);
	}

	public void configUpdated(@Observes CurationConfiguration config) {
		this.config.setModel(config);
	}
}