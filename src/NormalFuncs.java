import com.mysql.cj.jdbc.exceptions.SQLError;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NormalFuncs
{
    public static void suggest()////////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else
        {
            ArrayList<Restaurant> used = new ArrayList<>();
            for(int i=0 ; i<((Normal)User.loggedInUser).userHistory.size(); i++)
            {
                findRestaurantByOrderID(((Normal)User.loggedInUser).userHistory.get(i).ID).count++;
                if(!used.contains(findRestaurantByOrderID(((Normal)User.loggedInUser).userHistory.get(i).ID)))
                    used.add(findRestaurantByOrderID(((Normal)User.loggedInUser).userHistory.get(i).ID));
            }
            int count = Math.min(used.size(),5);

            System.out.println("SUGGESTIONS: (MAX = 5) BASED ON RATING AND NUMBER OF TIMES CHOSEN BY YOU");
            Collections.sort(used, new Comparator<Restaurant>() {
                @Override
                public int compare(Restaurant r1, Restaurant r2) {
                    int counterComparison = Integer.compare(r2.count, r1.count);
                    if (counterComparison == 0)
                        return Double.compare(calculateTotalRestaurantRating(r2), calculateTotalRestaurantRating(r1));
                    return counterComparison;
                }
            });

            for (int i=0; i<count; i++)
                System.out.println(used.get(i).ID +" "+used.get(i).name);

        }
        for(int i=0; i<Restaurant.restaurants.size(); i++)
            Restaurant.restaurants.get(i).count = 0;
    }
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
            if(Double.isNaN(calculateTotalRestaurantRating(Restaurant.restaurantInuse)))
               System.out.println("TOTAL RATING: NO RATING YET!");
            else
                System.out.println("TOTAL RATING: "+calculateTotalRestaurantRating(Restaurant.restaurantInuse));
            System.out.println("FOOD TYPES:");
            for(int i=0; i<Restaurant.restaurantInuse.foodTypes.size(); i++)
                System.out.println(Restaurant.restaurantInuse.foodTypes.get(i));
            System.out.println("FOODS:");
            for(int i=0; i<Restaurant.findRestaurantByID(restaurantID).menu.size(); i++)
                System.out.println(Restaurant.findRestaurantByID(restaurantID).menu.get(i).ID+" "+Restaurant.findRestaurantByID(restaurantID).menu.get(i).name+" "+Restaurant.findRestaurantByID(restaurantID).menu.get(i).price+"$");
            if(Restaurant.findRestaurantByID(restaurantID).menu.size()==0)
                System.out.println("NO FOODS!");
        }
    }
    public static void searchFoodByName(String name)////////////////////////////////////////////
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
                    System.out.println(Restaurant.restaurantInuse.menu.get(i).ID+" "+Restaurant.restaurantInuse.menu.get(i).name+" "+Restaurant.restaurantInuse.menu.get(i).price+"$");
                    found = true;
                }
            if(!found)
                System.out.println("NOTHING FOUND!");
        }
    }
    public static void selectFood(int foodID)throws SQLException////////////////////////////////////////////////////////////
    {
        OwnerFuncs.updateDiscountActivity();
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
            if(Food.foodInuse.active)
                System.out.println("AVAILABILITY: available");
            else
                System.out.println("AVAILABILITY: unavailable");
            if(Food.foodInuse.discount!=null && Food.foodInuse.discount.active)
                System.out.println("DISCOUNT PERCENT: "+Food.foodInuse.discount.percent+"%");
            else
                System.out.println("DISCOUNT PERCENT: 0%");
        }
    }
    public static void displayComments()//////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else
        {
            System.out.println("COMMENTS:");
            for(int i=0; i<Food.foodInuse.comments.size(); i++){
                System.out.println(Food.foodInuse.comments.get(i).ID+" USER: "+Food.foodInuse.comments.get(i).user.username + " COMMENT: "+ Food.foodInuse.comments.get(i).text);
            if(Food.foodInuse.comments.get(i).response!=null)
                System.out.println("    RESPONSE: "+Food.foodInuse.comments.get(i).response.massage+"  OWNER: "+Food.foodInuse.comments.get(i).response.owner.username);
            }
            if(Food.foodInuse.comments.size()==0)
                System.out.println("NO COMMENTS YET!");
        }
    }
    public static boolean checkIfUserHasOrderedAFood(Normal inuse, Food inuse1)///////////////////////////////////
    {
        for(int i=0; i<inuse.userHistory.size(); i++)
            for(int j=0; j<inuse.userHistory.get(i).orderedFoods.size(); j++)
                if(inuse.userHistory.get(i).orderedFoods.get(j)==inuse1)
                    return true;
        return false;
    }
    public static void addNewComment(String[] splitted)throws SQLException////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else if(!checkIfUserHasOrderedAFood((Normal)User.loggedInUser,Food.foodInuse))
            System.out.println("YOU HAVE NOT ORDERED THIS FOOD BEFORE!");
        else
        {
            String comment="";
            for (int i=3; i< splitted.length; i++)
               comment+=splitted[i];
            Comment comment1 = new Comment(Comment.allComments.size()+1,comment,(Normal)User.loggedInUser,null);
            Comment.allComments.add(comment1);
            Food.foodInuse.comments.add(comment1);
            String query = "INSERT INTO comments(commentedText, responseID, UserID) VALUES ( ?, ?, ?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setString(1, comment);
            statement.setInt(2, 0);///default = unanswered
            statement.setInt(3, User.loggedInUser.ID);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("COMMENTED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
            String query1 = "INSERT INTO food_comment(commentID, foodID) VALUES ( ?, ?)";
            PreparedStatement statement1 = SQL.connection.prepareStatement(query1);
            statement1.setInt(1, Comment.allComments.size());
            statement1.setInt(2, Food.foodInuse.ID);
            int rowsInserted1 = statement1.executeUpdate();
            if (rowsInserted1 <= 0)
                System.out.println("Failed to make connection!");
        }
    }
    public static void editComment(int commentID, String[] splitted)throws SQLException/////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else if(!checkIfUserHasOrderedAFood((Normal)User.loggedInUser,Food.foodInuse))
            System.out.println("YOU HAVE NOT ORDERED THIS FOOD BEFORE!");
        else if(Comment.findCommentByID(commentID)==null)
            System.out.println("COMMENT WITH THIS ID DOES NOT EXIST!");
        else if(Comment.findCommentByID(commentID).user!=User.loggedInUser)
            System.out.println("THIS COMMENT IS NOT YOURS!");
        else
        {
            String newComment="";
            for (int i=3; i< splitted.length; i++)
                newComment+=splitted[i];
            Comment.findCommentByID(commentID).text = newComment;
            String query = "UPDATE comments SET commentedText = ? WHERE ID = ?";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setString(1, newComment);
            statement.setInt(2, commentID);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0)
                System.out.println("COMMENT EDITED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }
    public static boolean checkIfUserHasRatedBefore(Food food)/////////////////////////////////////
    {
        for(int i=0; i<food.ratings.size(); i++)
            if(food.ratings.get(i).user==User.loggedInUser)
                return true;
        return false;
    }
    public static void displayRatings()/////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else
        {
            System.out.println("RATINGS:");
            for(int i=0; i<Food.foodInuse.ratings.size(); i++)
                System.out.println(Food.foodInuse.ratings.get(i).ID+" USER: "+Food.foodInuse.ratings.get(i).user.username+" RATE: "+Food.foodInuse.ratings.get(i).stars+"/5");
            if(Food.foodInuse.ratings.size()==0)
              System.out.println("NO RATING YET!");
        }
    }
    public static double calculateTotalRestaurantRating(Restaurant restaurant)//////////////////////////////
    {
        double sum=0;
        double counter=0;
       for (int i=0; i<restaurant.menu.size(); i++)
           for(int j=0; j<restaurant.menu.get(i).ratings.size(); j++) {
               sum += restaurant.menu.get(i).ratings.get(j).stars;
               counter++;
           }
       return sum/counter;
    }
    public static void submitRating(int stars)throws SQLException///////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else if(!checkIfUserHasOrderedAFood((Normal)User.loggedInUser,Food.foodInuse))
            System.out.println("YOU HAVE NOT ORDERED THIS FOOD BEFORE!");
        else if(checkIfUserHasRatedBefore(Food.foodInuse))
            System.out.println("YOU HAVE RATED BEFORE!");
        else if(stars<0 || stars>5)
            System.out.println("INVALID AMOUNT!");
        else
        {
           Rate rate = new Rate(Rate.allRates.size()+1,stars,(Normal)User.loggedInUser);
           Rate.allRates.add(rate);
           Food.foodInuse.ratings.add(rate);
            String query = "INSERT INTO rating(stars, UserID) VALUES ( ?, ?)";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, stars);
            statement.setInt(2, User.loggedInUser.ID);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
                System.out.println("RATED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
            String query1 = "INSERT INTO food_rating(foodID,rateID) VALUES ( ?, ?)";
            PreparedStatement statement1 = SQL.connection.prepareStatement(query1);
            statement1.setInt(1, Food.foodInuse.ID);
            statement1.setInt(2, Rate.allRates.size());
            int rowsInserted1 = statement1.executeUpdate();
            if (rowsInserted1 <= 0)
                System.out.println("Failed to make connection!");
        }
    }
    public static void editRating(int rateID, int newRate)throws SQLException/////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.foodInuse==null)
            System.out.println("YOU HAVE SELECTED NO FOOD!");
        else if(!checkIfUserHasOrderedAFood((Normal)User.loggedInUser,Food.foodInuse))
            System.out.println("YOU HAVE NOT ORDERED THIS FOOD BEFORE!");
        else if(Rate.findRateByID(rateID)==null)
            System.out.println("RATE WITH THIS ID DOES NOT EXIST!");
        else if(Rate.findRateByID(rateID).user!=User.loggedInUser)
            System.out.println("THIS RATE IS NOT YOURS!");
        else
        {
            Rate.findRateByID(rateID).stars = newRate;
            String query = "UPDATE rating SET stars = ? WHERE ID = ?";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, newRate);
            statement.setInt(2, rateID);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0)
                System.out.println("RATE EDITED SUCCESSFULLY!");
            else
                System.out.println("Failed to make connection!");
        }
    }
    public static void addFoodToCart(int foodID)////////////////////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Restaurant.restaurantInuse==null)
            System.out.println("YOU HAVE SELECTED NO RESTAURANTS!");
        else if(Food.findFoodByID(foodID)==null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
            System.out.println("THIS RESTAURANT MENU DOES NOT HAVE A FOOD WITH THE CHOSEN ID!");
        else if(!Food.findFoodByID(foodID).active)
            System.out.println("THE CHOSEN FOOD IS NOT AVAILABLE RIGHT NOW!");
        else
        {
            Normal customer = (Normal)User.loggedInUser;
            customer.cart.add(Food.findFoodByID(foodID));
            System.out.println("FOOD ADDED TO CART SUCCESSFULLY!");
        }
    }
    public static void showOrderHistory()//////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else
        {
            Normal customer = (Normal)User.loggedInUser;
            System.out.println("ORDER ID:");
            for(int i=0; i<customer.userHistory.size(); i++)
                System.out.println(customer.userHistory.get(i).ID);
            if(customer.userHistory.size()==0)
                System.out.println("NO PREVIOUS ORDERS!");
        }
    }
    public static Restaurant findRestaurantByOrderID(int orderID)/////////////////////////////////////////////
    {
        for(int i=0; i<Restaurant.restaurants.size(); i++)
            for(int j=0; j<Restaurant.restaurants.get(i).restaurantHistory.size(); j++)
                if(Restaurant.restaurants.get(i).restaurantHistory.get(j).ID==orderID)
                    return Restaurant.restaurants.get(i);
        return null;
    }
    public static double calculateOrderCost(Order order)throws SQLException//////////////////////////////////
    {
        OwnerFuncs.updateDiscountActivity();
        double sum=0;
        for(int i=0; i<order.orderedFoods.size(); i++)
        {
            if(order.orderedFoods.get(i).discount==null || order.orderedFoods.get(i).discount.percent==0 || !order.orderedFoods.get(i).discount.active  )
                sum+=order.orderedFoods.get(i).price;
            else
                sum+=order.orderedFoods.get(i).price*(double)order.orderedFoods.get(i).discount.percent/100;
        }
        return sum;
    }
    public static double calculateCartCost(Normal user)throws SQLException//////////////////////////////////
    {
        OwnerFuncs.updateDiscountActivity();
        double sum=0;
        for(int i=0; i<user.cart.size(); i++)
        {
            if(user.cart.get(i).discount==null || user.cart.get(i).discount.percent==0 || !user.cart.get(i).discount.active  )
                sum+=user.cart.get(i).price;
            else
                sum+=user.cart.get(i).price*(double)user.cart.get(i).discount.percent/100;
        }
        return sum;
    }
    public static void selectOrder(int orderID)throws SQLException////////////////////////////////////////////////
    {
        Normal customer = null;
        if(User.loggedInUser!=null)
             customer = (Normal)User.loggedInUser;
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(Order.findOrderByID(orderID)==null || !customer.userHistory.contains(Order.findOrderByID(orderID)))
            System.out.println("ORDER WITH THIS ID DOES NOT EXIST IN YOUR ORDERS HISTORY!");
        else
        {
            Order.orderInUse = Order.findOrderByID(orderID);
            System.out.println("RESTAURANT: "+findRestaurantByOrderID(orderID).name);
            System.out.println("ORDERED FOODS:");
            for(int i=0; i<Order.findOrderByID(orderID).orderedFoods.size(); i++)
                System.out.println(Order.findOrderByID(orderID).orderedFoods.get(i).name);
            System.out.println("TOTAL PRICE: "+calculateOrderCost(Order.findOrderByID(orderID))+"$");
        }
    }
    public static void showCartStatus()////////////////////////////////////
    {
        Normal customer = null;
        if(User.loggedInUser!=null)
            customer = (Normal)User.loggedInUser;
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(customer.cart.size()==0)
            System.out.println("CART IS EMPTY!");
        else
        {
            System.out.println("CART CONTAINS:");
            for(int i=0; i<customer.cart.size(); i++)
                System.out.println(customer.cart.get(i).name);
        }
    }
    public static void confirmOrder()throws SQLException//////////////////////////////////
    {
        Normal customer = null;
        if(User.loggedInUser!=null)
            customer = (Normal)User.loggedInUser;
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(customer.cart.size()==0)
            System.out.println("CART IS EMPTY!");
        else if(calculateCartCost(customer)> customer.charge)
            System.out.println("CHARGE YOUR ACCOUNT FIRST!");
        else
        {
            customer.charge-=calculateCartCost(customer);
            Restaurant.restaurantInuse.owner.charge+=calculateCartCost(customer);
            Order NEW = new Order(Order.allOrders.size()+1,customer.cart,calculateCartCost(customer),"sending");
            ArrayList<Food> temp = new ArrayList<>(customer.cart);
            NEW.orderedFoods = temp;
            customer.cart.clear();
            Restaurant.restaurantInuse.restaurantHistory.add(NEW);
            ((Normal) User.loggedInUser).userHistory.add(NEW);
            Order.allOrders.add(NEW);
            System.out.println("ORDER SUBMITTED SUCCESSFULLY!");
            String query = "UPDATE users SET Charge = ? WHERE ID = ?";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, customer.charge);
            statement.setInt(2, User.loggedInUser.ID);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "UPDATE users SET Charge = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, Restaurant.restaurantInuse.owner.charge);
            statement.setInt(2, Restaurant.restaurantInuse.owner.ID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "INSERT INTO restaurant_order(restaurantID,orderID) VALUES ( ?, ?)";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, Restaurant.restaurantInuse.ID);
            statement.setInt(2, Order.allOrders.size());
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "INSERT INTO user_order(userID,orderID) VALUES ( ?, ?)";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, User.loggedInUser.ID);
            statement.setInt(2, Order.allOrders.size());
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "INSERT INTO orders(orderStatus,totallPrice) VALUES ( ?, ?)";
            statement = SQL.connection.prepareStatement(query);
            statement.setString(1, NEW.status);
            statement.setDouble(2, NEW.totalPrice);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            for(int i=0; i<NEW.orderedFoods.size();i++)
            {
                query = "INSERT INTO order_food(foodID,orderID) VALUES ( ?, ?)";
                statement = SQL.connection.prepareStatement(query);
                statement.setInt(1, NEW.orderedFoods.get(i).ID);
                statement.setInt(2, NEW.ID);
                rowsAffected = statement.executeUpdate();
                if(rowsAffected<=0)
                    System.out.println("Failed to make connection!");
            }
            System.out.println("TYPE YOUR LOCATION(NODE ID):");
            int nodeID = Integer.parseInt(Main.input.nextLine());
            while(nodeID<1 || nodeID>1000 )
            {
                System.out.println("INVALID LOCATION!  TYPE YOUR LOCATION AGAIN (NODE ID):");
                nodeID = Integer.parseInt(Main.input.nextLine());
            }
            customer.location = Node.getNodeByID(nodeID);
            query = "UPDATE users SET location = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, nodeID);
            statement.setInt(2, User.loggedInUser.ID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
        }
    }
    public static void chargeAccount(double amount)throws SQLException//////////////////////////////////
    {
        Normal customer = null;
        if(User.loggedInUser!=null)
            customer = (Normal)User.loggedInUser;
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else if(amount<=0)
            System.out.println("INVALID AMOUNT!");
        else
        {
            customer.charge+=amount;
            System.out.println("ACCOUNT CHARGED SUCCESSFULLY!");
            String query = "UPDATE users SET Charge = ? WHERE ID = ?";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, customer.charge);
            statement.setInt(2, User.loggedInUser.ID);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
        }
    }
    public static void chargeStatus()///////////////////////////////////
    {
        Normal customer = null;
        if(User.loggedInUser!=null)
            customer = (Normal)User.loggedInUser;
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE AN OWNER NOT A CUSTOMER!");
        else
            System.out.println("CHARGE: "+customer.charge);
    }
    public static Normal findUserByOrder(int orderID)///////////////////////
    {
        for(int i=0; i<User.allUsers.size(); i++)
            if(User.allUsers.get(i).type.equals("Normal"))
              if(((Normal)User.allUsers.get(i)).userHistory.contains(Order.findOrderByID(orderID)))
                  return ((Normal)User.allUsers.get(i));
        return null;
    }
    public static double calculateDeliveryPrice(int firstNode, int secondNode)////////////////////////
    {
        int time = Graph.calculateTime(firstNode,secondNode);
        return (double)time/5;
    }
    public static void showUnSent()throws SQLException//////////////////////////////////////////////
    {
        OwnerFuncs.updateOrderActivity();
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("RESTAURANT OWNER CAN NOT DELIVER!");
        else
        {
            for(int i=0; i<Order.allOrders.size(); i++)
                if(Order.allOrders.get(i).status.equals("sending"))
                    System.out.println(Order.allOrders.get(i).ID+" RESTAURANT LOCATION: "+findRestaurantByOrderID(Order.allOrders.get(i).ID).location.ID+" CUSTOMER LOCATION: "+findUserByOrder(Order.allOrders.get(i).ID).location.ID+ " DELIVERY PRICE: "+calculateDeliveryPrice(findRestaurantByOrderID(Order.allOrders.get(i).ID).location.ID,findUserByOrder(Order.allOrders.get(i).ID).location.ID)+"$");
        }
    }
    public static void deliver(int orderID)throws SQLException/////////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("RESTAURANT OWNER CAN NOT DELIVER!");
        else if(Order.findOrderByID(orderID)==null || !Order.findOrderByID(orderID).status.equals("sending"))
            System.out.println("ORDER WITH THIS ID EITHER DOES NOT EXIST OR IS TAKEN (IT ALSO CAN HAVE BEEN DELIVERED :} )!");
        else if(((Normal)User.loggedInUser).deliver!=null && ((Normal)User.loggedInUser).deliver.status.equals("taken"))
            System.out.println("YOU ALREADY HAVE AN UNDELIVERED ORDER!");
        else if(findUserByOrder(orderID)==(User.loggedInUser))
            System.out.println("YOU CAN NOT DELIVER YOUR OWN ORDER :} !");
        else
        {
            Order.findOrderByID(orderID).status="taken";
            ((Normal)User.loggedInUser).charge+=calculateDeliveryPrice(findRestaurantByOrderID(orderID).location.ID,findUserByOrder(orderID).location.ID);
            (findUserByOrder(orderID)).charge-=calculateDeliveryPrice(findRestaurantByOrderID(orderID).location.ID,findUserByOrder(orderID).location.ID);
            Order.findOrderByID(orderID).whenTaken = Instant.now();
            ((Normal)User.loggedInUser).deliver = Order.findOrderByID(orderID);
            String query = "UPDATE orders SET orderStatus = ? WHERE ID = ?";
            PreparedStatement statement = SQL.connection.prepareStatement(query);
            statement.setString(1, "taken");
            statement.setInt(2, orderID);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "UPDATE orders SET whenTaken = CURRENT_TIMESTAMP WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, orderID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "UPDATE users SET Charge = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, ((Normal)User.loggedInUser).charge);
            statement.setInt(2,User.loggedInUser.ID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "UPDATE users SET Charge = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setDouble(1, (findUserByOrder(orderID)).charge);
            statement.setInt(2,(findUserByOrder(orderID)).ID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            query = "INSERT INTO delivery_user(orderID,userID) VALUES ( ?, ?)";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, orderID);
            statement.setInt(2, User.loggedInUser.ID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            System.out.println("GIVE US YOUR LOCATION (NODE ID) :");
            int nodeID = Integer.parseInt(Main.input.nextLine().trim());
            while(nodeID<1 || nodeID>1000 )
            {
                System.out.println("INVALID LOCATION!  TYPE YOUR LOCATION AGAIN (NODE ID):");
                nodeID = Integer.parseInt(Main.input.nextLine());
            }
            query = "UPDATE users SET location = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, nodeID);
            statement.setInt(2, User.loggedInUser.ID);
            rowsAffected = statement.executeUpdate();
            ((Normal) User.loggedInUser).location = Node.getNodeByID(nodeID);
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            int duration = Graph.calculateTime(nodeID,findRestaurantByOrderID(orderID).ID)+Graph.calculateTime(findRestaurantByOrderID(orderID).ID,findUserByOrder(orderID).ID);
            query = "UPDATE orders SET duration = ? WHERE ID = ?";
            statement = SQL.connection.prepareStatement(query);
            statement.setInt(1, duration);
            statement.setInt(2, orderID);
            rowsAffected = statement.executeUpdate();
            if(rowsAffected<=0)
                System.out.println("Failed to make connection!");
            System.out.println("LETS DELIVER!");
        }
    }
    public static void showOrderStatus(int orderID)//////////////////////////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A RESTAURANT OWNER NOT A CUSTOMER!");
        else if(((Normal)User.loggedInUser).userHistory.size()==0)
            System.out.println("NO ORDERS YET!");
        else if(Order.findOrderByID(orderID)==null)
            System.out.println("THIS ORDER DOES NOT EXIST!");
        else if(!((Normal)User.loggedInUser).userHistory.contains(Order.findOrderByID(orderID)))
            System.out.println("THIS ORDER IS NOT CONFIRMED BY YOU!");
        else if(Order.findOrderByID(orderID).status.equals("sending"))
            System.out.println("ORDER NOT YET ACCEPTED BY DELIVERY MAN!");
        else if(Order.findOrderByID(orderID).status.equals("sent"))
            System.out.println("ORDER IS DELIVERED!");
        else
        {
            String query = "SELECT TIMESTAMPDIFF(MINUTE, whenTaken, NOW()) AS minutes_difference " +
                    "FROM orders " +
                    "WHERE ID = ?";
            try (PreparedStatement statement = SQL.connection.prepareStatement(query)) {
                statement.setInt(1, orderID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int minutesDifference = resultSet.getInt("minutes_difference");
                    int duration = 0;
                    String query1 = "SELECT DURATION FROM orders WHERE ID = ?";
                    try (PreparedStatement statement1 = SQL.connection.prepareStatement(query1)) {
                        statement1.setInt(1, orderID);
                        ResultSet resultSet1 = statement1.executeQuery();
                        if (resultSet1.next()) {
                            duration = resultSet1.getInt("DURATION");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(duration-minutesDifference<=0)
                    {
                        System.out.println("ORDER IS DELIVERED");
                        String query2 = "UPDATE orders SET orderStatus = ? WHERE ID = ?";
                        PreparedStatement statement1 = SQL.connection.prepareStatement(query2);
                        statement1.setString(1, "sent");
                        statement1.setInt(2, orderID);
                        int rowsAffected = statement1.executeUpdate();
                        if(rowsAffected<=0)
                            System.out.println("Failed to make connection!");
                        Order.findOrderByID(orderID).status="sent";
                        ((Normal)User.loggedInUser).deliver = null;
                    }
                    else
                       System.out.println("THE REMAINING TIME: " + (duration-minutesDifference)+"m");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void showFromNowToRestaurant(int nodeID)/////////////////////////
    {
        if(nodeID==0 || nodeID>1000)
            System.out.println("INVALID NODE ID!");
        else if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A RESTAURANT OWNER NOT A CUSTOMER!");
        else if(((Normal)User.loggedInUser).deliver==null || !((Normal)User.loggedInUser).deliver.status.equals("taken") )
            System.out.println("THERE IS NO ACTIVE ORDER TO DELIVER FOR YOU!");
        else
            Graph.showPath(nodeID,findRestaurantByOrderID(((Normal)User.loggedInUser).deliver.ID).location.ID);
    }
    public static void showFromNowToCustomer(int nodeID)///////////////////////////
    {
        if(nodeID==0 || nodeID>1000)
            System.out.println("INVALID NODE ID!");
        else if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A RESTAURANT OWNER NOT A CUSTOMER!");
        else if(((Normal)User.loggedInUser).deliver==null || !((Normal)User.loggedInUser).deliver.status.equals("taken") )
            System.out.println("THERE IS NO ACTIVE ORDER TO DELIVER FOR YOU!");
        else
            Graph.showPath(nodeID,findUserByOrder(((Normal)User.loggedInUser).deliver.ID).location.ID);
    }
    public static void showDeliveryToRestaurant()/////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A RESTAURANT OWNER NOT A CUSTOMER!");
        else if(((Normal)User.loggedInUser).deliver==null || !((Normal)User.loggedInUser).deliver.status.equals("taken") )
            System.out.println("THERE IS NO ACTIVE ORDER TO DELIVER FOR YOU!");
        else
            Graph.showPath(((Normal) User.loggedInUser).location.ID, findRestaurantByOrderID(((Normal)User.loggedInUser).deliver.ID).location.ID);
    }
    public static void showFromRestaurantToCustomer()///////////////////////////
    {
        if(User.loggedInUser==null)
            System.out.println("LOGIN FIRST!");
        else if(User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A RESTAURANT OWNER NOT A CUSTOMER!");
        else if(((Normal)User.loggedInUser).deliver==null || !((Normal)User.loggedInUser).deliver.status.equals("taken") )
            System.out.println("THERE IS NO ACTIVE ORDER TO DELIVER FOR YOU!");
        else
            Graph.showPath(findRestaurantByOrderID(((Normal)User.loggedInUser).deliver.ID).location.ID,findUserByOrder(((Normal)User.loggedInUser).deliver.ID).location.ID);
    }
}
