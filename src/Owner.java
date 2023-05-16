import java.util.ArrayList;
public class Owner extends User
{
   ArrayList<Restaurant> ownedRestaurants = new ArrayList<>();
   Owner(String username, String password, String type)
   {
      super(username, password, type);
   }
   public static void sortAndPrintRestaurants()
   {}
   public static void selectRestaurant()
   {}
}
