import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Entrance
{
    static int counter = 0;
    public static void registerOwner(String username, String password) throws SQLException
    {
        counter++;
        String query = "INSERT INTO users (ID, Charge, class, Location, Pass, Username) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = SQL.connection.prepareStatement(query);
        statement.setInt(1, counter);
        statement.setDouble(2, 0);///default charge
        statement.setString(3, "Owner");
        statement.setInt(4, 0);///unknown
        statement.setString(5, password);
        statement.setString(6, username);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new row has been inserted.");
        }
         else {
        System.out.println("Failed to make connection!");
        }
    }
}
