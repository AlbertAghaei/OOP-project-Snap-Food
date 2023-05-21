import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Restaurant
{
    static ArrayList<Restaurant> restaurants = new ArrayList<>();
    int ID;
    static Restaurant restaurantInuse;
    String name;
    ArrayList<String> foodTypes = new ArrayList<>();
    ArrayList<Food> menu = new ArrayList<>();
    ArrayList<Order> restaurantHistory = new ArrayList<>();
    Node location;
    Owner owner;
    Restaurant(String name, ArrayList<String> types, Node location,Owner user,int ID )
    {
        this.name = name;
        this.foodTypes = types;
        this.location = location;
        this.owner = user;
        this.ID = ID;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
    String getName(){return this.name;}
    int getAge (){return this.ID;}
    public static Comparator<Restaurant> sortRestaurants() {
        return Comparator.comparing(Restaurant::getName)
                .thenComparing(Restaurant::getAge);

    }
    //show location = in ownerFunc
    ////////////////////////////get new list from owner
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
