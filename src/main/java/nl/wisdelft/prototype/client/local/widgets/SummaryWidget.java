/**
 * 
 */
package nl.wisdelft.prototype.client.local.widgets;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import nl.wisdelft.prototype.client.shared.CurationConfiguration;
import org.jboss.errai.databinding.client.api.DataBinder;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ui.client.widget.ListWidget;
import org.jboss.errai.ui.client.widget.UnOrderedList;
import org.jboss.errai.ui.shared.api.annotations.AutoBound;
import org.jboss.errai.ui.shared.api.annotations.Bound;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * @author oosterman
 */
@Templated("SummaryWidget.html#app-template")
@Dependent
public class SummaryWidget extends Composite {

	@Inject
	CurationConfiguration conf;
	
	@Inject
	@AutoBound
	private DataBinder<CurationConfiguration> configuration;
	
	@Inject
	@Bound
	@DataField
	private Label pageName;
	
	@Inject
	@Bound(property="selectedTypes")
	@DataField(value="summaryProperties")
	private PropertyListWidget summaryProperties;
	
	@AfterInitialization
	private void onInit(){
		configuration.setModel(conf);
	}

	
}