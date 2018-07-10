package C482.Model;

public class InhousePart extends Part{
    private int machineID;

    /**
     * Constructor
     * @param machineID Machine ID
     */
    public InhousePart(int machineID) {
        super();
        this.machineID = machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public int getMachineID() {
        return this.machineID;
    }
}
