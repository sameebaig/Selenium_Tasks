package tests;

import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import utils.DBConnection;

public class DBTest {

    @Test
    public void testDatabaseConnection() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Password: " + rs.getString("password"));
            }

            DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
