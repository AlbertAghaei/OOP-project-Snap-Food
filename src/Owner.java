import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Owner extends User
{
   ArrayList<Restaurant> ownedRestaurants = new ArrayList<>();
   Owner(String username, String password, String type)
   {
      super(username, password, type);
   }



   public  void PrintRestaurants() throws SQLException
   {   Collections.sort(ownedRestaurants,Restaurant.sortRestaurants());
      if(ownedRestaurants.size()>1)
      for(int i=0;i<ownedRestaurants.size();i++)
         System.out.println("Name : "+ownedRestaurants.get(i).name+" ID : "+ownedRestaurants.get(i).ID);
      if(ownedRestaurants.size()==0)
         System.out.println("You have not registered your restaurants yet");
   }
   public  Restaurant selectRestaurant(int restaurantID) //determine restaurantInuse
   {for(int i=0;i<ownedRestaurants.size();i++)
   {if(ownedRestaurants.get(i).ID==restaurantID)
      return ownedRestaurants.get(i);
   }
    return  null;
   }
   public void editLocation(int nodeID) throws SQLException
   {int u=Restaurant.restaurantInuse.ID;
      for(int i=0;i<ownedRestaurants.size();i++)
      {if (ownedRestaurants.get(i).ID==u)
         ownedRestaurants.get(i).location.ID=nodeID;
      }
      Restaurant.restaurantInuse.location.ID=nodeID;
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
               statement.executeUpdate("update restaurants"+" set Location = "+nodeID+"where ID = "+u);
               System.out.println("Location edits successfully");
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
