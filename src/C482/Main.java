package C482;

import C482.Model.InhousePart;
import C482.Model.Inventory;
import C482.Model.Part;
import C482.Model.Product;
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
    private BorderPane rootLayout;
    public static Inventory inventory;

    @Override
    public void start(Stage primaryStage) throws Exception {
        inventory = new Inventory();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C482 Project");

        // Add parts to product
        Product p1 = new Product();
        p1.setProductID(1);
        p1.setInStock(3);
        p1.setName("TestProduct1");
        p1.setPrice(142.19);
        p1.setMin(1);
        p1.setMax(32);
        InhousePart part1 = new InhousePart();
        part1.setMachineID(125);
        part1.setInStock(4);
        part1.setName("TestPart1");
        part1.setPrice(2.57);
        part1.setPartID(1);
        part1.setMin(1);
        part1.setMax(15);

        InhousePart part2 = new InhousePart();
        part2.setMachineID(14);
        part2.setInStock(5);
        part2.setName("TestPart2");
        part2.setPrice(1.27);
        part2.setPartID(2);
        part2.setMin(1);
        part2.setMax(10);

        InhousePart part3 = new InhousePart();
        part3.setMachineID(125);
        part3.setInStock(6);
        part3.setName("TestPart3");
        part3.setPrice(8.63);
        part3.setPartID(3);
        part3.setMin(1);
        part3.setMax(30);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p1.addAssociatedPart(part3);

        inventory.addProduct(p1);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);

        initRootLayout();
        showMainScreen("parts");
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
    public void showMainScreen(String tabName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/MainScreen.fxml"));
        AnchorPane mainScreen = loader.load();
        rootLayout.setCenter(mainScreen);

        MainScreenController controller = loader.getController();
        controller.setMain(this);
        // controller.setInventory(inventory);
        controller.selectTab(tabName);

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

    public void showModifyPart(Part partToModify) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View_Controller/ModifyPart.fxml"));
        AnchorPane modifyPart = loader.load();
        rootLayout.setCenter(modifyPart);

        // Give controller access to part and main app.
        ModifyPartController controller = loader.getController();
        controller.setMain(this);
        controller.setPartToModify(partToModify);
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
