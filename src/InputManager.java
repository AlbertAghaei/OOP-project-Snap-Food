import java.sql.SQLException;
public class InputManager
{
   public static void funcCaller(String input) throws SQLException
   {
       String[] splitted = input.split(" ");
       if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("ADD ADMIN"))//////////////
         Entrance.registerOwner(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("ADD USER"))///////////////
           Entrance.registerNormal(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("LOGIN ADMIN"))///////////////
           Entrance.loginOwner(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("LOGIN USER"))///////////////
           Entrance.loginNormal(splitted[2],splitted[3]);
       else if(splitted.length==1 && splitted[0].equals("LOGOUT"))///////////////
           Entrance.logout();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).equals("SHOW ALL RESTAURANTS"))////////
           NormalFuncs.showAllAvailableRestaurants();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]).equals("SEARCH RESTAURANT"))////////
           NormalFuncs.searchRestaurantByName(splitted[2]);
       else if(splitted.length==5 && (splitted[0]+" "+splitted[1]+" "+splitted[2]+" "+splitted[3]).equals("SELECT RESTAURANT BY USER"))////////
           NormalFuncs.selectRestaurant(Integer.parseInt(splitted[4]));
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]).equals("SEARCH FOOD"))///////////////////
           NormalFuncs.searchFoodByName(splitted[2]);
       else if(splitted.length==5 && (splitted[0]+" "+splitted[1]+" "+splitted[2]+" "+splitted[3]).equals("SELECT FOOD BY USER"))///////////////////
           NormalFuncs.selectFood(Integer.parseInt(splitted[4]));
       else if(splitted.length==2 && (splitted[0]+" "+splitted[1]).equals("DISPLAY COMMENTS"))///////////////
           NormalFuncs.displayComments();
       else if(splitted.length>=3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).equals("ADD NEW COMMENT"))////////////////
           NormalFuncs.addNewComment(splitted);
       else if(splitted.length>=3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).matches("EDIT COMMENT \\d"))////////////////
           NormalFuncs.editComment(Integer.parseInt(splitted[2]),splitted);
       else if(splitted.length==2 && (splitted[0]+" "+splitted[1]).equals("DISPLAY RATING"))///////////////
           NormalFuncs.displayRatings();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).matches("SUBMIT RATING \\d"))///////////////
           NormalFuncs.submitRating(Integer.parseInt(splitted[2]));
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]+" "+splitted[2]+" "+splitted[3]).matches("EDIT RATING \\d \\d"))///////////////
           NormalFuncs.editRating(Integer.parseInt(splitted[2]),Integer.parseInt(splitted[3]));
       else if(splitted.length==6 && input.trim().matches("ADD THIS FOOD TO CART \\d"))///////////////////////
           NormalFuncs.addFoodToCart(Integer.parseInt(splitted[5]));
       else if(input.trim().matches("ACCESS USER ORDER HISTORY"))//////////////////////////////
           NormalFuncs.showOrderHistory();
       else if(input.trim().matches("SELECT ORDER BY USER \\d"))///////////////////////
           NormalFuncs.selectOrder(Integer.parseInt(splitted[2]));
       else if(input.trim().equals("DISPLAY CART STATUS"))////////////////////////
           NormalFuncs.showCartStatus();
       else if(input.trim().equals("CONFIRM ORDER"))///////////////////////////
           NormalFuncs.confirmOrder();
       else if(input.trim().equals("DISPLAY ACCOUNT CHARGE"))///////////////////////////
           NormalFuncs.chargeStatus();
       else if(splitted.length==3 && input.trim().matches("CHARGE ACCOUNT \\d+(\\.\\d+)?"))///////////////////////////
           NormalFuncs.chargeAccount(Double.parseDouble(splitted[2]));
       else if(input.trim().equals("SHOW ORDERS TO DELIVER"))///////////////////////////
           NormalFuncs.showUnSent();
       else if(input.trim().matches("SELECT ORDER TO DELIVER \\d"))//////////////////////////////////............
           NormalFuncs.deliver(Integer.parseInt(splitted[4]));
       else if(input.trim().matches("SHOW ESTIMATED DELIVERY TIME \\d"))/////////////////////
           NormalFuncs.showOrderStatus(Integer.parseInt(splitted[4]));
       else if(input.trim().matches("FIND BEST PATH \\d+ TO RESTAURANT"))/////////////////////
           NormalFuncs.showFromNowToRestaurant(Integer.parseInt(splitted[3]));
       else if(input.trim().matches("FIND BEST PATH \\d+ TO CUSTOMER"))/////////////////////
           NormalFuncs.showFromNowToCustomer(Integer.parseInt(splitted[3]));
       else if(input.trim().equals("SHOW MAP"))/////////////////////
           Graph.showMap();
       else if(input.trim().matches("FIND BEST PATH TO RESTAURANT"))/////////////////////
           NormalFuncs.showDeliveryToRestaurant();
       else if(input.trim().matches("FIND BEST PATH TO CUSTOMER"))/////////////////////
           NormalFuncs.showFromRestaurantToCustomer();
       else //////////////////
           System.out.println("invalid command");
   }
}
