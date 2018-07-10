package C482.Model;

import java.util.ArrayList;

public class Product {
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    /**
     * Constructor
     */
    public Product() {
        //TODO: Write constructor
    }

    /**
     * Takes a part and adds it to product
     * @param part
     */
    public void addAssociatedPart(Part part){
        //TODO: Write method
    }

    /**
     * Takes a part ID and removes the part from the product.
     * @param partID ID of the part to remove
     * @return Returns TRUE if successfully removed, false if not found.
     */
    public boolean removeAssociatedPart(int partID) {
        //TODO: Write method
        return true; //REMOVE
    }

    /**
     * Takes a part ID and searches if the part is in the product.
     * @param partID ID of part to lookup
     * @return Returns Part if found, otherwise returns null
     */
    public Part lookupAssociatedPart(int partID) {
        //TODO: Write Method
        return null;
    }

    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getInStock() {
        return inStock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
