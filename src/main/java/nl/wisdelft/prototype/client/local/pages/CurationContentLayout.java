/**
 * 
 */
package nl.wisdelft.prototype.client.local.pages;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.wisdelft.prototype.client.local.widgets.FooterWidget;
import nl.wisdelft.prototype.client.local.widgets.HeaderWidget;
import nl.wisdelft.prototype.client.local.widgets.SummaryWidget;

import org.jboss.errai.ui.nav.client.local.TransitionAnchor;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

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
	private TransitionAnchor<SelectResourcePage> lnkSelectResource;

	/*
	 * @Inject
	 * 
	 * @DataField private TransitionAnchor<SelectTypesPage> lnkSelectTypes;
	 * 
	 * @Inject
	 * 
	 * @DataField private TransitionAnchor<SelectPropertiesPage>
	 * lnkSelectProperties;
	 * 
	 * @Inject
	 * 
	 * @DataField private TransitionAnchor<SelectKnowledgeModelPage>
	 * lnkSelectKnowledgeModel;
	 */
	@Inject
	@DataField
	private TransitionAnchor<SelectContributorsPage> lnkSelectContributors;

	@Inject
	@DataField("contentRight")
	private SummaryWidget summary;

	@PostConstruct
	private void highlightMenu() {
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = event.getValue();
				Widget w = null;
				if (token == null || token.isEmpty() || token.equals("SelectResource"))
					w = lnkSelectResource;
				/*
				 * if (token.equals("SelectTypes")) w = lnkSelectTypes; if
				 * (token.equals("SelectProperties")) w = lnkSelectProperties;
				 * if (token.equals("NeedSpecification")) w =
				 * lnkSelectKnowledgeModel;
				 */
				if (token.equals("SelectContributors"))
					w = lnkSelectContributors;
				w.getElement().getParentElement().addClassName("active");
			}
		});
	}
}
