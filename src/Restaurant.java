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
    User owner;
}
