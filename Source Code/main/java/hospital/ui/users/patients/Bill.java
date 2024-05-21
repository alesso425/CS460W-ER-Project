package hospital.ui.users.patients;

import hospital.ui.labs.Lab;
import java.io.Serializable;

import java.io.Serializable;

/**
 * Generates a bill for medical services rendered to a patient, including costs
 * associated with their stay and any laboratory tests conducted. This class
 * provides a method to create a formatted bill as a string.
 */
public class Bill implements Serializable {
    private final int formatSpaces = 40;

    /**
     * Constructs the cost summary of all laboratory tests.
     * This includes the name of each lab, how many times it was run, and the total cost.
     *
     * @param labs An array of Lab objects representing the laboratory tests to be billed.
     * @return A formatted string detailing each lab's costs.
     */
    private String labCosts(Lab[] labs) {
        StringBuilder result = new StringBuilder();

        for (Lab lab : labs) {
            String s1 = lab.getName() + "(" + lab.getTimesRun() + "x)";
            String s2 = lab.getTotalCost() + "$\n";
            result.append(formatBill(s1, s2, formatSpaces));
        }

        return result.toString();
    }

    /**
     * Formats a single line of the bill with a specified total number of spaces between the
     * description and the cost. Ensures the bill is neatly aligned.
     *
     * @param s1 The description or name of the cost item.
     * @param s2 The cost associated with the item, as a string.
     * @param totalSpaces The total length of the line, ensuring alignment in the bill.
     * @return A single line of the bill, properly formatted.
     */
    private String formatBill(String s1, String s2, int totalSpaces) {
        StringBuilder result = new StringBuilder(s1);
        int usedSpaces = s1.length() + s2.length();

        for (int i = 0; i < totalSpaces - usedSpaces; i++) {
            result.append(" ");
        }

        result.append(s2);
        return result.toString();
    }

    /**
     * Generates a complete bill for a patient, formatted as a string. This bill includes
     * detailed costs for the patient's stay and any laboratory tests, formatted for easy reading.
     *
     * @param patient The patient for whom the bill is being generated.
     * @return The complete, formatted bill as a string.
     */
    public String toBill(Patient patient) {
        String bill = "";
        double flatRate = 850; // It seems flatRate is intended but not used in calculations. Consider integrating or removing.
        double dayRate = 10900;
        double stayCost = patient.getStay().getDays() * dayRate;
        double labsCost = 0;

        for (Lab lab : patient.getLabPanel().getLabs()) {
            labsCost += lab.getTotalCost();
        }

        bill += "Patient: " + patient.getLastName() + ", " + patient.getFirstName() + "\n";
        bill += "#################COSTS:#################\n";
        bill += "\n-----------------Stay:------------------\n";
        bill += formatBill("-Base ", dayRate + "$\n", formatSpaces);
        bill += formatBill("-Days: " + patient.getStay().getDays(), stayCost + "$\n", formatSpaces);
        bill += "\n-----------------LABS:------------------\n";
        bill += labCosts(patient.getLabPanel().getLabs());
        bill += "\n########################################\n\n";
        bill += formatBill("Insurance:", patient.getInsurancePlan(), formatSpaces);
        bill += "\n\n########################################";
        bill += "\n\n\n";
        bill += formatBill("Total:", Double.toString(labsCost + stayCost + dayRate) + "$\n", formatSpaces);

        return bill;
    }
}





