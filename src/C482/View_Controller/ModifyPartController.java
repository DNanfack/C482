package C482.View_Controller;

import C482.Main;
import C482.Model.InhousePart;
import C482.Model.Inventory;
import C482.Model.OutsourcedPart;
import C482.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    private Main main;
    private Inventory inventory;
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
    @FXML TextField partIDTextField;

    public ModifyPartController(Part partToModify) {
        //this.inventory = inventory;
        this.partToModify = partToModify;
    }

    public ModifyPartController() {

    }

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

    private void showPartData() {
        partIDTextField.setText(Integer.toString(partToModify.getPartID()));
        partNameTextField.setText(partToModify.getName());
        inventoryTextField.setText(Integer.toString(partToModify.getInStock()));
        priceCostTextField.setText(Double.toString(partToModify.getPartID()));
        maxTextField.setText(Integer.toString(partToModify.getMax()));
        minTextField.setText(Integer.toString(partToModify.getMin()));
        // Set optional text field based on part type
        if(partToModify instanceof  InhousePart) {
            inHouseRadio.fire();
            optionalRowTextfield.setText(Integer.toString(((InhousePart) partToModify).getMachineID()));
        } else {
            outsourcedRadio.fire();
            optionalRowTextfield.setText(((OutsourcedPart) partToModify).getCompanyName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        optionalRowLabel.setVisible(false);
        optionalRowTextfield.setVisible(false);
        showPartData();
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
