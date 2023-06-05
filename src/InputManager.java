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
       else if(input.trim().equals("DISPLAY SUGGESTIONS"))///////////////////
           NormalFuncs.suggest();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).equals("SHOW ALL RESTAURANTS"))////////
           NormalFuncs.showAllAvailableRestaurants();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]).equals("SEARCH RESTAURANT"))////////
           NormalFuncs.searchRestaurantByName(splitted[2]);
       else if(input.trim().matches("SELECT RESTAURANT BY USER \\d+"))////////
           NormalFuncs.selectRestaurant(Integer.parseInt(splitted[4]));
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]).equals("SEARCH FOOD"))///////////////////
           NormalFuncs.searchFoodByName(splitted[2]);
       else if(input.trim().matches("SELECT FOOD BY USER \\d+"))///////////////////
           NormalFuncs.selectFood(Integer.parseInt(splitted[4]));
       else if(splitted.length==2 && (splitted[0]+" "+splitted[1]).equals("DISPLAY COMMENTS"))///////////////
           NormalFuncs.displayComments();
       else if(splitted.length>=4 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).equals("ADD NEW COMMENT"))////////////////
           NormalFuncs.addNewComment(splitted);
       else if(splitted.length>=3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).matches("EDIT COMMENT \\d+"))////////////////
           NormalFuncs.editComment(Integer.parseInt(splitted[2]),splitted);
       else if(splitted.length==2 && (splitted[0]+" "+splitted[1]).equals("DISPLAY RATING"))///////////////
           NormalFuncs.displayRatings();
       else if(splitted.length==3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).matches("SUBMIT RATING \\d"))///////////////
           NormalFuncs.submitRating(Integer.parseInt(splitted[2]));
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]+" "+splitted[2]+" "+splitted[3]).matches("EDIT RATING \\d+ \\d"))///////////////
           NormalFuncs.editRating(Integer.parseInt(splitted[2]),Integer.parseInt(splitted[3]));
       else if(splitted.length==6 && input.trim().matches("ADD THIS FOOD TO CART \\d+"))///////////////////////
           NormalFuncs.addFoodToCart(Integer.parseInt(splitted[5]));
       else if(input.trim().matches("ACCESS USER ORDER HISTORY"))//////////////////////////////
           NormalFuncs.showOrderHistory();
       else if(input.trim().matches("SELECT ORDER BY USER \\d+"))///////////////////////
           NormalFuncs.selectOrder(Integer.parseInt(splitted[4]));
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
       else if(input.trim().matches("SELECT ORDER TO DELIVER \\d+"))//////////////////////////////////
           NormalFuncs.deliver(Integer.parseInt(splitted[4]));
       else if(input.trim().matches("SHOW ESTIMATED DELIVERY TIME \\d+"))/////////////////////
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
       else if(input.trim().matches("DISPLAY ALL RESTAURANTS"))/////////////////////
           OwnerFuncs.PrintRestaurants();
       else if(input.trim().matches("SELECT RESTAURANT BY OWNER \\d+"))//////////////////////
           OwnerFuncs.selectRestaurant(Integer.parseInt(splitted[4]));
       else if(input.trim().matches("SHOW LOCATION"))////////////////////////
           OwnerFuncs.showLocation();
       else if(input.trim().matches("EDIT LOCATION \\d+"))/////////////////////////////////////
           OwnerFuncs.editLocation(Integer.parseInt(splitted[2]));
       else if(input.trim().matches("SHOW FOODTYPE"))////////////////////////////////
           OwnerFuncs.showFoodType();
       else if(splitted.length>2 && (splitted[0]+" "+splitted[1]).equals("EDIT FOODTYPE"))///////////////////
           OwnerFuncs.insertNewFoodType(splitted);
       else if(input.trim().equals("SELECT MENU"))///////////////////////////
           OwnerFuncs.showMenu();
       else if(input.trim().matches("EDIT FOOD \\d+ NAME [a-zA-Z]+"))///////////////////////////
           OwnerFuncs.editFoodName(Integer.parseInt(splitted[2]),splitted[4]);
       else if(input.trim().matches("EDIT FOOD \\d+ PRICE \\d+(\\.\\d+)?"))///////////////////////////////
           OwnerFuncs.editFoodPrice(Integer.parseInt(splitted[2]),Double.parseDouble(splitted[4]));
       else if(input.trim().matches("DELETE FOOD \\d+"))////////////////////////////////////////
           OwnerFuncs.deleteFood(Integer.parseInt(splitted[2]));
       else if(input.trim().matches("ADD FOOD [a-zA-Z]+ \\d+(\\.\\d+)?"))////////////////////////////
           OwnerFuncs.addFood(splitted[2], Double.parseDouble(splitted[3]));
       else if (input.trim().matches("ACTIVE FOOD \\d+"))///////////////////////////////
           OwnerFuncs.activeFood(Integer.parseInt(splitted[2]));
       else if(input.trim().matches("DEACTIVE FOOD \\d+"))///////////////////////
           OwnerFuncs.deActiveFood(Integer.parseInt(splitted[2]));
       else if(input.trim().matches("SET DISCOUNT \\d+ \\d+ \\d+"))////////////////////////
           OwnerFuncs.SetDiscountFood(Integer.parseInt(splitted[2]),Integer.parseInt(splitted[3]),Integer.parseInt(splitted[4]));
       else if(input.trim().matches("SELECT FOOD BY OWNER \\d+"))////////////////////////
           OwnerFuncs.selectFoodToResponse(Integer.parseInt(splitted[4]));
       else if(input.trim().equals("DISPLAY RATINGS FOR OWNER"))/////////////////////////
           OwnerFuncs.displayRating();
       else if(input.trim().equals("DISPLAY COMMENTS FOR OWNER"))/////////////////////////////
           OwnerFuncs.displayComments();
       else if(splitted.length>4 && (splitted[0]+" "+splitted[1]+" "+splitted[2]+" "+splitted[3]).matches("ADD NEW RESPONSE \\d+"))////////
           OwnerFuncs.addResponse(Integer.parseInt(splitted[3]),splitted);
       else if(splitted.length>3 && (splitted[0]+" "+splitted[1]+" "+splitted[2]).matches("EDIT RESPONSE \\d+"))//////////
           OwnerFuncs.editResponse(Integer.parseInt(splitted[2]),splitted);
       else if(input.trim().equals("DISPLAY OPEN ORDERS"))//////////////////////////
           OwnerFuncs.activeOrders();
       else if(input.trim().equals("SHOW ORDER HISTORY"))//////////////////
           OwnerFuncs.orderHistory();
       else if(input.trim().matches("ADD RESTAURANT [a-zA-Z]+ \\d+"))///////////////
           OwnerFuncs.addRestaurant(splitted[2],Integer.parseInt(splitted[3]));
       else if(input.trim().matches("EDIT ORDER \\d+ STATUS [a-zA-Z]+"))/////////////////////////
           OwnerFuncs.changeOrderStatusManually(Integer.parseInt(splitted[2]), splitted[4]);
       else //////////////////
           System.out.println("invalid command");
   }
}
