import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bonus
{
    static ArrayList<Bonus> allBonuses = new ArrayList<>();
    int code;
    Normal user;
    boolean used ;
    Bonus(int code, Normal user, boolean used)
    {
        this.code = code;
        this.user = user;
        this.used = used;
    }
    public static Bonus findBonusByID(int id)
    {
        for(int i=0 ;i< allBonuses.size();i++)
            if(allBonuses.get(i).code==id)
                return allBonuses.get(i);
        return null;
    }
    public static void readBonusFromDataBase()throws SQLException
    {
        try
        {
            String query = "SELECT * FROM Bonus;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int code = resultSet.getInt("ID");
                boolean used = resultSet.getBoolean("used");
                int userID = resultSet.getInt("userID");

                Bonus created = new Bonus(code,(Normal)User.findUserByID(userID),used);
                allBonuses.add(created);

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
