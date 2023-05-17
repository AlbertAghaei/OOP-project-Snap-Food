import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Food
{
    static ArrayList<Food> foods = new ArrayList<>();
    String name;
    int ID;
    Double price;
    String foodType;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<Rate> ratings = new ArrayList<>();
    Boolean active;
    static Food foodInuse;
    Discount discount;
    Food(int ID, String name,Double price,String type,boolean activity)
    {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.foodType = type;
        this.active = activity;
    }
    public static Food findFoodByID(int ID)
    {
        for(int i=0; i<foods.size(); i++)
            if(foods.get(i).ID==ID)
                return foods.get(i);
        return null;
    }
    public static void getAllFoodsFromDataBaseAndRatesAndComments()throws SQLException
    {
            try
            {
                String query = "SELECT * FROM food;";
                Statement statement = SQL.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    double price = resultSet.getDouble("price");
                    String type = resultSet.getString("foodType");
                    boolean activity = resultSet.getBoolean("activity");
                    String name = resultSet.getString("title");
                    foods.add(new Food(id, name, price, type, activity));
                    try {
                        String query1 = "SELECT * FROM food_rating;";
                        Statement statement1 = SQL.connection.createStatement();
                        ResultSet resultSet1 = statement1.executeQuery(query1);
                        while (resultSet1.next()) {
                            if (resultSet1.getInt("foodID") == id)///////////read all rates from sql
                                findFoodByID(id).ratings.add(Rate.findRateByID(resultSet1.getInt("rateID")));
                        }
                        resultSet1.close();
                        statement1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        String query1 = "SELECT * FROM food_comment;";
                        Statement statement1 = SQL.connection.createStatement();
                        ResultSet resultSet1 = statement1.executeQuery(query1);
                        while (resultSet1.next()) {
                            if (resultSet1.getInt("foodID") == id)/////////////read all comments from sql
                                findFoodByID(id).comments.add(Comment.findCommentByID(resultSet1.getInt("commentID")));
                        }
                        resultSet1.close();
                        statement1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
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
}
