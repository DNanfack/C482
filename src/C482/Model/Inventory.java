/*
 * Author: Taylor Vories
 * WGU C482 Project
 */
package C482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory to hold products and parts.
 */
public class Inventory {
    private ObservableList<Product> products;
    private ObservableList<Part> parts;
    private int productID;
    private int partID;

    /**
     * Constructor
     */
    public Inventory() {
        this.products = FXCollections.observableArrayList();
        this.parts = FXCollections.observableArrayList();
    }

    // ************ METHODS *************************

    /**
     * Adds product to inventory.
     * @param product Product to add
     */
    public void addProduct(Product product) {
        products.add(product);
        productID++;
    }

    /**
     * Takes a single product and removes it from inventory
     * @param product product to remove
     * @return Returns true if successfully removed, false if not found.
     */
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    /**
     * Takes a list of products and removes them from inventory
     * @param products list of products to remove
     * @return true if successful
     */
    public boolean removeProducts(ObservableList<Product> products) {
        return this.products.removeAll(products);
    }

    /**
     * Takes a single part and removes it from inventory
     * @param part part to remove
     * @return Returns true if successfully removed.
     */
    public boolean removePart(Part part) {
        return parts.remove(part);
    }

    /**
     * Takes a list of parts and removes them from inventory
     * @param parts List of parts to remove
     * @return returns true if successful
     */
    public boolean removeParts(ObservableList<Part> parts) {
        return this.parts.removeAll(parts);
    }

    /**
     * Searches for a product based on ID.  I did not use this in my project.
     * @param productID Integer ID of the product to remove
     * @return returns true if successfully removed.
     */
    public Product lookupProduct(int productID) {
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if(p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }

    /**
     * Takes a new product and replaces it if there is a match by ID
     * @param productToUpdate New product to replace old product.
     * @return Returns true if successfully updated.
     */
    public boolean updateProduct(Product productToUpdate) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductID() == productToUpdate.getProductID()) { // Match found
                products.set(i, productToUpdate);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a part to inventory
     * @param part Part to add.
     */
    public void addPart(Part part) {
        parts.add(part);
        partID++;
    }

    /**
     * Takes an integer unique ID and searches through the inventory to find it.
     * @param partID Unique integer value
     * @return returns part if found, null if not found.
     */
    public Part lookupPart(int partID) {
        for (Part p : parts) {
            if (p.getPartID() == partID) {
                return p;
            }
        }
        return null;
    }

    /**
     * Getter
     * @return returns a unique product ID.
     */
    public int getProductID() {
        return ++productID;
    }

    /**
     * Getter
     * @return returns a unique part ID
     */
    public int getPartID() {
        return ++partID;
    }

    /**
     * Updates a part and replaces it in the inventory.
     * @param partToUpdate the updated part to replace the existing part.
     */
    public void updatePart(Part partToUpdate) {
        for(int i = 0; i < parts.size(); i++) {
            if(parts.get(i).getPartID() == partToUpdate.getPartID()) { // Match found
                parts.set(i, partToUpdate);
                break;
            }
        }
    }

    /**
     * Returns the full inventory of parts
     * @return  ObservableList of all parts
     */
    public ObservableList<Part> getParts() {
        return this.parts;
    }

    /**
     * Returns the full inventory of products
     * @return ObservableList of all products
     */
    public ObservableList<Product> getProducts() {
        return this.products;
    }
}
