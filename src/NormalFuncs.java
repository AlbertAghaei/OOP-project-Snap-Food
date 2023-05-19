import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NormalFuncs
{
    public static void showAllAvailableRestaurants()//////////////////////////////////////////////
    {
       if(User.loggedInUser==null)
           System.out.println("LOGIN FIRST!");
       else if(User.loggedInUser.type.equals("Owner"))
           System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
       else
       {
           System.out.println("RESTAURANTS:");
           for (int i = 0; i < Restaurant.restaurants.size(); i++)
               System.out.println(Restaurant.restaurants.get(i).ID + " " + Restaurant.restaurants.get(i).name);
           if(Restaurant.restaurants.size()==0)
               System.out.println("NO RESTAURANTS!");
       }
    }
    public static void searchRestaurantByName(String name)///////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else
        {
            System.out.println("RESTAURANTS:");
            boolean found = false;
            for (int i = 0; i < Restaurant.restaurants.size(); i++)
                if(Restaurant.restaurants.get(i).name.equals(name))
                {
                    System.out.println(Restaurant.restaurants.get(i).ID + " " + Restaurant.restaurants.get(i).name);
                    found = true;
                }
            if(!found)
                System.out.println("NOTHING FOUND!");
        }
    }
    public static void selectRestaurant(int restaurantID)////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.findRestaurantByID(restaurantID)==null)
            System.out.println("RESTAURANT BY THIS ID DOES NOT EXIST!");
        else
        {
            Restaurant.restaurantInuse = Restaurant.findRestaurantByID(restaurantID);
            System.out.println("FOODS:");
            for(int i=0; i<Restaurant.findRestaurantByID(restaurantID).menu.size(); i++)
                System.out.println(Restaurant.findRestaurantByID(restaurantID).menu.get(i).ID+" "+Restaurant.findRestaurantByID(restaurantID).menu.get(i).name+" "+Restaurant.findRestaurantByID(restaurantID).menu.get(i).price);
            if(Restaurant.findRestaurantByID(restaurantID).menu.size()==0)
                System.out.println("NO FOODS!");
        }
    }
    public static void searchFoodByName(String name)
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else
        {
            System.out.println("FOODS:");
            boolean found = false;
            for (int i = 0; i < Restaurant.restaurantInuse.menu.size(); i++)
                if(Restaurant.restaurantInuse.menu.get(i).name.equals(name))
                {
                    System.out.println(Restaurant.restaurantInuse.menu.get(i).ID+" "+Restaurant.restaurantInuse.menu.get(i).name+" "+Restaurant.restaurantInuse.menu.get(i).price);
                    found = true;
                }
            if(!found)
                System.out.println("NOTHING FOUND!");
        }
    }
    public static void selectFood(int foodID)/////////////////////////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.findFoodByID(foodID)==null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
            System.out.println("FOOD WITH THIS ID DOES NOT EXIST!");
        else
        {
            Food.foodInuse=Food.findFoodByID(foodID);
            System.out.println("FOOD WITH THIS ID DOES NOT EXIST!");/////////print food data and restuarant data
        }
    }
}
