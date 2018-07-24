package C482.View_Controller;

import C482.Main;
import C482.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Main main;
    private Inventory inventory;
    @FXML TabPane tabPane;
    @FXML Tab partsTab;
    @FXML Tab productsTab;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, String> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInventoryLevelColumn;
    @FXML private TableColumn<Part, String> priceCostColumn;


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

    private void showPartTableData() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("inStock"));
        priceCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
        // Try adding an additional part
        OutsourcedPart p = new OutsourcedPart();
        p.setCompanyName("TaylorCompany");
        p.setInStock(10);
        p.setMax(70);
        p.setMin(3);
        p.setName("Taylor's Cool Part");
        p.setPartID(32);
        p.setPrice(51.21);
        main.inventory.addPart(p);
        partTableView.setItems(main.inventory.getAllParts());
    }

    // I don't think I'll need this.  TODO: Maybe remove
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showPartTableData();
    }

}
