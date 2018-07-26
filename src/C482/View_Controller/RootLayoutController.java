package C482.View_Controller;

import C482.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

    @FXML
    private MenuItem fileCloseMenuItem;

    //Handle fileCloseMenuClick
    @FXML
    public void handleClose(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Shows About Dialog
     */
    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("C482 Project");
        alert.setHeaderText("About");
        alert.setContentText("Author: Taylor Vories\nC482 Java Project");

        // Show alert
        alert.showAndWait();
    }
}
