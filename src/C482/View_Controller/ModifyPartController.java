package C482.View_Controller;

import C482.Main;
import C482.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    private Main main;
    private Part partToModify;
    private boolean inHousePart;
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

    public void setMain(Main main) {
        this.main = main;
    }

    public void setPartToModify(Part partToModify) {
        this.partToModify = partToModify;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        inHouseRadio.fire();
        modifyPartRadioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(modifyPartRadioGroup.selectedToggleProperty() != null) {
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
