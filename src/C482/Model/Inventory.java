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
        products.add(product);
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

    public void updatePart(int partID) {
        //TODO: Create method
    }
}
