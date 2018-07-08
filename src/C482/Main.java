package C482;

import C482.View_Controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C482 Project");

        initRootLayout();
        showMainMenu();
    }

    /**
     * Initializes the root layout
     */
    public void initRootLayout() throws IOException {
        // Load root layout fxml file
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Show RootLayout scene
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows MainScreen inside the RootLayout
     */
    public void showMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        AnchorPane mainScreen = (AnchorPane) loader.load();

        // Set mainScreen to center of rootLayout
        rootLayout.setCenter(mainScreen);

        // Give the controller access to Main
        MainScreenController mainScreenController = loader.getController();
        mainScreenController.setMainApp(this);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
