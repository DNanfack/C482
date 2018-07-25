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
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Main main;
    @FXML TabPane tabPane;
    @FXML Tab partsTab;
    @FXML Tab productsTab;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, String> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInventoryLevelColumn;
    @FXML private TableColumn<Part, String> priceCostColumn;
    @FXML private Button partDeleteButton;
    @FXML private Button partModifyButton;


    public void setMain(Main main) {
        this.main = main;
    }

    public void showAddPart() throws IOException {
       main.showAddPart();
    }

    /**
     * Enable or disable buttons when table items are selected
     */
    public void userClickedOnPartTable() {
        this.partDeleteButton.setDisable(false);
        this.partModifyButton.setDisable(false);
    }

    public void deletePartButtonPressed() {
        ObservableList<Part> selectedRows;

        // Gets selected rows
        selectedRows = partTableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part?");
        alert.setHeaderText("Are you sure you want to delete the following parts?");
        StringBuilder partsToDelete = new StringBuilder();
        // Iterates through matching parts to get names to confirm
        for(Part part: selectedRows){
            partsToDelete.append(part.getName()).append("\n");
        }
        alert.setContentText("Remove Parts:\n" + partsToDelete);
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            // Loop through list until match found.
            //TODO: Not working when multiple are selected.
            for (Part part : selectedRows) {
                Main.inventory.removePart(part);
            }
        }

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
        partTableView.setItems(Main.inventory.getAllParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Allows editing for table and selecting multiple rows
        partTableView.setEditable(true);
        partTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        showPartTableData();
    }

}
