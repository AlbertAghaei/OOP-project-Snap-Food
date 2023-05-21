import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

public class OwnerFuncs {
    public static void PrintRestaurants() {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (User.loggedInUser.type != "Owner")
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() > 1) {
                Collections.sort(inuse.ownedRestaurants, Restaurant.sortRestaurants());
                for (int i = 0; i < inuse.ownedRestaurants.size(); i++)
                    System.out.println("Name : " + inuse.ownedRestaurants.get(i).name + " ID : " + inuse.ownedRestaurants.get(i).ID);
            } else if (inuse.ownedRestaurants.size() == 1) {
                Restaurant.restaurantInuse = inuse.ownedRestaurants.get(0);
                System.out.println("YOU LOGIN TO YOUR RESTAURANT : " + inuse.ownedRestaurants.get(0).name);
            } else if (inuse.ownedRestaurants.size() == 0)
                System.out.println("You have not registered your restaurants yet");
        }
    }

    public static Restaurant selectRestaurant(int restaurantID) //determine restaurantInuse
    {
        boolean exist = true;
        if (User.loggedInUser == null) {
            System.out.println("LOGIN FIRST!!");
            return null;
        } else if (User.loggedInUser.type != "Owner") {
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
            return null;
        } else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS!!!");
            else if (inuse.ownedRestaurants.size() == 1)
                System.out.println("YOU HAVE ALREADY LOGIN IN YOUR RESTAURANT AUTOMATICALLY!!");
            else if (inuse.ownedRestaurants.size() > 1) {
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++) {
                    if (inuse.ownedRestaurants.get(i).ID == restaurantID) {
                        exist = false;
                        return inuse.ownedRestaurants.get(i);
                    }
                }
                if (exist) {
                    System.out.println("THERE IS NO RESTAURANTS WITH THIS ID IN YOUR ACCOUNT!!");
                    return null;
                }
            }
        }
        return null;
    }

    public static void editLocation(int nodeID) throws SQLException {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (User.loggedInUser.type != "Owner")
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if (nodeID > 1000 || nodeID < 1)
                System.out.println("NEW NODE ID IS INVALID!!");
            else {
                int u = Restaurant.restaurantInuse.ID;
                boolean exist = true;
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++) {
                    if (inuse.ownedRestaurants.get(i).ID == u) {
                        exist = false;
                        inuse.ownedRestaurants.get(i).location.ID = nodeID;
                        Restaurant.restaurantInuse.location.ID = nodeID;
                        //update in sql
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
                                    statement.executeUpdate("update restaurants" + " set Location = " + nodeID + "where ID = " + u);
                                    System.out.println("Location edits successfully");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (exist)
                    System.out.println("YOU HAVE NOT SELECTED YOUR RESTAURANT!!");
            }
        }


    }

    public static void showFoodType() {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (User.loggedInUser.type != "Owner")
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if(Restaurant.restaurantInuse.foodTypes.size()!=0)
                System.out.println("Restaurant's FoodType : " +Restaurant.restaurantInuse.foodTypes);
            else if(Restaurant.restaurantInuse.foodTypes.size()==0)
                System.out.println("YOU HAVE NOT CHOOSE YOUR RESTAURANT's FOOD TYPE");
        }
    }

    public static void deleteFoodListDueToEditFoodType()
    {   if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (User.loggedInUser.type != "Owner")
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Restaurant.restaurantInuse.restaurantHistory.size()!=0)
            System.out.println("Sorry you are not able to change the foodType , There are still some active orders");
        else{
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
                    statement.executeUpdate("delete from restaurant_food"+"where restaurntID = "+Restaurant.restaurantInuse.ID);
                    statement.executeUpdate("delete from restaurant_type"+"where restaurntID = "+Restaurant.restaurantInuse.ID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Restaurant.restaurantInuse.menu.clear();
        Restaurant.restaurantInuse.foodTypes.clear();
        //clear menu arraylist in owner
            boolean exist =true;
            for (int i=0;i<inuse.ownedRestaurants.size()&&exist;i++)
            {if(inuse.ownedRestaurants.get(i).ID==Restaurant.restaurantInuse.ID)
            {exist =false;
                inuse.ownedRestaurants.get(i).foodTypes.clear();
                inuse.ownedRestaurants.get(i).menu.clear();
            }
            }

            }
    }
    }

}