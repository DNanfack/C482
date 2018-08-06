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

public class ModifyProductController implements Initializable {
    private Inventory inventory;
    private BorderPane rootLayout;
    private Product productToModify;
    private ObservableList<Part> partsAvailable, partsToAddToProduct;

    @FXML TextField productNameTextField, inventoryTextField, priceCostTextField, maxTextField, minTextField, productIDTextField;
    @FXML TextField textFieldAvailablePartsSearch, textFieldPartsToAddSearch;
    @FXML TableView<Part> tableViewAvailableParts, tableViewPartsToAdd;
    @FXML TableColumn columnAvailablePartsID, columnAvailablePartsName, columnAvailablePartsInventory, columnAvailablePartsPrice;
    @FXML TableColumn columnPartsToAddID, columnPartsToAddName, columnPartsToAddInventory, columnPartsToAddPrice;

    public ModifyProductController(Inventory inventory, BorderPane rootLayout, Product productToModify) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
        this.partsAvailable = FXCollections.observableArrayList(inventory.getParts());
        this.productToModify = productToModify;
        this.partsToAddToProduct = FXCollections.observableArrayList(productToModify.getAssociatedParts());

        // Clean up lists
        partsAvailable.removeAll(partsToAddToProduct);
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

    /**
     * Cancels without saving
     * @throws IOException If FXML fails to load
     */
    public void cancelButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel modify product?");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            showMainScreen();
        }
    }

    private void saveProduct() {
        // Update fields
        productToModify.setName(productNameTextField.getText());
        productToModify.setInStock(Integer.parseInt(inventoryTextField.getText()));
        productToModify.setPrice(Double.parseDouble(priceCostTextField.getText()));
        productToModify.setMin(Integer.parseInt(minTextField.getText()));
        productToModify.setMax(Integer.parseInt(maxTextField.getText()));

        // Update parts
        productToModify.setAssociatedParts(partsToAddToProduct);
        inventory.updateProduct(productToModify);
    }

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
            double productTotalPrice = Validation.getRoundedPrice(priceCostTextField.getText());
            double totalPartCost = 0;
            for(Part part: partsToAddToProduct) {
                totalPartCost += part.getPrice();
            }
            if(productTotalPrice < totalPartCost) { // modified product price is less than total part cost
                errors.append("Total cost of parts in product exceeds cost of product.  Either raise price of product or remove parts.");
                errors.append("\n");
                errors.append("Total cost: ");
                errors.append(totalPartCost);
                priceCostTextField.setStyle("-fx-border-color: #ba171c;");
                priceIsValid = false;
            } else {
                priceCostTextField.setStyle(null);
            }
            if(priceIsValid) {
                saveProduct();
                showMainScreen();
            } else {
                Alerts.warningAlert(errors.toString());
            }
        } else {
            Alerts.warningAlert(errors.toString());
        }
    }

    public void addPartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewAvailableParts.getSelectionModel().getSelectedItems();
        partsToAddToProduct.addAll(selectedRows);
        partsAvailable.removeAll(selectedRows);

        /*for(Part part: selectedRows) {
            partsAvailable.remove(part);
            partsToAddToProduct.add(part);
        }*/
        tableViewPartsToAdd.refresh();
        tableViewAvailableParts.refresh();
    }

    public void removePartToProductButtonPressed() {
        ObservableList<Part> selectedRows = tableViewPartsToAdd.getSelectionModel().getSelectedItems();
        partsAvailable.addAll(selectedRows);
        partsToAddToProduct.removeAll(selectedRows);

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

    public void showProductData() {
        productNameTextField.setText(productToModify.getName());
        productIDTextField.setText(Integer.toString(productToModify.getProductID()));
        inventoryTextField.setText(Integer.toString(productToModify.getInStock()));
        priceCostTextField.setText(Double.toString(productToModify.getPrice()));
        maxTextField.setText(Integer.toString(productToModify.getMax()));
        minTextField.setText(Integer.toString(productToModify.getMin()));
    }

    public void buttonClearPartsAvailableSearchPressed() {
        textFieldAvailablePartsSearch.clear();
    }

    public void buttonClearPartsToAddSearchPressed() {
        textFieldPartsToAddSearch.clear();
    }

    public void initialize(URL url, ResourceBundle rb) {
        tableViewPartsToAdd.setEditable(true);
        tableViewAvailableParts.setEditable(true);
        tableViewPartsToAdd.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewAvailableParts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        priceCostTextField.setTooltip(new Tooltip("Price will auto round to two decimal places."));

        showAvailablePartsTableData();
        showPartsToAddTableData();
        showProductData();
    }
}
