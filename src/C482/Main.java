package C482;

import C482.Model.InhousePart;
import C482.Model.Inventory;
import C482.Model.OutsourcedPart;
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
    public BorderPane rootLayout;
    private Inventory inventory;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.inventory = new Inventory();
        generateDummyData();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("C482 Inventory Project");
        rootLayout = new BorderPane();

        initRootLayout();
        showMainScreen("parts", inventory, rootLayout);
    }

    public void generateDummyData() {
        //<editor-fold desc="DummyDataCreation">
        // Add parts to product
        Product p1 = new Product();
        p1.setProductID(12);
        p1.setInStock(3);
        p1.setName("TestProduct1");
        p1.setPrice(142.19);
        p1.setMin(1);
        p1.setMax(32);

        Product p2 = new Product();
        p2.setProductID(6);
        p2.setInStock(6);
        p2.setName("TestProduct3");
        p2.setPrice(20.21);
        p2.setMin(4);
        p2.setMax(20);

        Product p3 = new Product();
        p3.setProductID(6);
        p3.setInStock(6);
        p3.setName("TestProduct4");
        p3.setPrice(40.12);
        p3.setMin(4);
        p3.setMax(20);

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

        OutsourcedPart part4 = new OutsourcedPart();
        part4.setCompanyName("Test Company 1");
        part4.setInStock(9);
        part4.setName("TestPart4");
        part4.setPrice(125.32);
        part4.setPartID(4);
        part4.setMin(1);
        part4.setMax(214);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p1.addAssociatedPart(part3);

        p2.addAssociatedPart(part4);
        p2.addAssociatedPart(part1);

        p3.addAssociatedPart(part2);

        inventory.addProduct(p1);
        inventory.addProduct(p2);
        inventory.addProduct(p3);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        inventory.addPart(part4);
        //</editor-fold>
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
