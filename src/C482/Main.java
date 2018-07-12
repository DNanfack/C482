package C482;

import C482.View_Controller.AddPartController;
import C482.View_Controller.AddProductController;
import C482.View_Controller.MainScreenController;
import C482.View_Controller.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C482 Project");
//
//        initRootLayout();
//
//        showMainScreen(0);


        // Set root layout
//        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/RootLayout.fxml"));
//        Scene scene = new Scene(root);

//        stage.setScene(scene);
//        stage.show();

        // this.primaryStage = primaryStage;
        // this.primaryStage.setTitle("C482 Project");

        initRootLayout();
        showMainScreen(0);
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

        // Give the controller access to the main app.
        RootLayoutController controller = loader.getController();
        controller.setMain(this);
    }

    /**
     * Shows MainScreen inside the RootLayout
     */
    public void showMainScreen(int tabNumber) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        TabPane tabPane = new TabPane();
        tabPane.getSelectionModel().select(tabNumber);
        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);

        MainScreenController controller = loader.getController();
        controller.setMain(this);


    }

    public void showAddPart() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/AddPart.fxml"));
        AnchorPane addPart = loader.load();
        rootLayout.setCenter(addPart);

        // Give the controller access to the main app.
        AddPartController controller = loader.getController();
        controller.setMain(this);
    }

    public void showAddProduct() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/AddProduct.fxml"));
        AnchorPane addProduct = loader.load();
        rootLayout.setCenter(addProduct);

        // Give controller access to main
        AddProductController controller = loader.getController();
        controller.setMain(this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
