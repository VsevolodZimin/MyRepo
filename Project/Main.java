import Controller.LabelController;
import Model.Label;
import Service.LabelService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException {

        LabelController controller = new LabelController();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        System.out.println("\n WELCOME TO THE TESTDB!");
        while (!exit) {
            int success = 0;
            System.out.println(
                    "\n ___________________________" +
                    "\n What would you like to do? " +
                    "\n ___________________________" +
                    "\n A.    Get a label" +
                    "\n B.    Put a label" +
                    "\n C.    Update a label" +
                    "\n D.    Delete a label" +
                    "\n E.    Exit" +
                    "\n____________________________");
            System.out.print(" Enter your choice:  ");

            switch (sc.nextLine().toUpperCase()) {
                case "A" -> {
                    controller.connect();
                    controller.getService().read();
                    controller.disconnect();
                }
                case "B" -> {
                    controller.connect();
                    success = controller.getService().put();
                    if (success != 0) System.out.println(" A new label added!");
                    else System.out.println(" Insertion failed!");
                    controller.disconnect();
                }
                case "C" -> {
                    controller.connect();
                    success = controller.getService().update();
                    if (success != 0) {
                        System.out.println(" Successfully updated!");
                    } else {
                        System.out.println(" Update failed!");
                    }
                    controller.disconnect();
                }
                case "D" -> {
                    controller.connect();
                    success = controller.getService().delete();
                    if (success != 0) {
                        System.out.println(" Successfully deleted!");
                    } else {
                        System.out.println(" Failed to delete!");
                    }
                    controller.disconnect();
                }
                case "E" -> {
                    exit = true;
                    System.out.println(" Have a nice day!");
                }
                default -> System.out.println(" Enter a valid value!");
            }
            TimeUnit.SECONDS.sleep(3);
        }
    }
}