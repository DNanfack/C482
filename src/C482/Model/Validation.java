/*
 * Author: Taylor Vories
 * WGU C482 Project
 * This class provides methods shared across the controllers to validate user input
 */

package C482.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Validation {

    /**
     * Validates that a name is not empty
     * @param name The String value provided by the user
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
    public static String validateName(String name) {
        StringBuilder errors = new StringBuilder();
        // Ensure name is not null
        if(name.isEmpty()) {
            errors.append("Name must contain at least 1 character.");
        }
        return errors.toString();
    }

    /**
     * Validates that the user input price is parsable and that it is positive.
     * It also prevents a user from accidentally entering a $-sign.
     * @param price The String value provided by the user
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
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

    /**
     * Validates that the minimum and maximum values provided by the user are
     * parsable integers and that they are positive.  It also verifies the
     * minimum value is not larger than the maximum value.
     * @param min The minimum value provided by the user
     * @param max The maximum value provided by the user
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
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

    /**
     * Validates that a product cannot be deleted if it has associated parts.
     * @param product The product to validate before deleting
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
    public static String validateDeleteProduct(Product product) {
        StringBuilder errors = new StringBuilder();
        if(product.getNumParts() > 0) {
            errors.append("Cannot delete Product with a part attached. Remove all parts and try again.");
        }
        return errors.toString();
    }

    /**
     * Validates that the product price cannot be lower than the total cost of parts
     * @param product The product to validate
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
    public static String validateProductPrice(Product product) {
        StringBuilder errors = new StringBuilder();
        if(product.getTotalCost() > product.getPrice()) {
            errors.append("Total cost of parts in product exceeds cost of product.  Either raise price of product or remove parts.");
            errors.append("\n");
            errors.append("Total cost: ");
            errors.append(product.getTotalCost());
            return errors.toString();
        }
        return errors.toString();
    }

    /**
     * Validates that the inventory is integer parsable and that it is between the minimum and maximum
     * values provided by the user.
     * @param numInventory The String value provided by the user
     * @param minValue The already verified minimum value provided by the user
     * @param maxValue The already verified maximum value provided by the user
     * @return Returns a list of errors if there are any and an empty string if there are no errors.
     */
    public static String validateInventory(String numInventory, String minValue, String maxValue) {
        StringBuilder errors = new StringBuilder();
        if(!intValid(numInventory)) {
            errors.append("Inventory must be in an integer format.");
            return errors.toString();
        }
        int parsedInv = Integer.parseInt(numInventory);
        int min = Integer.parseInt(minValue);
        int max = Integer.parseInt(maxValue);

        if(parsedInv < 1) {
            errors.append("Inventory must be a positive number.");
            return errors.toString();
        }
        if(parsedInv < min || parsedInv > max) {
            errors.append("Inventory must be between minimum (");
            errors.append(min);
            errors.append(") and maximum (");
            errors.append(max);
            errors.append(").");
            return errors.toString();
        }
        return errors.toString();
    }

    /**
     * Validates that an integer is parsable and catches NumberFormatExceptions
     * @param intValue The integer value provided by the user interface
     * @return Returns true if integer is valid, false if not.
     */
    public static boolean intValid(String intValue) {
        try {
            Integer.parseInt(intValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }

    /**
     * Validates that a double is parsable and catches NumberFormatExceptions
     * @param doubleValue The double value provided by the user interface
     * @return Returns true if double is valid, false if not.
     */
    private static boolean doubleValid(String doubleValue) {
        try {
            Double.parseDouble(doubleValue);
        } catch (NumberFormatException e ) {
            return false;
        }
        return true;
    }

    /**
     * Rounds the price value to 2 decimal places.
     * @param price The price value provided by the user interface
     * @return Returns a rounded double value
     */
    public static double getRoundedPrice(String price) {
        BigDecimal bd = new BigDecimal(price);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
