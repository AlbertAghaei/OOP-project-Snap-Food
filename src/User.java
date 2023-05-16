import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
abstract public class User
{
    int ID;
    String username;
    String password;
    String type;///owner or normal
    static User loggedInUser;
    static ArrayList<User> allUsers = new ArrayList<>();
    String securityQuestion;///enums might be needed
    String securityAnswer;
    User(int ID,String username, String password, String type)
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.type = type;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
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
    public static void getAllUsersFromDataBase()throws SQLException
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
                    allUsers.add(new Owner(id,username,pass,Class));
                else if(Class.equals("Normal"))
                    allUsers.add(new Normal(id,username,pass,Class,Node.getNodeByID(location),charge));
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
