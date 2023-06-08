import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Owner extends User
{
   ArrayList<Restaurant> ownedRestaurants = new ArrayList<>();
   Owner(int ID,String username, String password, String type,Double charge) {
      super(ID, username, password, type);
      this.charge = charge;
   }
}
