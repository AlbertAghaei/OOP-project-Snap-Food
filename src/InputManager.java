import java.util.Scanner;

public class InputManager
{
 Scanner scanner=new Scanner(System.in);
 String rawinput=scanner.nextLine();
 String[] input=rawinput.split(" ");
 String addAdmin="ADD ADMIN [a-zA-Z]+ \\w\\s*";
 String addUser="ADD USER [a-zA-Z]+ \\w\\s*";
 String loginAdmin="LOGIN ADMIN [a-zA-Z]+ \\w\\s*";
 String loginUser="LOGIN USER [a-zA-Z]+ \\w\\s*";
 String logout="LOGOUT\\s*";
 //resturant owner orders regex//
 String selectResturan="SELECT \\d+\\s*";
 String showLocation="SHOW LOCATION\\s*";
 String editLocation="EDIT LOCATION \\d+\\s*";
 String showFoodtype="SHOW FOODTYPE";
// String editFoodtype
 String selectMenu="SELECT MENU";
// String editFood
 String addFood="ADD FOOD [a-zA-Z]+ -?\\d*(\\.\\d+)?\\s*";
 String deleteFood="DELETE FOOD \\d+\\s*";
 String deactiveFood="DEACTIVE FOOD \\d+\\s*";
 String activeFood="ACTIVE FOOD \\d+\\s*";
 String discountFood="DISCOUNT FOOD \\d+ \\d+ \\d+\\s*";
 String selectFood="SELECT FOOD \\d+\\s*";
 String displayRating="DISPLAY RATING";
 String displayComments="DISPLAY COMMENTS";
// String addNewResponse
// String editResponse
 String displayOpenOrders="DISPLAY OPEN ORDERS";
// String editOrder="EDIT ORDER \\d\\s*";
 String showOrderHistory="SHOW ORDER HISTORY";
 //customer orders regex//
 String searchResturant="SEARCH RESTURANT [a-zA-Z]+ \\s*";
 String searchFood="SEARCH \\d+\\s*";
 String selectFoodCustomer="SELECT \\d+\\s*";
 // String addNewComment
// String editComment
 String submitRating="SUBMIT RATING \\d+\\s*";//rating must be a digit from 0to5
 String editRating="EDUT RATING \\d+\\s*";
 String addFoodToCart="ADD THIS FOOD TO CARTR";
 String accessOrserHistory="ACCESS ORDER HISTORY";
 String selectOrder="SELECT ORDER \\d+\\s*";
 String displayCartStatus="DISPLAY CART STATUS";
 String confirmOrder="CONFIRM ORDER";
 String showEstimatedDeliveryTime="SHOW ESTIMATED DELIVERY TIME";
 String chargeAccount="CHARGE ACCOUNT -?\\d*(\\.\\d+)?\\s*";
 String displayAccountCharge="DISPLAY ACCOUNT CHARGE";
 String suggestedFoods="SHOW SUGGESTED FOODS";
 String showDiscounts="SHOW MY AVAILABLE DISCOUNTS";
 //deliverer regex//
 String undeliveredOrders="SHOW UNDELIVERED ORDERS";
 //path regexes//
 String findBestPath="FIND BEST PATH \\d+\\s*";
 String showPath="SHOW PATH";
 String showDeliveryTime="SHOW DELIVERY TIME";

}
