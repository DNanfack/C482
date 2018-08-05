package C482.Model;

import java.util.ArrayList;

public class Validation {

    public static String validateName(String name) {
        StringBuilder errors = new StringBuilder();
        // Ensure name is not null
        if(name.isEmpty()) {
            errors.append("Name must contain at least 1 character.");
        }
        return errors.toString();
    }

    public static String validatePrice(String price) {
        StringBuilder errors = new StringBuilder();
        double dPrice;

        // Make sure price doesn't contain dollar sign
        if(price.contains("$")){
            errors.append("Please do not include dollar sign in price");
            return errors.toString();
        }
        // Validate price is parsable
        if(!doubleValid(price)) {
            errors.append("Price is not in valid format.");
            return errors.toString();
        }
        dPrice = Double.parseDouble(price);

        // Validate price is positive
        if(dPrice < 0 ) {
            errors.append("Price must be greater than 0.");
        }

        return errors.toString();
    }

    public static String validateMinMax(String min, String max) {
        int minInt;
        int maxInt;
        StringBuilder errors = new StringBuilder();

        if(intValid(min) && intValid(max)) { // Both min and max are valid integers
            minInt = Integer.parseInt(min);
            maxInt = Integer.parseInt(max);

            // validate max is positive
            if(maxInt < 0) {
                errors.append("Max value must be greater than 0.");
                return errors.toString();
            }

            // validate min is positive
            if(minInt < 0) {
                errors.append("Min value must be greater than 0.");
                return errors.toString();
            }

            // validate max is greater than min
            if(maxInt < minInt ) {
                errors.append("Min value must be less than Max value.");
                return errors.toString();
            }
        } else {
            errors.append("Invalid format.  Please enter integer.");
            return errors.toString();
        }
        return errors.toString();
    }

    public static String validateDeleteProduct(Product product) {
        StringBuilder errors = new StringBuilder();
        if(product.getNumParts() > 0) {
            errors.append("Cannot delete Product with a part attached. Remove all parts and try again.");
        }
        return errors.toString();
    }

    public static String validateInventory(String numInventory) {
        StringBuilder errors = new StringBuilder();
        if(!intValid(numInventory)) {
            errors.append("Inventory must be in an integer format.");
            return errors.toString();
        }
        int parsedInv = Integer.parseInt(numInventory);
        if(parsedInv < 1) {
            errors.append("Inventory must be a positive number.");
            return errors.toString();
        }
        return errors.toString();
    }

    public static boolean intValid(String intValue) {
        try {
            Integer.parseInt(intValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }

    public static boolean doubleValid(String doubleValue) {
        try {
            Double.parseDouble(doubleValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }
}
