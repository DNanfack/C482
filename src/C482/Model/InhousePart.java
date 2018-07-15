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

    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }

    public int getMachineID() {
        return this.machineID.getValue();
    }
}
