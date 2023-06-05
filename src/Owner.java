import java.util.ArrayList;
public class Owner extends User
{
   ArrayList<Restaurant> ownedRestaurants = new ArrayList<>();
   Owner(int ID,String username, String password, String type,Double charge) {
      super(ID, username, password, type);
      this.charge = charge;
   }
}
