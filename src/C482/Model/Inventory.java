package C482.Model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Product> products;
    private ArrayList<Part> allParts;

    /**
     * Constructor
     */
    public Inventory() {
        this.products = new ArrayList<>();
        this.allParts = new ArrayList<>();
    }

    // ************ METHODS *************************

    public void addProduct(Product product) {
        //TODO: Create Method
    }

    public boolean removeProduct(int productID) {
        //TODO: Create method
        return false;
    }

    public Product lookupProduct(int productID) {
        //TODO: Create method
        return null;
    }

    public void updateProduct(int productID) {
        //TODO: Create method
    }

    public void addPart(Part part) {
        //TODO: Create method
    }

    public boolean deletePart(Part part) {
        //TODO: Create method
        return false;
    }

    public Part lookupPart(int partID) {
        //TODO: Create method
        return null;
    }

    public void updatePart(int partID) {
        //TODO: Create method
    }
}
