package C482.View_Controller;

import C482.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Main main;
    @FXML
    TabPane tabPane;
    @FXML
    Tab partsTab;
    @FXML
    Tab productsTab;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showAddPart() throws IOException {
       main.showAddPart();
    }

    public void showAddProduct() throws IOException {
        main.showAddProduct();
    }

    public void selectTab(String tab) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        switch (tab) {
            case "parts":
                selectionModel.select(partsTab);
                break;
            case "products":
                selectionModel.select(productsTab);
                break;
            default:
                selectionModel.select(1);
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
