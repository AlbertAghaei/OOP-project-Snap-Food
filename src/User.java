import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
abstract public class User
{
    int ID;
    String username;
    Double charge;

    String password;
    String type;///Owner or Normal
    static User loggedInUser;
    static ArrayList<User> allUsers = new ArrayList<>();
    User(int ID,String username, String password, String type)
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.type = type;
    }
    User(){}
    public static User findUserByID(int ID)
    {
        for(int i=0; i<allUsers.size(); i++)
            if(allUsers.get(i).ID==ID)
                return allUsers.get(i);
        return null;
    }
    public static User findUserByUsername(String username)
    {
        for(int i=0; i<allUsers.size(); i++)
            if(allUsers.get(i).username.equals(username))
                return allUsers.get(i);
        return null;
    }
    public static void getHistoryAndOwnedRestaurantsFromDataBase()throws SQLException
    {
        try
        {
            String query = "SELECT * FROM users;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                double charge = resultSet.getDouble("Charge");
                String Class = resultSet.getString("class");
                int location = resultSet.getInt("Location");
                String pass = resultSet.getString("Pass");
                String username = resultSet.getString("Username");
                if(Class.equals("Owner"))
                {
                    try {
                        String query1 = "SELECT * FROM user_restaurant;";
                        Statement statement1 = SQL.connection.createStatement();
                        ResultSet resultSet1 = statement1.executeQuery(query1);
                        while (resultSet1.next()) {
                            if (resultSet1.getInt("userID") == id)
                                ((Owner)User.findUserByID(id)).ownedRestaurants.add(Restaurant.findRestaurantByID(resultSet1.getInt("restaurantID")));
                        }
                        resultSet1.close();
                        statement1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if(Class.equals("Normal"))
                {
                    try {
                        String query1 = "SELECT * FROM user_order;";
                        Statement statement1 = SQL.connection.createStatement();
                        ResultSet resultSet1 = statement1.executeQuery(query1);
                        while (resultSet1.next()) {
                            if (resultSet1.getInt("userID") == id)
                                ((Normal)User.findUserByID(id)).userHistory.add(Order.findOrderByID(resultSet1.getInt("orderID")));
                        }
                        resultSet1.close();
                        statement1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void getOnlyUserFromDtaBase()
    {
        try
        {
            String query = "SELECT * FROM users;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                double charge = resultSet.getDouble("Charge");
                String Class = resultSet.getString("class");
                int location = resultSet.getInt("Location");
                String pass = resultSet.getString("Pass");
                String username = resultSet.getString("Username");
                if(Class.equals("Owner"))
                {
                    Owner inuse = new Owner(id,username,pass,Class,charge);
                    allUsers.add(inuse);
                }
                else if(Class.equals("Normal"))
                {
                    Normal inuse = new Normal(id,username,pass,Class,Node.getNodeByID(location),charge);
                    allUsers.add(inuse);
                }
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void readDeliveryUserFromDataBase()throws SQLException
    {
        try
        {
            String query = "SELECT * FROM delivery_user;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int userID = resultSet.getInt("userID");
                String query1 = "SELECT * FROM delivery_user;";
                Statement statement1 = SQL.connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(query1);
                while (resultSet1.next())
                {
                    if(resultSet.getInt("userID")==userID)
                        ((Normal)User.findUserByID(userID)).deliver = Order.findOrderByID(resultSet1.getInt("orderID"));
                }
                resultSet1.close();
                statement1.close();
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
