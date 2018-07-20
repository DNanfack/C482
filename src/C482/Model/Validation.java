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
        double p;

        if(doubleValid(price)) {
            p = Double.parseDouble(price);
        } else {
            return;
        }
        if(p < 0) {
            results.add("Price must be greater than 0.");
        }
    }

    public void validateMinMax(String min, String max) {
        int minInt;
        int maxInt;

        if(intValid(min) && intValid(max)) { // Both min and max are valid integers
            minInt = Integer.parseInt(min);
            maxInt = Integer.parseInt(max);

            // validate max is positive
            if(maxInt < 0) {
                results.add("Max value must be greater than 0.");
            }

            // validate min is positive
            if(minInt < 0) {
                results.add("Min value must be greater than 0.");
            }

            // validate max is greater than min
            if(maxInt < minInt ) {
                results.add("Min value must be less than Max value.");
            }
        }
    }

    public void validateProduct(Product prodValue) {
        // validate product has at least one part
        if(prodValue.getNumParts() < 1) {
            results.add("Product must contain at least one part.");
        }
    }

    public void validateDeleteProduct(Product prodValue) {
        if(prodValue.getNumParts() > 0) {
            results.add("Cannot delete Product with a part attached.  Remove part and try again.");
        }
    }

    public int getNumErrors() {
        return results.size();
    }

    public void runAllValidation(String name, String price, String inStock, String min, String max) {
        validateName(name);
        validatePrice(price);
    }

    //TODO: Add required option for product name, price, and inventory

    private boolean intValid(String intValue) {
        try {
            Integer.parseInt(intValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }

    private boolean doubleValid(String doubleValue) {
        try {
            Double.parseDouble(doubleValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }
}
