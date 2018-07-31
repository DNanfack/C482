package C482.View_Controller;

import C482.Main;
import C482.Model.*;
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

    public void generateDummyData() {
        //<editor-fold desc="DummyDataCreation">
        // Add parts to product
        Product p1 = new Product();
        p1.setProductID(12);
        p1.setInStock(3);
        p1.setName("TestProduct1");
        p1.setPrice(142.19);
        p1.setMin(1);
        p1.setMax(32);

        Product p2 = new Product();
        p2.setProductID(6);
        p2.setInStock(6);
        p2.setName("TestProduct3");
        p2.setPrice(20.21);
        p2.setMin(4);
        p2.setMax(20);

        Product p3 = new Product();
        p3.setProductID(6);
        p3.setInStock(6);
        p3.setName("TestProduct4");
        p3.setPrice(40.12);
        p3.setMin(4);
        p3.setMax(20);

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

        OutsourcedPart part4 = new OutsourcedPart();
        part4.setCompanyName("Test Company 1");
        part4.setInStock(9);
        part4.setName("TestPart4");
        part4.setPrice(125.32);
        part4.setPartID(4);
        part4.setMin(1);
        part4.setMax(214);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p1.addAssociatedPart(part3);

        p2.addAssociatedPart(part4);
        p2.addAssociatedPart(part1);

        p3.addAssociatedPart(part2);

        inventory.addProduct(p1);
        inventory.addProduct(p2);
        inventory.addProduct(p3);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        inventory.addPart(part4);
        //</editor-fold>
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
        ObservableList<Part> selectedRows;

        // Gets selected rows
        selectedRows = partTableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part?");
        alert.setHeaderText("Are you sure you want to delete the following part(s)?");
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

    public void deleteProductButtonPressed() {
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
        alert.setContentText("Remove Parts:\n" + productsToDelete);
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            // Loop through list until match found.
            //TODO: Not working when multiple are selected.
            for (Product product : selectedRows) {
                inventory.removeProduct(product);
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
    }

    public void showProductTableData() {
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
        productTableView.setEditable(true);
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
