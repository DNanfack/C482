/*
 * Author: Taylor Vories
 * WGU C482 Project
 */

package C482.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InhousePart extends Part{
    private final IntegerProperty machineID;

    /**
     * Constructor
     */
    public InhousePart() {
        super();
        this.machineID = new SimpleIntegerProperty();
    }

    /**
     * Setter
     * @param machineID Integer ID.  Should be unique
     */
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }

    /**
     * Getter
     * @return returns machine ID
     */
    public int getMachineID() {
        return this.machineID.getValue();
    }
}
