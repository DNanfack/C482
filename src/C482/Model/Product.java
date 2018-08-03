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
    private final IntegerProperty numParts;

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
        this.numParts = new SimpleIntegerProperty();
    }

    /**
     * Takes a part and adds it to product
     * @param part part to add to the list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
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

    public void setProductID(int productID) {
        this.productID.set(productID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public int getProductID() {
        return productID.getValue();
    }

    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    public String getName() {
        return name.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    public int getInStock() {
        return inStock.getValue();
    }

    public int getMin() {
        return min.getValue();
    }

    public int getMax() {
        return max.getValue();
    }

    public int getNumParts() {
        return numParts.getValue();
    }
}
