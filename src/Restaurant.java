import java.util.ArrayList;
public class Restaurant
{
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    int ID;
    Restaurant restaurantInuse;
    String name;
    ArrayList<String> foodTypes = new ArrayList<>();
    ArrayList<Food> menu = new ArrayList<>();
    ArrayList<Order> restaurantHistory = new ArrayList<>();
    Node location;
    Owner owner;
    Restaurant(String name, ArrayList<String> types, Node location,Owner user )
    {
        this.name = name;
        this.foodTypes = types;
        this.location = location;
        this.owner = user;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
    public static Restaurant findRestaurantByID(int ID)
    {
        for(int i=0; i<restaurants.size(); i++)
            if(restaurants.get(i).ID==ID)
                return restaurants.get(i);
        return null;
    }
}
