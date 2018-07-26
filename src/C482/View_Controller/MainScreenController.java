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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Inventory inventory;
    private BorderPane rootLayout;
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

    public MainScreenController(Inventory inventory, BorderPane rootLayout) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
    }

    public void showAddPart() throws IOException {
        // Instantiate the controller and give it access to inventory.
        FXMLLoader loader = new FXMLLoader();
        AddPartController controller = new AddPartController(inventory, rootLayout);
        loader.setController(controller);
        //TODO: Remove controller selection from fxml
        loader.setLocation(Main.class.getResource("View_Controller/AddPart.fxml"));
        AnchorPane addPart = loader.load();
        rootLayout.setCenter(addPart);
    }

    public void generateDummyData() {
        //<editor-fold desc="DummyDataCreation">
        // Add parts to product
        Product p1 = new Product();
        p1.setProductID(1);
        p1.setInStock(3);
        p1.setName("TestProduct1");
        p1.setPrice(142.19);
        p1.setMin(1);
        p1.setMax(32);
        InhousePart part1 = new InhousePart();
        part1.setMachineID(125);
        part1.setInStock(4);
        part1.setName("TestPart1");
        part1.setPrice(2.57);
        part1.setPartID(1);
        part1.setMin(1);
        part1.setMax(15);

        InhousePart part2 = new InhousePart();
        part2.setMachineID(14);
        part2.setInStock(5);
        part2.setName("TestPart2");
        part2.setPrice(1.27);
        part2.setPartID(2);
        part2.setMin(1);
        part2.setMax(10);

        InhousePart part3 = new InhousePart();
        part3.setMachineID(125);
        part3.setInStock(6);
        part3.setName("TestPart3");
        part3.setPrice(8.63);
        part3.setPartID(3);
        part3.setMin(1);
        part3.setMax(30);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p1.addAssociatedPart(part3);

        inventory.addProduct(p1);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        //</editor-fold>
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
                inventory.removePart(part);
            }
        }
    }

    public void showModifyPart(Part partToModify) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/ModifyPart.fxml"));
        AnchorPane modifyPart = loader.load();
        rootLayout.setCenter(modifyPart);

        // Give controller access to part and main app.
        ModifyPartController controller = new ModifyPartController(partToModify);
        controller.setPartToModify(partToModify);
    }

    public void modifyPartButtonPressed() throws IOException{
        // Only allow single object selection
        ObservableList<Part> selectedRows = partTableView.getSelectionModel().getSelectedItems();
        if(selectedRows.size() > 1) {
            Alerts.warningAlert("You can only modify one item at a time.");
        } else {
            InhousePart p = new InhousePart();
            Alerts.warningAlert("Value of part name: " + selectedRows.get(0).getName());
        }
    }

    public void showAddProduct() throws IOException {
        showAddProduct();
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
        partTableView.setItems(inventory.getAllParts());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateDummyData();
        // Allows editing for table and selecting multiple rows
        partTableView.setEditable(true);
        partTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        showPartTableData();
    }

}
