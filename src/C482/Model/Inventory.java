package C482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public void addProduct(Product product) {
        products.add(product);
        productID++;
    }

    /**
     * Searches for productID in products list and removes it if it finds it
     * @return Returns true if successfully removed, false if not found.
     */
    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public boolean removeProducts(ObservableList<Product> products) {
        return this.products.removeAll(products);
    }

    public boolean removePart(Part part) {
        return parts.remove(part);
    }

    public boolean removeParts(ObservableList<Part> parts) {
        return this.parts.removeAll(parts);
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

    public void updateProduct(Product productToUpdate) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getProductID() == productToUpdate.getProductID()) { // Match found
                products.set(i, productToUpdate);
                break;
            }
        }
    }

    public void addPart(Part part) {
        parts.add(part);
        partID++;
    }

    public boolean deletePart(Part part) {
        return parts.remove(part);
    }

    public Part lookupPart(int partID) {
        for(int i = 0; i < parts.size(); i++) {
            Part p = parts.get(i);
            if(p.getPartID() == partID) {
                return p;
            }
        }
        return null;
    }

    public int getProductID() {
        return ++productID;
    }

    public int getPartID() {
        return ++partID;
    }

    public void updatePart(Part partToUpdate) {
        for(int i = 0; i < parts.size(); i++) {
            if(parts.get(i).getPartID() == partToUpdate.getPartID()) { // Match found
                parts.set(i, partToUpdate);
                break;
            }
        }
    }

    public ObservableList<Part> getParts() {
        return this.parts;
    }
    public ObservableList<Product> getProducts() {
        return this.products;
    }
}
