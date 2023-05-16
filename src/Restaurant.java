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
    public static void showLocation()
    {}
    public static void editLocation()
    {}
    public static void showFoodType()
    {}
    public static void editFoodType()
    {
        //confirmation about foodType
    }
    public static void showMenu()
    {}
    public static boolean isTHereAnyActiveOrder()
    {return true;}
    public static void editFood()
    {}
    public static void addFood()
    {}
    public static void deleteFood()
    {
        //confirmation about deleting
        //isThereAnyOrderFromThisFood
    }
    public static void activeOrders()
    {}
    public static void editOrderStatus()
    {}
    public static void orderHistory()
    {}



}
