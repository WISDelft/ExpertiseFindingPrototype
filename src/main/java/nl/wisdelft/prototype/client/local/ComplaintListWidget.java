package nl.wisdelft.prototype.client.local;

import nl.wisdelft.prototype.client.shared.UserComplaint;
import org.jboss.errai.ui.client.widget.ListWidget;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * A custom list widget implementation that will display an {@link HTMLPanel}
 * containing a {@link ComplaintListItemWidget} for each {@link UserComplaint} in the
 * list passed to {@link #setItems(java.util.List)} .
 */
public class ComplaintListWidget extends ListWidget<UserComplaint, ComplaintListItemWidget> {

  /**
   * Creates a new instance of this list widget using the provided type as root
   * element.
   * 
   * @param type
   *          the root tag to use for the panel.
   */
  public ComplaintListWidget(String type) {
    super(new HTMLPanel(type, ""));
  }

  @Override
  protected Class<ComplaintListItemWidget> getItemWidgetType() {
    return ComplaintListItemWidget.class;
  }
}
