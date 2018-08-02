package C482.View_Controller;

import C482.Main;
import C482.Model.Inventory;
import C482.Model.Part;
import C482.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    private Inventory inventory;
    private BorderPane rootLayout;
    private ObservableList<Part> partsAvailable, partsToAddToProduct;

    @FXML TextField  productNameTextField, inventoryTextField, priceCostTextField, maxTextField, minTextField;
    @FXML TextField textFieldAvailablePartsSearch, textFieldPartsToAddSearch;
    @FXML TableView<Part> tableViewAvailableParts, tableViewPartsToAdd;
    @FXML TableColumn columnAvailablePartsID, columnAvailablePartsName, columnAvailablePartsInventory, columnAvailablePartsPrice;
    @FXML TableColumn columnPartsToAddID, columnPartsToAddName, columnPartsToAddInventory, columnPartsToAddPrice;

    public AddProductController(Inventory inventory, BorderPane rootLayout) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
        this.partsAvailable = inventory.getParts();
        this.partsToAddToProduct = FXCollections.observableArrayList();
    }

    public void showMainScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);
        controller.selectTab("products");
    }

    public void cancelButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel add product?");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            showMainScreen();
        }
    }

    private Product saveProduct() {
        Product p = new Product();
        p.setName(productNameTextField.getText());
        p.setInStock(Integer.parseInt(inventoryTextField.getText()));
        p.setPrice(Double.parseDouble(priceCostTextField.getText()));
        p.setMin(Integer.parseInt(minTextField.getText()));
        p.setMax(Integer.parseInt(maxTextField.getText()));
        return p;
    }

    public void saveButtonPressed() throws IOException {
        // if any fields are empty, underline field.
        if(productNameTextField.getText().isEmpty()) {
            Alerts.warningAlert("The name field is blank!");
            productNameTextField.setStyle("-fx-border-color: #ba171c;");
        }
        Product p = saveProduct();
        p.setProductID(inventory.getProductID());
        inventory.addProduct(p);
        showMainScreen();
     }

     public void addPartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewAvailableParts.getSelectionModel().getSelectedItems();
        for(Part part: selectedRows) {
            partsAvailable.remove(part);
            partsToAddToProduct.add(part);
        }
        tableViewPartsToAdd.refresh();
        tableViewAvailableParts.refresh();
     }

     public void removePartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewPartsToAdd.getSelectionModel().getSelectedItems();
        for(Part part: selectedRows) {
            partsToAddToProduct.remove(part);
            partsAvailable.add(part);
        }
        tableViewPartsToAdd.refresh();
        tableViewAvailableParts.refresh();
     }

     public void showAvailablePartsTableData() {
         FilteredList<Part> filteredAvailableParts = new FilteredList<>(partsAvailable, p -> true);
         textFieldAvailablePartsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
             filteredAvailableParts.setPredicate(part -> {
                 if(newValue == null || newValue.isEmpty()) {
                     return true;
                 }

                 // Build filters for search
                 String lowerCaseFilter = newValue.toLowerCase();

                 if(part.getName().toLowerCase().contains(lowerCaseFilter)){
                     return true;
                 } else if(String.valueOf(part.getPartID()).contains(lowerCaseFilter)) {
                     return true;
                 }
                 return false;
             });
         });

         // Wrap filtered list in sorted list
         SortedList<Part> sortedAvailableParts = new SortedList<>(filteredAvailableParts);

         sortedAvailableParts.comparatorProperty().bind(tableViewAvailableParts.comparatorProperty());
         columnAvailablePartsID.setCellValueFactory(new PropertyValueFactory<Part, String>("partID"));
         columnAvailablePartsName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
         columnAvailablePartsInventory.setCellValueFactory(new PropertyValueFactory<Part, String>("inStock"));
         columnAvailablePartsPrice.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
         tableViewAvailableParts.setItems(sortedAvailableParts);
     }

     public void showPartsToAddTableData() {
         FilteredList<Part> filteredPartsToAdd = new FilteredList<>(partsToAddToProduct, p -> true);
         textFieldPartsToAddSearch.textProperty().addListener((observable, oldValue, newValue) -> {
             filteredPartsToAdd.setPredicate(part -> {
                 if(newValue == null || newValue.isEmpty()) {
                     return true;
                 }

                 // Build filters for search
                 String lowerCaseFilter = newValue.toLowerCase();

                 if(part.getName().toLowerCase().contains(lowerCaseFilter)){
                     return true;
                 } else if(String.valueOf(part.getPartID()).contains(lowerCaseFilter)) {
                     return true;
                 }
                 return false;
             });
         });

         // Wrap filtered list in sorted list
         SortedList<Part> sortedPartsToAdd = new SortedList<>(filteredPartsToAdd);

         sortedPartsToAdd.comparatorProperty().bind(tableViewPartsToAdd.comparatorProperty());
         columnPartsToAddID.setCellValueFactory(new PropertyValueFactory<Part, String>("partID"));
         columnPartsToAddName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
         columnPartsToAddInventory.setCellValueFactory(new PropertyValueFactory<Part, String>("inStock"));
         columnPartsToAddPrice.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
         tableViewPartsToAdd.setItems(sortedPartsToAdd);
     }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewPartsToAdd.setEditable(true);
        tableViewAvailableParts.setEditable(true);
        tableViewPartsToAdd.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAvailableParts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        showAvailablePartsTableData();
        showPartsToAddTableData();
    }
}
