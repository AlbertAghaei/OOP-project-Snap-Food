import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Rate
{
    static ArrayList<Rate> allRates = new ArrayList<>();
    int ID;
    Rate rateInUse;
    int stars;///1-5
    Normal user;
    Rate(int ID,int stars, Normal user)
    {
        this.ID = ID;
        this.stars = stars;
        this.user = user;
    }
    public static Rate findRateByID(int id)
    {
        for(int i=0; i<allRates.size(); i++)
            if(allRates.get(i).ID == id)
                return allRates.get(i);
        return null;
    }
    public static void getAllRatingsFromDataBase()throws SQLException
    {
        try
        {
            String query = "SELECT * FROM rating;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                int stars = resultSet.getInt("stars");
                int userID = resultSet.getInt("userID");/////////////get users from database
                allRates.add(new Rate(id,stars,(Normal)User.findUserByID(userID)));
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
