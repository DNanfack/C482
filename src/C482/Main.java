package C482;

import C482.Model.Inventory;
import C482.View_Controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    public BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Inventory inventory = new Inventory();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C482 Inventory Project");
        rootLayout = new BorderPane();

        initRootLayout();
        showMainScreen("parts", inventory, rootLayout);
    }

    /**
     * Initializes the root layout
     */
    private void initRootLayout() throws IOException {
        // Load root layout fxml file
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/RootLayout.fxml"));
        rootLayout = loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Shows MainScreen inside the RootLayout
     */
    public void showMainScreen(String tabName, Inventory inventory, BorderPane rootLayout) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        // Instantiate MainScreenController and pass inventory
        MainScreenController controller = new MainScreenController(inventory, rootLayout);
        loader.setController(controller);

        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);
        // Select tab for TableView
        controller.selectTab(tabName);
        controller.generateDummyData();
    }

    /*public void showModifyPart(Part partToModify) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/ModifyPart.fxml"));
        AnchorPane modifyPart = loader.load();
        rootLayout.setCenter(modifyPart);

        // Give controller access to part and main app.
        ModifyPartController controller = new ModifyPartController(partToModify);
        controller.setMain(this);
    }*/

    public void showAddProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/AddProduct.fxml"));
        AnchorPane addProduct = loader.load();
        rootLayout.setCenter(addProduct);

        // Give controller access to main
        AddProductController controller = loader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
