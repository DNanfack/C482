package C482.Model;

public class OutsourcedPart extends Part {
    private String companyName;

    /**
     * Constructor
     * @param companyName Company Name
     */
    public OutsourcedPart(String companyName) {
        super();
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
}
