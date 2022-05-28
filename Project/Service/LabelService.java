package Service;
import Model.DBLayer;
import Model.Label;
import Model.ModelLayer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class LabelService {

    int success = 0;    //если put(), update(), delete() внесли изменения в таблицу, то success > 0 (соответствует "rows affected" в MySql)
    public Connection connection;
    private ModelLayer model = new DBLayer();

    public LabelService(Connection connection) {
        this.connection = connection;
    }

    public void read() throws SQLException, InterruptedException {
        Label label = new Label();
        System.out.print(" Enter id:  ");
        Scanner sc = new Scanner(System.in);

        try {
            label.id = sc.nextInt();
            label = model.getLabel(connection, label);
            if(label.id == 0 && label.name == null){
                System.out.println(" Nothing found!");
            }
            else {
                System.out.println(" Returned label " + label.name + " with id " + label.id);
            }
        }
        catch(InputMismatchException e) {
            System.out.println(" Enter a number!\n");
            TimeUnit.SECONDS.sleep(3);
            read();
        }
    }

    public int put() throws SQLException {
        System.out.print(" Enter the name of the label:  ");
        Scanner sc = new Scanner(System.in);
        Label label = new Label();
        label.name = sc.nextLine();
        ResultSet result =  model.searchLabelByName(connection, label);
        if (result.next()) {
            System.out.println(" DB already contains this label!");
            success = 0;
        } else {
            success = model.putLabel(connection, label);
        }
        return success;
    }

    public int update() throws SQLException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        Label updatedLabel = new Label();
        System.out.print (" What label would you like to update? (enter id):  ");

        try{
            updatedLabel.id = sc.nextInt();
            ResultSet result = model.searchLabelById(connection, updatedLabel);
            if(result.next()) {
                System.out.print(" Enter new name:  ");
                Scanner sc2 = new Scanner(System.in);
                updatedLabel.name = sc2.nextLine();
                success = model.updateLabel(connection, updatedLabel);
            }
            else {
                success = 0;
            }
        }
        catch (InputMismatchException e){
            System.out.println(" Enter a number!\n");
            TimeUnit.SECONDS.sleep(3);
            update();
        }
            return success;
    }

    public int delete() throws SQLException, InterruptedException {
        Label label = new Label();
        Scanner sc = new Scanner(System.in);
        System.out.print (" What label would you like to delete? (enter id):  ");
        try {
            label.id = sc.nextInt();
            ResultSet result = model.searchLabelById(connection, label);
            if(result.next()){
                success = model.deleteLabel(connection, label);
            }
            else {
                success = 0;
            }
        }
        catch(InputMismatchException e) {
            System.out.println(" Enter a number!\n");
            TimeUnit.SECONDS.sleep(3);
            delete();
        }
        return success;
    }
}