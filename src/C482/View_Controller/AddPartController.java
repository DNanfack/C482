package C482.View_Controller;

import C482.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showMainScreen(ActionEvent event) throws IOException {
        main.showMainScreen("parts");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
