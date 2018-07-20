package C482.View_Controller;

import C482.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    private Main main;
    private boolean inHousePart;
    @FXML
    RadioButton inHouseRadio;
    @FXML
    RadioButton outsourcedRadio;
    @FXML
    ToggleGroup addPartRadioGroup;
    @FXML
    GridPane partGridPane;
    @FXML
    Label optionalRowLabel;
    @FXML
    TextField optionalRowTextfield;
    @FXML
    TextField partNameTextField;
    @FXML
    TextField inventoryTextField;
    @FXML
    TextField priceCostTextField;
    @FXML
    TextField maxTextField;
    @FXML
    TextField minTextField;


    public void setMain(Main main) {
        this.main = main;
    }

    public void showMainScreen() {
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

    public void savePart() {
        //TODO: Write method
        //TODO: Add validation
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        inHouseRadio.fire();
        addPartRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(addPartRadioGroup.selectedToggleProperty() != null) {
                    if(inHouseRadio.isSelected()) {
                        showInHouseField();
                    } else if(outsourcedRadio.isSelected()) {
                        showOutsourcedField();
                    }
                }
            }
        });
    }
}
