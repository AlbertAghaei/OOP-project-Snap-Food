import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Entrance
{
    public static void registerOwner(String username, String password) throws SQLException
    {
        if(User.findUserByUsername(username)!=null)
            System.out.println("USER WITH USERNAME EXISTS!");
        else
        {
            String query = "INSERT INTO users (Charge, class, Location, Pass, Username) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, 0);///default charge
            statement.setString(2, "Owner");
            statement.setInt(3, 0);///unknown
            statement.setString(4, password);
            statement.setString(5, username);
            int rowsInserted = statement.executeUpdate();
            User.allUsers.add(new Owner (User.allUsers.size()+1,username,password,"Owner"));
            if (rowsInserted > 0)
                System.out.println("OWNER REGISTERED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }

    private static void getOnlyUserFromDtaBase() {
    }

    public static void registerNormal(String username, String password) throws SQLException
    {
        if(User.findUserByUsername(username)!=null)
            System.out.println("USER WITH USERNAME EXISTS!");
        else
        {
            String query = "INSERT INTO users (Charge, class, Location, Pass, Username) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, 0);///default charge
            statement.setString(2, "Normal");
            statement.setInt(3, 0);///unknown
            statement.setString(4, password);
            statement.setString(5, username);
            int rowsInserted = statement.executeUpdate();
            User.allUsers.add(new Normal (User.allUsers.size()+1,username,password,"Normal",null,0.00));
            if (rowsInserted > 0)
                System.out.println("USER REGISTERED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }
    public static void loginOwner(String username, String password)
    {
         if(User.findUserByUsername(username)==null)
             System.out.println("THERE IS NO USER WITH THIS USERNAME!");
         else if(!User.findUserByUsername(username).password.equals(password))
             System.out.println("INCORRECT PASSWORD!");
         else if(!User.findUserByUsername(username).type.equals("Owner"))
             System.out.println("THIS USER IS NOT AN ADMIN!");
         else if(User.loggedInUser!=null)
             System.out.println("LOGOUT FIRST!");
         else
         {
             User.loggedInUser=User.findUserByUsername(username);
             System.out.println("ADMIN LOGGED IN SUCCESSFULLY!");
         }
    }
    public static void loginNormal(String username, String password)
    {
        if(User.findUserByUsername(username)==null)
            System.out.println("THERE IS NO USER WITH THIS USERNAME!");
        else if(!User.findUserByUsername(username).password.equals(password))
            System.out.println("INCORRECT PASSWORD!");
        else if(!User.findUserByUsername(username).type.equals("Normal"))
            System.out.println("THIS USER IS ADMIN");
        else if(User.loggedInUser!=null)
            System.out.println("LOGOUT FIRST!");
        else
        {
            User.loggedInUser=User.findUserByUsername(username);
            System.out.println("USER LOGGED IN SUCCESSFULLY!");
        }
    }
    public static void logout()
    {
         if(User.loggedInUser==null)
             System.out.println("THERE IS NO LOGGED IN USER!");
         else
         {
             User.loggedInUser = null;
             System.out.println("USER LOGGED OUT SUCCESSFULLY!");
         }
    }
}
