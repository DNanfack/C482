/*
 * Author: Taylor Vories
 * WGU C482 Project
 */

package C482.Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private final IntegerProperty productID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    /**
     * Constructor
     */
    public Product() {
        this.associatedParts = FXCollections.observableArrayList();
        this.productID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.inStock = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
        this.max = new SimpleIntegerProperty();
    }

    /**
     * Takes a part and adds it to product
     * @param part part to add to the list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Takes a part and removes the part from the product.
     * @param part Part you want to remove
     * @return Returns TRUE if successfully removed, false if not found.
     */
    public boolean removeAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    /**
     * Takes a list of parts to remove from the product
     * @param parts ObservableList of parts to remove
     * @return Returns true if successfully removed.
     */
    public boolean removeAssociatedParts(ObservableList<Part> parts) {
        return associatedParts.removeAll(parts);
    }

    /**
     * Takes a list of parts and sets them as the products associated parts.
     * This will replace any existing parts.
     * @param parts List of parts to associate with product.
     * @return Returns true if successful
     */
    public boolean setAssociatedParts(ObservableList<Part> parts) {
        return this.associatedParts.setAll(parts);
    }

    /**
     * Takes a part ID and searches if the part is in the product.
     * @param partID ID of part to lookup
     * @return Returns Part if found, otherwise returns null
     */
    public Part lookupAssociatedPart(int partID) {
        for(Part part: associatedParts) {
            if(part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Sets product ID.  This should be a unique value.
     * @param productID Integer value
     */
    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    /**
     * Setter
     * @param name Name of the product.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Setter
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Setter
     * @param inStock The number of products in the inventory
     */
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    /**
     * Setter
     * @param min the minimum allowed items in the inventory
     */
    public void setMin(int min) {
        this.min.set(min);
    }

    /**
     * Setter
     * @param max the maximum number of allowed items in the inventory
     */
    public void setMax(int max) {
        this.max.set(max);
    }

    /**
     * Getter
     * @return Gets the product ID
     */
    public int getProductID() {
        return productID.getValue();
    }

    /**
     * Gets a full list of associated parts
     * @return Returns an ObservableList of associated parts
     */
    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    /**
     * Getter
     * @return String value of name
     */
    public String getName() {
        return name.getValue();
    }

    /**
     * Getter
     * @return Returns the price of the product
     */
    public double getPrice() {
        return price.getValue();
    }

    /**
     * Getter
     * @return Returns total number of products in stock
     */
    public int getInStock() {
        return inStock.getValue();
    }

    /**
     * Getter
     * @return Returns minimum number of allowed items in inventory
     */
    public int getMin() {
        return min.getValue();
    }

    /**
     * Getter
     * @return Returns maximum number of allowed items in inventory
     */
    public int getMax() {
        return max.getValue();
    }

    /**
     * Getter
     * @return Returns the total cost of all parts associated with the product
     */
    public double getTotalCost() {
        double totalCost = 0;
        for(Part part: associatedParts) {
            totalCost += part.getPrice();
        }
        return totalCost;
    }

    /**
     * Getter
     * @return Returns the number of associated parts.
     */
    public int getNumParts() {
        return associatedParts.size();
    }
}
