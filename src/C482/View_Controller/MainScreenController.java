package C482.View_Controller;

import C482.Main;
import C482.Model.*;
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
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Inventory inventory;
    private BorderPane rootLayout;
    private ObservableList<Part> partList = FXCollections.observableArrayList();
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    @FXML TabPane tabPane;
    @FXML Tab partsTab;
    @FXML Tab productsTab;
    @FXML public TableView<Part> partTableView;
    @FXML public TableView<Product> productTableView;
    @FXML public TableColumn partIDColumn, partNameColumn, partInventoryLevelColumn, partPriceCostColumn;
    @FXML public TableColumn productIDColumn, productNameColumn, productInventoryLevelColumn, productPriceCostColumn;
    @FXML public Button partDeleteButton, productDeleteButton;
    @FXML public Button partModifyButton, productModifyButton;
    @FXML private CustomTextField textFieldPartSearch, textFieldProductSearch;

    public MainScreenController(Inventory inventory, BorderPane rootLayout) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
    }

    public void showAddPart() throws IOException {
        // Instantiate the controller and give it access to inventory.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/AddPart.fxml"));
        AddPartController controller = new AddPartController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane addPart = loader.load();
        rootLayout.setCenter(addPart);
    }

    public void showModifyPart(Part partToModify) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/ModifyPart.fxml"));
        ModifyPartController controller = new ModifyPartController(partToModify,inventory, rootLayout);
        loader.setController(controller);

        AnchorPane modifyPart = loader.load();
        rootLayout.setCenter(modifyPart);
    }

    /**
     * Enable or disable buttons when table items are selected
     */
    public void userClickedOnPartTable() {
        this.partDeleteButton.setDisable(false);
        this.partModifyButton.setDisable(false);
    }

    public void userClickedOnProductTable() {
        this.productDeleteButton.setDisable(false);
        this.productModifyButton.setDisable(false);
    }

    public void deletePartButtonPressed() {
        ObservableList<Part> selectedRows = partTableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part?");
        alert.setHeaderText("Are you sure you want to delete the following part(s)?");
        StringBuilder partsToDelete = new StringBuilder();
        // Iterates through matching parts to get names to confirm
        for(Part part: selectedRows){
            partsToDelete.append(part.getName()).append("\n");
        }
        alert.setContentText("Remove Parts:\n\n" + partsToDelete);
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            //partList.removeAll(selectedRows);
            inventory.removeParts(selectedRows);
            partTableView.getSelectionModel().clearSelection();
        }
    }

    public void deleteProductButtonPressed() {
        boolean isDeletable = true;
        ObservableList<Product> selectedRows;

        // Gets selected rows
        selectedRows = productTableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete product?");
        alert.setHeaderText("Are you sure you want to delete the following product(s)?");
        StringBuilder productsToDelete = new StringBuilder();
        // Iterates through matching parts to get names to confirm
        for(Product product: selectedRows){
            productsToDelete.append(product.getName()).append("\n");
        }
        alert.setContentText("Remove Products:\n\n" + productsToDelete);
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            for(Product product: selectedRows) {
                String validateDelete = Validation.validateDeleteProduct(product);
                if(!validateDelete.isEmpty()) {
                    Alerts.warningAlert(validateDelete);
                    isDeletable = false;
                }
            }
            if(isDeletable) {
                inventory.removeProducts(selectedRows);
                productTableView.getSelectionModel().clearSelection();
            }
        }
    }

    public void modifyPartButtonPressed() throws IOException{
        // Only allow single object selection
        ObservableList<Part> selectedRows = partTableView.getSelectionModel().getSelectedItems();
        if(selectedRows.size() > 1) {
            Alerts.warningAlert("You can only modify one item at a time.");
        } else {
            Part partToModify = partTableView.getSelectionModel().getSelectedItem();
            showModifyPart(partToModify);
        }
    }

    public void showAddProduct() throws IOException {
        // Instantiate the controller and give it access to inventory
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/AddProduct.fxml"));
        AddProductController controller = new AddProductController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane addProduct = loader.load();
        rootLayout.setCenter(addProduct);
    }

    public void showModifyProduct(Product productToModify) throws IOException {
        // Instantiate the controller and give it access to inventory
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/ModifyProduct.fxml"));
        ModifyProductController controller = new ModifyProductController(inventory, rootLayout, productToModify);
        loader.setController(controller);

        AnchorPane modifyProduct = loader.load();
        rootLayout.setCenter(modifyProduct);
    }

    public void modifyProductButtonPressed() throws IOException{
        // Only allow single object selection
        ObservableList<Product> selectedRows = productTableView.getSelectionModel().getSelectedItems();
        if(selectedRows.size() > 1) {
            Alerts.warningAlert("You can only modify one item at a time.");
        } else {
            Product productToModify = productTableView.getSelectionModel().getSelectedItem();
            showModifyProduct(productToModify);
        }
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
        // Only add items if the inventory is not empty.
        // partList.addAll(inventory.getParts());
        // Add all parts to filtered list
        FilteredList<Part> filteredParts = new FilteredList<>(inventory.getParts(), p -> true);
        textFieldPartSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate(part -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Build filters
                String lowerCaseFilter = newValue.toLowerCase();

                if(part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Matches part name
                } else if(String.valueOf(part.getPartID()).contains(lowerCaseFilter)) {
                    return true; // ID Matches
                }
                return false; // No matches
            });
        });

        // Wrap filtered list in sorted list
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);

        // Bind Sorted List to TableView
        sortedParts.comparatorProperty().bind(partTableView.comparatorProperty());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("inStock"));
        partPriceCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
        partTableView.setItems(sortedParts);
        partTableView.refresh();
    }

    public void showProductTableData() {
        //productList.addAll(inventory.getProducts());
        // Add all products to filtered list
        FilteredList<Product> filteredProducts = new FilteredList<>(inventory.getProducts(), p -> true);
        textFieldProductSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Build filters for search
                String lowerCaseFilter = newValue.toLowerCase();

                if(product.getName().toLowerCase().contains(lowerCaseFilter)) { // Matches product name
                    return true;
                } else if(String.valueOf(product.getProductID()).contains(lowerCaseFilter)) { // matches product ID
                    return true;
                }
                return false;
            });
        });

        // Wrap filtered list in sorted list
        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);

        // Bind sorted list to table view
        sortedProducts.comparatorProperty().bind(productTableView.comparatorProperty());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("inStock"));
        productPriceCostColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        productTableView.setItems(sortedProducts);
        productTableView.refresh();
    }

    public void clearTextFieldPartSearch() {
        textFieldPartSearch.clear();
    }

    public void clearTextFieldProductSearch() {
        textFieldProductSearch.clear();
    }

    public void setPartTableColumnWidth() {
        partIDColumn.prefWidthProperty().bind(partTableView.widthProperty().divide(4));
        partNameColumn.prefWidthProperty().bind(partTableView.widthProperty().divide(4));
        partInventoryLevelColumn.prefWidthProperty().bind(partTableView.widthProperty().divide(4));
        partPriceCostColumn.prefWidthProperty().bind(partTableView.widthProperty().divide(4));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Allows editing for table and selecting multiple rows
        partTableView.setEditable(true);
        partTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        partTableView.setPlaceholder(new Label("No parts found."));
        productTableView.setEditable(true);
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productTableView.setPlaceholder(new Label("No products found."));
        setPartTableColumnWidth();
        showPartTableData();
        showProductTableData();
    }

    /*private static final PseudoClass CLASS_FAIL
            = PseudoClass.getPseudoClass("fail");

    public static void updateStateClass(final Node node,
                                        final boolean isFail) {
        node.pseudoClassStateChanged(CLASS_FAIL, isFail);
    }*/
}
