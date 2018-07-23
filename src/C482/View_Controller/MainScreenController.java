package C482.View_Controller;

import C482.Main;
import C482.Model.InhousePart;
import C482.Model.Part;
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
    @FXML
    TabPane tabPane;
    @FXML
    Tab partsTab;
    @FXML
    Tab productsTab;
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
        InhousePart part1 = new InhousePart();
        part1.setMachineID(125);
        part1.setInStock(4);
        part1.setName("TestPart1");
        part1.setPrice(2.57);
        part1.setPartID(1);
        part1.setMin(1);
        part1.setMax(15);
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(part1);
        partTableView.setItems(parts);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showPartTableData();
    }

}
