package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainD {
    protected static Connection conection = null;
    public MainD() {
        this.conecta();
    }
    private void conecta() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException exc) {
                System.out.println("No driver detected! " + exc);
            }
            MainD.conection = DriverManager.getConnection("jdbc:mysql://192.168.56.102:3306/quboo", "maraka", "1560");
            System.out.println("You have been connected :)");
        } catch (java.sql.SQLException sqle) {
            System.out.println("ERROR: " + sqle);
        }
    }
    public static void desconecta() {
        if (MainD.conection != null) {
            try {
                MainD.conection.close();
                System.out.println("You have been desconnected ;) BYE BYE ");
            } catch (SQLException sqle) {
                System.out.println("CLOSE CONN ERROR: " + sqle);
            }
        }
    }
}