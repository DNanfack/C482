package C482.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OutsourcedPart extends Part {
    private final StringProperty companyName;

    /**
     * Constructor
     */
    public OutsourcedPart() {
        super();
        this.companyName = new SimpleStringProperty();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getCompanyName() {
        return this.companyName.getValue();
    }
}
