/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.shared.CurationConfiguration;

import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.slf4j.Logger;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
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
	@DataField
	@Bound(property = "title")
	private Anchor resource;

	@Inject
	@DataField
	private Label lblQuery;

	@Inject
	@DataField
	private Image imgResource;

	@PostConstruct
	public void loadData() {
		config.setModel(c);
		updateSummary();
	}

	public void configUpdated(@Observes CurationConfiguration config) {
		this.config.setModel(config);
		updateSummary();
	}

	public void updateSummary() {
		resource.setHref(c.getResourceUrl());
		lblQuery.setText(c.getQueryString());
		if (c.getImageUrl() != null) {
			imgResource.setUrl(c.getImageUrl());
		}
	}
}