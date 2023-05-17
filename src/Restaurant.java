import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Restaurant
{
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    int ID;
    Restaurant restaurantInuse;
    String name;
    ArrayList<String> foodTypes;
    ArrayList<Food> menu = new ArrayList<>();
    ArrayList<Order> restaurantHistory = new ArrayList<>();
    Node location;
    Owner owner;
    Restaurant(int ID,String name, ArrayList<String> types, Node location,Owner user )
    {
        this.ID = ID;
        this.name = name;
        this.foodTypes = types;
        this.location = location;
        this.owner = user;
    }
    public static Restaurant findRestaurantByID(int ID)
    {
        for(int i=0; i<restaurants.size(); i++)
            if(restaurants.get(i).ID==ID)
                return restaurants.get(i);
        return null;
    }
    public static void getAllRestaurantsFromDataBaseAndTypesAndMenuAndHistory()throws SQLException
    {
        int id = 0;
        try
        {
            String query = "SELECT * FROM restaurants;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                id = resultSet.getInt("ID");
                int location = resultSet.getInt("location");
                int Ownerid = resultSet.getInt("ownerID");
                String name = resultSet.getString("title");
                  try
                  {
                      String query1 = "SELECT * FROM restaurant_type;";
                      Statement statement1 = SQL.connection.createStatement();
                      ResultSet resultSet1 = statement1.executeQuery(query1);
                      ArrayList<String> types = new ArrayList<>();
                      while (resultSet1.next())
                          if(resultSet1.getInt("restaurantID")==id)
                              types.add(resultSet1.getString("foodType"));
                      User.getAllUsersFromDataBase();
                      restaurants.add( new Restaurant(id,name,types,Node.getNodeByID(location),(Owner)User.findUserByID(Ownerid)));
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
        try
        {
            String query = "SELECT * FROM restaurant_order;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Order> Orders = new ArrayList<>();//////////////get orders from data base
            while (resultSet.next())
                if(resultSet.getInt("restaurantID")==id)
                    Orders.add(Order.findOrderByID(resultSet.getInt("orderID")));
            findRestaurantByID(id).restaurantHistory=Orders;
            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            String query = "SELECT * FROM restaurant_food;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Food> Foods = new ArrayList<>();///////////////read foods from data base
            while (resultSet.next())
                if(resultSet.getInt("restaurantID")==id)
                    Foods.add(Food.findFoodByID(resultSet.getInt("foodID")));
            findRestaurantByID(id).menu=Foods;
            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
