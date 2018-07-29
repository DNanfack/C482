package C482.View_Controller;

import C482.Main;
import C482.Model.InhousePart;
import C482.Model.Inventory;
import C482.Model.OutsourcedPart;
import C482.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField partIDTextField;

    public ModifyPartController(Part partToModify, Inventory inventory, BorderPane rootLyout) {
        this.inventory = inventory;
        this.partToModify = partToModify;
        this.rootLayout = rootLyout;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    //TODO: Can probably remove this part
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

    public void showPartData() {
        partNameTextField.setText(partToModify.getName());
        this.partIDTextField.setText((Integer.toString(partToModify.getPartID())));
        inventoryTextField.setText(Integer.toString(partToModify.getInStock()));
        priceCostTextField.setText(Double.toString(partToModify.getPartID()));
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

    public void saveButtonPressed() throws IOException {
        // if any fields are empty, underline field.
        if(partNameTextField.getText().isEmpty()) {
            Alerts.warningAlert("The name field is blank!");
            partNameTextField.setStyle("-fx-border-color: #ba171c;");
        }
        savePart();
        showMainScreen();
    }

    private void savePart() {
        if(partToModify instanceof InhousePart) {
            partToModify.setName(partNameTextField.getText());
            partToModify.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partToModify.setPrice(Double.parseDouble(priceCostTextField.getText()));
            partToModify.setMin(Integer.parseInt(minTextField.getText()));
            partToModify.setMax(Integer.parseInt(maxTextField.getText()));
            ((InhousePart) partToModify).setMachineID(Integer.parseInt(optionalRowTextfield.getText()));
        } else {
            partToModify.setName(partNameTextField.getText());
            partToModify.setInStock(Integer.parseInt(inventoryTextField.getText()));
            partToModify.setPrice(Double.parseDouble(priceCostTextField.getText()));
            partToModify.setMin(Integer.parseInt(minTextField.getText()));
            partToModify.setMax(Integer.parseInt(maxTextField.getText()));
            ((OutsourcedPart) partToModify).setCompanyName(optionalRowTextfield.getText());
        }
        inventory.updatePart(partToModify);
    }

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
        partIDTextField.setDisable(true);
        //TODO: Probably don't need this anymore
        /*modifyPartRadioGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(modifyPartRadioGroup.selectedToggleProperty() != null) {
                if(inHouseRadio.isSelected()) {
                    inHousePart = true;
                    showInHouseField();
                } else if(outsourcedRadio.isSelected()) {
                    inHousePart = false;
                    showOutsourcedField();
                }
            }
        });*/
        showPartData();
    }
}
