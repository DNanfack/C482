package C482.View_Controller;

import C482.Main;
import C482.Model.InhousePart;
import C482.Model.OutsourcedPart;
import C482.Model.Part;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    private Main main;
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


    public void setMain(Main main) {
        this.main = main;
    }

    private void showMainScreen() {
        try {
            main.showMainScreen("parts");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInHouseField() {
        optionalRowLabel.setText("Machine ID");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
        inHousePart = true;
    }
    public void showOutsourcedField() {
        optionalRowLabel.setText("Company Name");
        optionalRowLabel.setVisible(true);
        optionalRowTextfield.setVisible(true);
        inHousePart = false;
    }

    //TODO: Can probably remove this.  Was used for testing.
    public void showValuesAndChange() {
        System.out.println("Values of fields:");
        System.out.println("Part Name: " + partNameTextField.getText());
        System.out.println("Inventory: " + inventoryTextField.getText());
        System.out.println("Price/Cost: " + priceCostTextField.getText());
        System.out.println("Max: " + maxTextField.getText());
        System.out.println("Min: " + minTextField.getText());

        showMainScreen();
    }

    public void cancelButtonPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel add part?");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Are you sure?");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.get() == ButtonType.OK) {
            showMainScreen();
        }
    }

    private Part savePart() {
        if(inHousePart) {
            InhousePart p = new InhousePart();
            p.setName(partNameTextField.getText());
            p.setInStock(Integer.parseInt(inventoryTextField.getText()));
            p.setPrice(Double.parseDouble(priceCostTextField.getText()));
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

    public void saveButtonPressed() throws IOException {
        // if any fields are empty, underline field.
        if(partNameTextField.getText().isEmpty()) {
            Alerts.warningAlert("The name field is blank!");
            partNameTextField.setStyle("-fx-border-color: #ba171c;");
        }
        Part p = savePart();
        // Get partID from inventory
        p.setPartID(Main.inventory.getPartID());
        Main.inventory.addPart(p);
        main.showMainScreen("parts");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        inHouseRadio.fire();
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
