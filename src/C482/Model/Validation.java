package C482.Model;

import java.util.ArrayList;

public class Validation {
    private ArrayList<String> results;

    public Validation() {
        results = new ArrayList<>();
    }

    /**
     * Validate Name
     * @param name
     */
    public void validateName(String name) {
        // Ensure name is not null
        if(name == null) {
            results.add("Name must contain at least 1 character.");
        }
    }

    // validate price is parsable
    public void validatePrice(String price) {
        try {
            Double p = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            results.add("Price not in proper format, IE: 2.57");
            return;
        }
        if(p < 0) {

        }
    }

    Validation results = new Validation();
    results.numErrors = numErrors;
    results.results = results;

}
