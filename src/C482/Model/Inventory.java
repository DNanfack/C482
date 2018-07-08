package C482.Model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    private ArrayList<Part> allParts;

    /**
     * Constructor
     */
    public Inventory() {
        products = new ArrayList<>();
        allParts = new ArrayList<>();
    }

    // ************ METHODS *************************

    public void addProduct(Product product) {
        //TODO: Create Method
    }

    public boolean removeProduct(int productID) {
        //TODO: Create method
    }

    public Product lookupProduct(int productID) {
        //TODO: Create method
    }

    public void updateProduct(int productID) {
        //TODO: Create method
    }

    public void addPart(Part part) {
        //TODO: Create method
    }

    public boolean deletePart(Part part) {
        //TODO: Create method
    }

    public Part lookupPart(int partID) {
        //TODO: Create method
    }

    public void updatePart(int partID) {
        //TODO: Create method
    }
}
