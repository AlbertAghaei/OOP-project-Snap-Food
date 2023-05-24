import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;

public class Discount
{
    static ArrayList<Discount> allDiscounts = new ArrayList<>();
    int ID;
    int percent;
    Timestamp activationTime;
    int duration; ///in minutes
    boolean active;
    Discount(int ID,int percent, Timestamp activationTime, boolean activity, int duration)
    {
        this.ID = ID;
        this.percent = percent;
        this.activationTime = activationTime;
        this.active = activity;
        this.duration = duration;
    }
    public static Discount findDiscountByID(int id)
    {
        for(int i=0; i<allDiscounts.size(); i++)
          if (allDiscounts.get(i).ID==id)
              return allDiscounts.get(i);
        return null;
    }
    public static void getAllDiscountsFromDataBase()
    {
        try
        {
            String query = "SELECT * FROM discount;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                int id = resultSet.getInt("ID");
                Timestamp activationTime = resultSet.getTimestamp("activationTime");
                boolean activity = resultSet.getBoolean("activity");
                int duration = resultSet.getInt("duration");
                int percent = resultSet.getInt("percent");
                allDiscounts.add(new Discount(id,percent,activationTime,activity,duration));
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
