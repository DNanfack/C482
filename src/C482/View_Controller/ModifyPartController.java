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

public class ModifyPartController implements Initializable {
    private Main main;
    private Inventory inventory;
    private BorderPane rootLayout;
    private Part partToModify;
    @FXML RadioButton inHouseRadio;
    @FXML RadioButton outsourcedRadio;
    @FXML ToggleGroup modifyPartRadioGroup;
    @FXML GridPane partGridPane;
    @FXML Label optionalRowLabel;
    @FXML TextField optionalRowTextfield;
    @FXML TextField partNameTextField;
    @FXML TextField inventoryTextField;
    @FXML TextField priceCostTextField;
    @FXML TextField maxTextField;
    @FXML TextField minTextField;
    @FXML private TextField partIDTextField;

    ModifyPartController(Part partToModify, Inventory inventory, BorderPane rootLyout) {
        this.inventory = inventory;
        this.partToModify = partToModify;
        this.rootLayout = rootLyout;
    }

    /**
     * Sets Text Field and Label to InHouse specific
     */
    public void showInHouseField() {
        optionalRowLabel.setText("Machine ID");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
    }

    /**
     * Sets the Text Field and Label to Outsourced specific
     */
    public void showOutsourcedField() {
        optionalRowLabel.setText("Company Name");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
    }

    /**
     * Fills the interface with the part to modify's data.
     */
    public void showPartData() {
        partNameTextField.setText(partToModify.getName());
        this.partIDTextField.setText((Integer.toString(partToModify.getPartID())));
        inventoryTextField.setText(Integer.toString(partToModify.getInStock()));
        priceCostTextField.setText(Double.toString(partToModify.getPrice()));
        maxTextField.setText(Integer.toString(partToModify.getMax()));
        minTextField.setText(Integer.toString(partToModify.getMin()));
        // Set optional text field based on part type
        if(partToModify instanceof  InhousePart) {
            inHouseRadio.fire();
            outsourcedRadio.setDisable(true);
            optionalRowTextfield.setText(Integer.toString(((InhousePart) partToModify).getMachineID()));
        } else {
            outsourcedRadio.fire();
            optionalRowTextfield.setText(((OutsourcedPart) partToModify).getCompanyName());
            inHouseRadio.setDisable(true);
        }
    }

    public void cancelButtonPressed() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel modify part?");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            showMainScreen();
        }
    }

    /**
     * Handles save button press.  Validates part before saving.
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

        // Validate min/max
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

        if(inHouseRadio.isArmed()) {
            if(!Validation.intValid(optionalRowTextfield.getText())) {
                errors.append("Machine ID must be a valid integer.");
                errors.append("\n");
                optionalRowTextfield.setStyle("-fx-border-color: #ba171c;");
                partIsValid = false;
            } else {
                optionalRowTextfield.setStyle(null);
            }
        } else {
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
        if(partIsValid) {
            savePart();
            // Get partID from inventory
            partToModify.setPartID(inventory.getPartID());
            showMainScreen();
        } else {
            Alerts.warningAlert(errors.toString());
        }
    }

    /**
     * Saves the part to the inventory if input is valid.
     */
    private void savePart() {
        if(partToModify instanceof InhousePart) {
            partToModify.setName(partNameTextField.getText());
            partToModify.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partToModify.setPrice(Validation.getRoundedPrice(priceCostTextField.getText()));
            partToModify.setMin(Integer.parseInt(minTextField.getText()));
            partToModify.setMax(Integer.parseInt(maxTextField.getText()));
            ((InhousePart) partToModify).setMachineID(Integer.parseInt(optionalRowTextfield.getText()));
        } else {
            partToModify.setName(partNameTextField.getText());
            partToModify.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partToModify.setPrice(Validation.getRoundedPrice(priceCostTextField.getText()));
            partToModify.setMin(Integer.parseInt(minTextField.getText()));
            partToModify.setMax(Integer.parseInt(maxTextField.getText()));
            ((OutsourcedPart) partToModify).setCompanyName(optionalRowTextfield.getText());
        }
        inventory.updatePart(partToModify);
    }

    /**
     * Shows the main screen
     * @throws IOException If FXML fails to load.
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        priceCostTextField.setTooltip(new Tooltip("Price will auto round to two decimal places."));
        partIDTextField.setDisable(true);
        showPartData();
    }
}
