import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Entrance
{
    public static void registerOwner(String username, String password) throws SQLException
    {
        if(User.loggedInUser!=null)
            System.out.println("LOGOUT FIRST!");
        else if(User.findUserByUsername(username)!=null)
            System.out.println("USER WITH USERNAME EXISTS!");
        else
        {
            String query = "INSERT INTO users (Charge, class, Location, Pass, Username,ans1,ans2) VALUES ( ?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, 0);///default charge
            statement.setString(2, "Owner");
            statement.setInt(3, 0);///unknown
            statement.setString(4, password);
            statement.setString(5, username);
            System.out.println("SECURITY QUESTIONS:");
            System.out.println(SecurityQuestions.IN_WHAT_CITY_WHERE_YOU_BORN);
            String ans1 = Main.input.nextLine();
            System.out.println(SecurityQuestions.WHAT_IS_YOUR_FAVORITE_BOOK);
            String ans2 = Main.input.nextLine();
            statement.setString(6, ans1);
            statement.setString(7, ans2);
            int rowsInserted = statement.executeUpdate();
            User.allUsers.add(new Owner (User.allUsers.size()+1,username,password,"Owner",0.0));
            if (rowsInserted > 0)
                System.out.println("OWNER REGISTERED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }

    public static void registerNormal(String username, String password) throws SQLException
    {
        if(User.loggedInUser!=null)
            System.out.println("LOGOUT FIRST!");
        else if(User.findUserByUsername(username)!=null)
            System.out.println("USER WITH USERNAME EXISTS!");
        else
        {
            String query = "INSERT INTO users (Charge, class, Location, Pass, Username,ans1,ans2) VALUES ( ?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, 0);///default charge
            statement.setString(2, "Normal");
            statement.setInt(3, 0);///unknown
            statement.setString(4, password);
            statement.setString(5, username);
            System.out.println("SECURITY QUESTIONS:");
            System.out.println(SecurityQuestions.IN_WHAT_CITY_WHERE_YOU_BORN);
            String ans1 = Main.input.nextLine();
            System.out.println(SecurityQuestions.WHAT_IS_YOUR_FAVORITE_BOOK);
            String ans2 = Main.input.nextLine();
            statement.setString(6, ans1);
            statement.setString(7, ans2);
            int rowsInserted = statement.executeUpdate();
            User.allUsers.add(new Normal (User.allUsers.size()+1,username,password,"Normal",null,0.00));
            if (rowsInserted > 0)
                System.out.println("USER REGISTERED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }
    public static void loginOwner(String username, String password)throws SQLException
    {
         if(User.findUserByUsername(username)==null)
             System.out.println("THERE IS NO USER WITH THIS USERNAME!");
         else if(!User.findUserByUsername(username).password.equals(password))
         {
             System.out.println("INCORRECT PASSWORD!   ANSWER SECURITY QUESTIONS TO RESET YOUR PASSWORD:");
         passwordRecovery(username);
         }
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
    public static void loginNormal(String username, String password)throws SQLException
    {
        if(User.findUserByUsername(username)==null)
            System.out.println("THERE IS NO USER WITH THIS USERNAME!");
        else if(!User.findUserByUsername(username).password.equals(password))
        {
            System.out.println("INCORRECT PASSWORD!   ANSWER SECURITY QUESTIONS TO RESET YOUR PASSWORD:");
            passwordRecovery(username);
        }
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
             Comment.commentInUse = null;
             Food.foodInuse = null;
             Order.orderInUse = null;
             Restaurant.restaurantInuse = null;
             System.out.println("USER LOGGED OUT SUCCESSFULLY!");
         }
    }
    public static void passwordRecovery(String username)throws SQLException
    {
        User dumbUser = User.findUserByUsername(username);
        System.out.println("SECURITY QUESTIONS:");
        System.out.println(SecurityQuestions.IN_WHAT_CITY_WHERE_YOU_BORN);
        String ans1 = Main.input.nextLine();
        System.out.println(SecurityQuestions.WHAT_IS_YOUR_FAVORITE_BOOK);
        String ans2 = Main.input.nextLine();

        String query = "SELECT ans1, ans2 FROM users WHERE ID = ?";
        PreparedStatement statement = SQL.connection.prepareStatement(query);
        statement.setInt(1, dumbUser.ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
        {
            String Ans1 = resultSet.getString("ans1");
            String Ans2 = resultSet.getString("ans2");

            if(!Ans1.trim().equals(ans1.trim()) || !Ans2.trim().equals(ans2.trim()))
                System.out.println("ONE OR BOTH OF YOUR ANSWERS DOES NOT MATCH!");
            else
            {
                System.out.println("ENTER YOUR NEW PASSWORD:");
                String pass = Main.input.nextLine();
                System.out.println("NEW PASSWORD SET SUCCESSFULLY!");
                dumbUser.password = pass;

                String query1 = "UPDATE users SET pass = ? WHERE ID = ?";
                PreparedStatement statement1 = SQL.connection.prepareStatement(query1);
                statement1.setString(1, pass);
                statement1.setInt(2,dumbUser.ID);
                int rowsUpdated = statement1.executeUpdate();

                if (rowsUpdated <= 0)
                    System.out.println("Failed to make connection!");
            }
        }
    }
}
