package C482.Model;

public abstract class Part {
    protected int partID;
    protected String name;
    protected double price;
    protected int inStock;
    protected int min;
    protected int max;

    public void setName(String name) {
        this.name = name;
    }

    public void setPartID(int partID) {
        this.partID = partID;
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

    public int getPartID() {
        return partID;
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
