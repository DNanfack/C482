/*
 * Author: Taylor Vories
 * WGU C482 Project
 */

package C482.View_Controller;

import C482.Main;
import C482.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    private Inventory inventory;
    private BorderPane rootLayout;
    private boolean inHousePart;
    @FXML RadioButton inHouseRadio;
    @FXML RadioButton outsourcedRadio;
    @FXML ToggleGroup addPartRadioGroup;
    @FXML GridPane partGridPane;
    @FXML Label optionalRowLabel;
    @FXML TextField optionalRowTextfield;
    @FXML TextField partNameTextField;
    @FXML TextField inventoryTextField;
    @FXML TextField priceCostTextField;
    @FXML TextField maxTextField;
    @FXML TextField minTextField;

    /**
     * Constructor that passes inventory object to class.
     * @param inventory Inventory to manage.
     * @param rootLayout The rootLayout provided by main.  Allows the class to change screens inside the same
     *                   root layout.
     */
    AddPartController(Inventory inventory, BorderPane rootLayout) {
        this.inventory = inventory;
        this.rootLayout = rootLayout;
    }

    /**
     * Shows the main screen
     * @throws IOException If FXML is unable to load.
     */
    private void showMainScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);
        controller.selectTab("parts");
    }

    /**
     * Shows a text field specifically for an InHouse part
     */
    public void showInHouseField() {
        optionalRowLabel.setText("Machine ID");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
        inHousePart = true;
    }

    /**
     * Shows a text field specifically for an OutsourcedPart
     */
    public void showOutsourcedField() {
        optionalRowLabel.setText("Company Name");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
        inHousePart = false;
    }

    /**
     * If the user selects cancel, it exits the screen without saving.
     * @throws IOException If FXML fails to load
     */
    public void cancelButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel add part?");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            showMainScreen();
        }
    }

    /**
     * Saves the part from the values provided by the user interface.
     * @return Returns a part to be added to inventory.
     */
    private Part savePart() {
        if(inHousePart) {
            InhousePart p = new InhousePart();
            p.setName(partNameTextField.getText());
            p.setInStock(Integer.parseInt(inventoryTextField.getText()));
            // format price to 2 decimal places
            p.setPrice(Validation.getRoundedPrice(priceCostTextField.getText()));
            p.setMin(Integer.parseInt(minTextField.getText()));
            p.setMax(Integer.parseInt(maxTextField.getText()));
            p.setMachineID(Integer.parseInt(optionalRowTextfield.getText()));
            return p;
        } else {
            OutsourcedPart p = new OutsourcedPart();
            p.setName(partNameTextField.getText());
            p.setInStock(Integer.parseInt(inventoryTextField.getText()));
            p.setPrice(Double.parseDouble(priceCostTextField.getText()));
            p.setMin(Integer.parseInt(minTextField.getText()));
            p.setMax(Integer.parseInt(maxTextField.getText()));
            p.setCompanyName(optionalRowTextfield.getText());
            return p;
        }
    }

    /**
     * When a user clicks the save button it validates the input is good and saves the part to the inventory.
     * @throws IOException If FXML fails to load
     */
    public void saveButtonPressed() throws IOException {
        boolean partIsValid = true;
        StringBuilder errors = new StringBuilder();

        // Validate name
        String nameValidation = Validation.validateName(partNameTextField.getText());
        if(!nameValidation.isEmpty()) {
            errors.append(nameValidation);
            errors.append("\n");
            partNameTextField.setStyle("-fx-border-color: #ba171c;");
            partIsValid = false;
        } else {
            partNameTextField.setStyle(null);
        }

        // Validate price
        String priceValidation = Validation.validatePrice(priceCostTextField.getText());
        if(!priceValidation.isEmpty()) {
            errors.append(priceValidation);
            errors.append("\n");
            priceCostTextField.setStyle("-fx-border-color: #ba171c;");
            partIsValid = false;
        } else {
            priceCostTextField.setStyle(null);
        }

        // Validate min/max values
        String minMaxValidation = Validation.validateMinMax(minTextField.getText(), maxTextField.getText());
        if(!minMaxValidation.isEmpty()) {
            errors.append(minMaxValidation);
            errors.append("\n");
            minTextField.setStyle("-fx-border-color: #ba171c;");
            maxTextField.setStyle("-fx-border-color: #ba171c;");
            partIsValid = false;
        } else {
            minTextField.setStyle(null);
            maxTextField.setStyle(null);
        }

        // Validate inventory number
        if(minMaxValidation.isEmpty()) {
            String invValidation = Validation.validateInventory(inventoryTextField.getText(), minTextField.getText(), maxTextField.getText());
            if(!invValidation.isEmpty()) {
                errors.append(invValidation);
                errors.append("\n");
                inventoryTextField.setStyle("-fx-border-color: #ba171c;");
                partIsValid = false;
            } else {
                inventoryTextField.setStyle(null);
            }
        }

        // Logic for InHouse parts
        if(inHousePart) {
            if(!Validation.intValid(optionalRowTextfield.getText())) {
                errors.append("Machine ID must be a valid integer.");
                errors.append("\n");
                optionalRowTextfield.setStyle("-fx-border-color: #ba171c;");
                partIsValid = false;
            } else {
                optionalRowTextfield.setStyle(null);
            }
        } else { // Logic for Outsourced parts
            String validateCompanyName = Validation.validateName(optionalRowTextfield.getText());
            if(!validateCompanyName.isEmpty()) {
                errors.append(validateCompanyName);
                errors.append("\n");
                optionalRowTextfield.setStyle("-fx-border-color: #ba171c;");
                partIsValid = false;
            } else {
                optionalRowTextfield.setStyle(null);
            }
        }
        if(partIsValid) { // input passed all validation
            Part partToSave = savePart();
            // Get partID from inventory
            partToSave.setPartID(inventory.getPartID());
            inventory.addPart(partToSave);
            showMainScreen();
        } else { // input was invalid
            Alerts.warningAlert(errors.toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        // Tooltip to let the user know the price will be rounded to 2 decimal places.
        priceCostTextField.setTooltip(new Tooltip("Price will auto round to two decimal places."));
        // Defaults to InHouse part
        inHouseRadio.fire();
        // Listener for radio buttons
        addPartRadioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(addPartRadioGroup.selectedToggleProperty() != null) {
                if(inHouseRadio.isSelected()) {
                    inHousePart = true;
                    showInHouseField();
                } else if(outsourcedRadio.isSelected()) {
                    inHousePart = false;
                    showOutsourcedField();
                }
            }
        });
    }
}
