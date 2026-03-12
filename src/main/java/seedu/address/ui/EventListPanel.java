package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {

    private static final String FXML = "EventListPanel.fxml";

    @FXML
    private ListView<String> eventListView;

    /**
     * Creates an {@code EventListPanel}.
     */
    public EventListPanel() {
        super(FXML);

        ObservableList<String> placeholderEvents = FXCollections.observableArrayList(
                "Event 1",
                "Event 2",
                "Event 3"
        );
        eventListView.setItems(placeholderEvents);
    }
}
