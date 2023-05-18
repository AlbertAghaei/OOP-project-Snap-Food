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
                .thenComparing(Restaurant::getAge);}
    public static void getAllRestaurantsFromDatabase()
    {
    }
    //show location = in ownerFunc
    //edit location  in owner
    public static void showFoodType()
    {
        System.out.println("Restaurant's FoodType : "+restaurantInuse.foodTypes);
    }
    public static void deleteFoodListInEditFoodType(String [] type)
    {
        //confirmation about foodType in OwnerFunctions
        //check if there is no order
//        if(restaurantInuse.restaurantHistory.size()!=0)
//            System.out.println("Sorry you are not able to change the foodType , There are still some active orders");
//        else { Scanner scanner = new Scanner(System.in);
//        String s = new String("");
//        boolean response = false;
//        while (s.indexOf("yes")<0) {
//            if (s.length() == 0) {
//                System.out.println("Are you sure you want to change your restaurant type ?!+"\n"+"Yes Or No !!!");
//            }
//                s = scanner.nextLine();
//            if (s.indexOf("yes")>=0) {
//                System.out.println("1");
//                break;
//            }
//        }}
        ////// after confirmation
        //delete foodList in sql
        {
            String url;
            String username;
            String password;
            Connection connection;
            url = "jdbc:mysql://localhost:3306/oop-project-snapfood";
            username = "root";
            password = "W@2915djkq#";
            try {
                connection = DriverManager.getConnection(url, username, password);
                if (connection != null) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("delete from restaurant_food"+"where restaurntID = "+restaurantInuse.ID);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        restaurantInuse.menu.clear();
        //clear menu arraylist in owner in ownerFunc
    }
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
