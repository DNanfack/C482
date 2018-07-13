package C482.View_Controller;

import C482.Main;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void showMainScreen(ActionEvent event) throws IOException {
        main.showMainScreen("products");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
