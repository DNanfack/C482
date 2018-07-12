package C482.View_Controller;

import C482.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showAddPart() throws IOException {
       main.showAddPart();
    }

    public void showAddProduct() throws IOException {
        main.showAddProduct();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
