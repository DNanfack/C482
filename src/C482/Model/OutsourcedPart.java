/*
 * Author: Taylor Vories
 * WGU C482 Project
 */

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

    /**
     * Setter
     * @param companyName Name of the company the part comes from
     */
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    /**
     * Getter
     * @return Returns string value of the company name
     */
    public String getCompanyName() {
        return this.companyName.getValue();
    }
}
