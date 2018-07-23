package C482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Inventory {
    private ObservableList<Product> products;
    private ObservableList<Part> allParts;
    private int productID;
    private int partID;

    /**
     * Constructor
     */
    public Inventory() {
        this.products = FXCollections.observableArrayList();
        this.allParts = FXCollections.observableArrayList();
    }

    // ************ METHODS *************************

    public void addProduct(Product product) {
        products.add(product);
        productID++;
    }

    /**
     * Searches for productID in products list and removes it if it finds it
     * @param productID ID of product to remove
     * @return Returns true if successfully removed, false if not found.
     */
    public boolean removeProduct(int productID) {
        // For each item in products ArrayList
        for(int i = 0; i < products.size(); i++) {
            // If productID exists in array
            if(products.get(i).getProductID() == productID) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public Product lookupProduct(int productID) {
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if(p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }

    public void updateProduct(int productID) {
        Product p = lookupProduct(productID);
        if(p != null){
            p.setProductID(productID);
        }
    }

    public void addPart(Part part) {
        allParts.add(part);
        partID++;
    }

    public boolean deletePart(Part part) {
        return allParts.remove(part);
    }

    public Part lookupPart(int partID) {
        for(int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);
            if(p.getPartID() == partID) {
                return p;
            }
        }
        return null;
    }

    public int getProductID() {
        return productID;
    }

    public int getPartID() {
        return partID;
    }

    public void updatePart(int partID) {
        //TODO: Create method
    }

    public ObservableList<Part> getAllParts() {
        return this.allParts;
    }
}
