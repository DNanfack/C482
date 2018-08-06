/*
 * Author: Taylor Vories
 * WGU C482 Project
 */

package C482.View_Controller;

import C482.Main;
import C482.Model.Inventory;
import C482.Model.Part;
import C482.Model.Product;
import C482.Model.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    /**
     * Constructor.  Loads the controller with access to inventory and rootLayout
     * @param inventory the project inventory
     * @param rootLayout the rootLayout to place the different scenes inside
     */
    AddProductController(Inventory inventory, BorderPane rootLayout) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
        this.partsAvailable = FXCollections.observableArrayList(inventory.getParts());
        this.partsToAddToProduct = FXCollections.observableArrayList();
    }

    /**
     * Shows the main screen
     * @throws IOException If FXML fails to load
     */
    public void showMainScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);
        controller.selectTab("products");
    }

    /**
     * Handles the cancel button being pressed. Returns to main screen without saving.
     * @throws IOException If FXML fails to load
     */
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

    /**
     * Creates a product to save to inventory once validation is complete.
     * @return The product to add to inventory
     */
    private Product saveProduct() {
        Product p = new Product();
        p.setName(productNameTextField.getText());
        p.setInStock(Integer.parseInt(inventoryTextField.getText()));
        double price = Validation.getRoundedPrice(priceCostTextField.getText());
        p.setPrice(price);
        p.setMin(Integer.parseInt(minTextField.getText()));
        p.setMax(Integer.parseInt(maxTextField.getText()));
        return p;
    }

    /**
     * Button handler
     * @throws IOException If FXML fails to load
     */
    public void saveButtonPressed() throws IOException {
        boolean productIsValid = true;
        boolean priceIsValid = true;
        StringBuilder errors = new StringBuilder();
        // if any fields are empty, underline field.
        // VALIDATE NAME
        String nameValidation = Validation.validateName(productNameTextField.getText());
        if(!nameValidation.isEmpty()) {
            errors.append(nameValidation);
            errors.append("\n");
            productNameTextField.setStyle("-fx-border-color: #ba171c;");
            productIsValid = false;
        } else {
            productNameTextField.setStyle(null);
        }

        // VALIDATE PRICE
        String priceValidation = Validation.validatePrice(priceCostTextField.getText());
        if(!priceValidation.isEmpty()) {
            errors.append(priceValidation);
            errors.append("\n");
            priceCostTextField.setStyle("-fx-border-color: #ba171c;");
            productIsValid = false;
        } else {
            priceCostTextField.setStyle(null);
        }

        // VALIDATE MIN/MAX
        String minMaxValidation = Validation.validateMinMax(minTextField.getText(), maxTextField.getText());
        if(!minMaxValidation.isEmpty()) {
            errors.append(minMaxValidation);
            errors.append("\n");
            minTextField.setStyle("-fx-border-color: #ba171c;");
            maxTextField.setStyle("-fx-border-color: #ba171c;");
            productIsValid = false;
        } else {
            minTextField.setStyle(null);
            maxTextField.setStyle(null);
        }

        // VALIDATE INVENTORY
        if(minMaxValidation.isEmpty()) {
            // VALIDATE INVENTORY
            String invValidation = Validation.validateInventory(inventoryTextField.getText(), minTextField.getText(), maxTextField.getText());
            if(!invValidation.isEmpty()) {
                errors.append(invValidation);
                errors.append("\n");
                inventoryTextField.setStyle("-fx-border-color: #ba171c;");
                productIsValid = false;
            } else {
                inventoryTextField.setStyle(null);
            }
        }

        // VALIDATE TOTAL COST vs PRICE
        if(productIsValid) {
            Product p = saveProduct();
            p.setAssociatedParts(partsToAddToProduct);
            String validProductPrice = Validation.validateProductPrice(p);
            if(!validProductPrice.isEmpty()) { // price is not valid.
                errors.append(validProductPrice);
                errors.append("\n");
                priceCostTextField.setStyle("-fx-border-color: #ba171c;");
                priceIsValid = false;
            } else {
                priceCostTextField.setStyle(null);
            }
            if(priceIsValid) {
                p.setProductID(inventory.getProductID());
                inventory.addProduct(p);
                showMainScreen();
            } else {
                Alerts.warningAlert(errors.toString());
            }
        } else {
            Alerts.warningAlert(errors.toString());
        }
     }

    /**
     * Handles the add part to product button.  Adds a part to the associated product list.
     */
    public void addPartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewAvailableParts.getSelectionModel().getSelectedItems();
        partsToAddToProduct.addAll(selectedRows);
        partsAvailable.removeAll(selectedRows);

        tableViewPartsToAdd.refresh();
        tableViewAvailableParts.refresh();
     }

    /**
     * Handles the remove part button.  Removes a part from the associated product list.
     */
    public void removePartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewPartsToAdd.getSelectionModel().getSelectedItems();
        partsAvailable.addAll(selectedRows);
        partsToAddToProduct.removeAll(selectedRows);
        /*for(Part part: selectedRows) {
            partsToAddToProduct.remove(part);
            partsAvailable.add(part);
        }*/
        tableViewPartsToAdd.refresh();
        tableViewAvailableParts.refresh();
     }

    /**
     * Shows table data when the scene loads.  Shows all parts that exist in inventory
     */
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

    /**
     * Shows table data when scene loads.  Shows parts to be added to associated product.
     */
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

    /**
     * Clears search text field
     */
    public void buttonClearPartsAvailableSearchPressed() {
         textFieldAvailablePartsSearch.clear();
     }

    /**
     * Clears search text field
     */
    public void buttonClearPartsToAddSearchPressed() {
        textFieldPartsToAddSearch.clear();
     }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableViewPartsToAdd.setEditable(true);
        tableViewAvailableParts.setEditable(true);
        // Sets table to be able to select multiple items
        tableViewPartsToAdd.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAvailableParts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        priceCostTextField.setTooltip(new Tooltip("Price will auto round to two decimal places."));

        showAvailablePartsTableData();
        showPartsToAddTableData();
    }
}
