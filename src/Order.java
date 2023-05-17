import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Order
{
    static ArrayList<Order> allOrders = new ArrayList<>();
    int ID;
    ArrayList<Food> orderedFoods ;
    Double totalPrice;
    String status;///sent or in the way
    String timeLeft;
    String timeDelivered;
    Order(int ID,ArrayList<Food> cart,Double totalPrice,String status)
    {
        this.ID = ID;
        this.orderedFoods = cart;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    public static Order findOrderByID(int ID)
    {
        for(int i=0; i<allOrders.size(); i++)
            if(allOrders.get(i).ID==ID)
                return allOrders.get(i);
        return null;
    }
    public static void getAllOrdersFromDataBase()throws SQLException
    {
        try
        {
            String query = "SELECT * FROM orders;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = 0;
            while (resultSet.next())
            {
                id = resultSet.getInt("ID");
                double price = resultSet.getDouble("totallPrice");
                String status = resultSet.getString("orderStatus");
                try
                {
                    String query1 = "SELECT * FROM order_food;";
                    Statement statement1 = SQL.connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery(query1);
                    ArrayList<Food> foods = new ArrayList<>();///////////////////////read foods from data base
                    while (resultSet1.next())
                        if(resultSet1.getInt("orderID")==id)
                          foods.add(Food.findFoodByID(resultSet.getInt("foodID")));
                    allOrders.add(new Order(id,foods,price,status));
                    resultSet1.close();
                    statement1.close();
                }
                catch (SQLException e)
                {
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
