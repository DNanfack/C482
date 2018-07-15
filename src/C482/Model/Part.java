package C482.Model;

import javafx.beans.property.*;

public abstract class Part {
    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Part() {
        this.partID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.inStock = new SimpleIntegerProperty();
        this.min = new SimpleIntegerProperty();
        this.max = new SimpleIntegerProperty();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
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

    public int getPartID() {
        return partID.getValue();
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
}
