import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
public class OwnerFuncs {
    public static void PrintRestaurants()///////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() > 1) {
                Collections.sort(inuse.ownedRestaurants, Restaurant.sortRestaurants());
                for (int i = 0; i < inuse.ownedRestaurants.size(); i++)
                    System.out.println("Name : " + inuse.ownedRestaurants.get(i).name + " ID : " + inuse.ownedRestaurants.get(i).ID);
            } else if (inuse.ownedRestaurants.size() == 1) {
                Restaurant.restaurantInuse = inuse.ownedRestaurants.get(0);
                System.out.println("Name : " + inuse.ownedRestaurants.get(0).name + " ID : " + inuse.ownedRestaurants.get(0).ID);
            } else if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANT!");
        }
    }

    public static Restaurant selectRestaurant(int restaurantID) ///////////////////////////////
    {
        boolean exist = true;
        if (User.loggedInUser == null) {
            System.out.println("LOGIN FIRST!");
            return null;
        } else if (!User.loggedInUser.type.equals("Owner")) {
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
            return null;
        } else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS!");
            else if (inuse.ownedRestaurants.size() == 1)
            {
                Restaurant.restaurantInuse = inuse.ownedRestaurants.get(0);
                System.out.println("YOU ARE LOGGED INTO YOUR RESTAURANT ACCOUNT AUTOMATICALLY!");
            }
            else if (inuse.ownedRestaurants.size() > 1) {
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++)
                    if (inuse.ownedRestaurants.get(i).ID == restaurantID) {
                        exist = false;
                        Restaurant.restaurantInuse = inuse.ownedRestaurants.get(i);
                        System.out.println("RESTAURANT SELECTED SUCCESSFULLY!");
                    }
                if (exist) {
                    System.out.println("YOU DO NOT OWN THIS RESTAURANT!");
                    return null;
                }
            }
        }
        return null;
    }

    public static void showLocation()////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else if (Restaurant.restaurantInuse == null || !((Owner) User.loggedInUser).ownedRestaurants.contains(Restaurant.restaurantInuse))
            System.out.println("YOU HAVE NOT YET SELECTED YOUR RESTAURANT!");
        else
            System.out.println("LOCATION: " + Restaurant.restaurantInuse.location.ID);
    }

    public static void editLocation(int nodeID) throws SQLException////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (nodeID > 1000 || nodeID < 1)
                System.out.println("NEW NODE ID IS INVALID!");
            else {
                int u = Restaurant.restaurantInuse.ID;
                boolean exist = true;
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++) {
                    if (inuse.ownedRestaurants.get(i).ID == u) {
                        exist = false;
                        try {
                            Statement statement = SQL.connection.createStatement();
                            statement.executeUpdate("update restaurants" + " set Location = " + nodeID + " where ID = " + u);
                            Restaurant.restaurantInuse.location = Node.getNodeByID(nodeID);
                            System.out.println("LOCATION EDITED SUCCESSFULLY!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (exist)
                    System.out.println("YOU HAVE NOT SELECTED YOUR RESTAURANT!");
            }
        }
    }

    public static void showFoodType()//////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Restaurant.restaurantInuse.foodTypes.size() != 0)
                System.out.println("RESTAURANT'S FOOD TYPES : " + Restaurant.restaurantInuse.foodTypes);
            else
                System.out.println("YOU HAVE NOT CHOSEN YOUR RESTAURANT'S FOOD TYPES");
        }
    }

    public static boolean checkIfThereIsAnyActiveOrder()throws SQLException////////////////////////////
    {
        updateOrderActivity();
        for (int i = 0; i < Restaurant.restaurantInuse.restaurantHistory.size(); i++)
            if (!Restaurant.restaurantInuse.restaurantHistory.get(i).status.equals("sent"))
                return true;
        return false;
    }

    public static boolean DeleteFoodToEditFOODTYPE() throws SQLException///////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (checkIfThereIsAnyActiveOrder())
                System.out.println("FOOD TYPE CHANGING CANCELLED DUE TO ACTIVE ORDERS!");
            else {
                System.out.println("ARE YOU SURE YOU WANT TO CHANGE YOUR RESTAURANT TYPE?" + "\n" + "PLEASE ONLY ANSWER BY \"YES\" Or \"NO\" !!!");
                {
                    String s = Main.input.nextLine();
                    if (s.equals("YES")) {
                        try {
                            Statement statement = SQL.connection.createStatement();
                            statement.executeUpdate("delete from restaurant_food" + " where restaurantID = " + Restaurant.restaurantInuse.ID);
                            statement.executeUpdate("delete from restaurant_type" + " where restaurantID = " + Restaurant.restaurantInuse.ID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Restaurant.restaurantInuse.menu.clear();
                        Restaurant.restaurantInuse.foodTypes.clear();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void insertNewFoodType(String[] splitted) throws SQLException//////////////////////////////
    {
        if (DeleteFoodToEditFOODTYPE()) {
            ArrayList<String> newType = new ArrayList<>();
            for (int i = 2; i < splitted.length; i++)
                newType.add(splitted[i]);
            try {
                PreparedStatement stm = SQL.connection.prepareStatement("insert into restaurant_type (foodType,restaurantID) values (?,?);");
                for (int i = 0; i < newType.size(); i++) {
                    stm.setString(1, newType.get(i));
                    stm.setInt(2, Restaurant.restaurantInuse.ID);
                    stm.executeUpdate();
                }
                Restaurant.restaurantInuse.foodTypes = newType;
                System.out.println("FOODTYPE ADDED SUCCESSFULLY!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showMenu()throws SQLException ///////////////////////////////////
    {
        updateDiscountActivity();
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Restaurant.restaurantInuse.menu.size() != 0) {
                for (int i = 0; i < Restaurant.restaurantInuse.menu.size(); i++) {
                    System.out.print("ID : " + Restaurant.restaurantInuse.menu.get(i).ID + " NAME : " + Restaurant.restaurantInuse.menu.get(i).name
                            + " PRICE : " + Restaurant.restaurantInuse.menu.get(i).price + " ACTIVATION STATUS : " + Restaurant.restaurantInuse.menu.get(i).active + " DISCOUNT : ");
                    if (Restaurant.restaurantInuse.menu.get(i).discount == null || Restaurant.restaurantInuse.menu.get(i).discount.active == false || Restaurant.restaurantInuse.menu.get(i).discount.percent == 0)
                        System.out.println("0%");
                    else
                        System.out.println(Restaurant.restaurantInuse.menu.get(i).discount.percent + "%");
                }
            } else
                System.out.println("NO FOOD IN MENU YET!");
        }
    }

    public static void editFoodName(int foodID, String newName) throws SQLException //////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else {
                if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                    System.out.println("NO FOOD WITH THIS ID IN MENU!");
                else {
                    Food.findFoodByID(foodID).name = newName;
                    try {
                        Statement statement = SQL.connection.createStatement();
                        if (statement.executeUpdate("update food" + " set title = " + "'" + newName + "'" + "where ID = " + foodID) > 0)
                            System.out.println("FOOD NAME EDITED SUCCESSFULLY!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void editFoodPrice(int foodID, Double newPrice) throws SQLException//////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANT!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else {
                if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                    System.out.println("NO FOOD WITH THIS ID IN MENU!");
                else {
                    Food.findFoodByID(foodID).price = newPrice;
                    try {
                        Statement statement = SQL.connection.createStatement();
                        if (statement.executeUpdate("update food" + " set price = " + newPrice + "where ID = " + foodID) > 0)
                            System.out.println("FOOD PRICE EDITED SUCCESSFULLY!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void addFood(String name, Double price) throws SQLException ///////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU DO NOT OWN ANY RESTAURANT!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else {
                try {
                    PreparedStatement stm = SQL.connection.prepareStatement("insert into food (activity,price,title) values (?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    stm.setBoolean(1, true);
                    stm.setDouble(2, price);
                    stm.setString(3, name);
                    if (stm.executeUpdate() > 0) {
                        ResultSet generatedKeys = stm.getGeneratedKeys();
                        int generatedID = 0;
                        if (generatedKeys.next())
                            generatedID = generatedKeys.getInt(1);
                        Food added = new Food(generatedID, name, price, true);
                        Restaurant.restaurantInuse.menu.add(added);
                        Food.foods.add(added);
                        generatedKeys.close();
                        stm = SQL.connection.prepareStatement("insert into restaurant_food (foodID,restaurantID) values (?,?);");
                        stm.setInt(2, Restaurant.restaurantInuse.ID);
                        stm.setInt(1, added.ID);
                        if (stm.executeUpdate() > 0)
                            System.out.println("FOOD ADDED SUCCESSFULLY!");
                        else
                            System.out.println("failed to make connection to database.");
                    } else System.out.println("failed to make connection to database.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deleteFood(int foodID) throws SQLException ///////////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU DO NOT OWN ANY RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                System.out.println("NO SUCH FOOD IN MENU!");
            else {
                if (checkIfThereIsAnyActiveOrder())
                    System.out.println("THERE ARE SOME ACTIVE ORDERS, YOU CAN'T DELETE THIS FOOD NOW!");
                else {
                    System.out.println("ARE YOU SURE TO DELETE THIS FOOD?" + "\n" + "PLEASE ANSWER BY \"YES\" Or \"NO\"!");
                    String s = Main.input.nextLine();
                    if (s.equals("YES")) {
                        Restaurant.restaurantInuse.menu.remove(Food.findFoodByID(foodID));
                        Food.foods.remove((Food.findFoodByID(foodID)));
                        try {
                            Statement statement = SQL.connection.createStatement();
                            statement.executeUpdate("SET SQL_SAFE_UPDATES = 0");
                            if (statement.executeUpdate("DELETE FROM food WHERE ID = " + foodID) > 0)
                                if (statement.executeUpdate("DELETE FROM food_comment WHERE foodID = " + foodID) >= 0)
                                    if (statement.executeUpdate("DELETE FROM food_discount WHERE foodID = " + foodID) >= 0)
                                        if (statement.executeUpdate("DELETE FROM food_rating WHERE foodID = " + foodID) >= 0)
                                            if (statement.executeUpdate("DELETE FROM order_food WHERE foodID = " + foodID) >= 0)
                                                if (statement.executeUpdate("DELETE FROM restaurant_food WHERE foodID = " + foodID) > 0)
                                                    System.out.println("FOOD DELETED SUCCESSFULLY!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void deActiveFood(int foodID) throws SQLException//////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU DO NOT OWN ANY RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                System.out.println("NO SUCH FOOD IN MENU!");
            else if (checkIfThereIsAnyActiveOrder())
                System.out.println("THERE ARE SOME ACTIVE ORDERS , YOU CAN'T DEACTIVATE THIS FOOD!");
            else if (!Food.findFoodByID(foodID).active )
                System.out.println("FOOD IS ALREADY DEACTIVATED!");
            else {
                try {
                    if (SQL.connection != null) {
                        Statement stm = SQL.connection.createStatement();
                        if (stm.executeUpdate("update food set activity = false where ID = " + foodID) > 0)
                            System.out.println("FOOD DEACTIVATED SUCCESSFULLY!");
                        Food.findFoodByID(foodID).active = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void activeFood(int foodID) throws SQLException//////////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU DO NOT OWN ANY RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                System.out.println("NO SUCH FOOD IN MENU!");
            else if (checkIfThereIsAnyActiveOrder())
                System.out.println("THERE ARE SOME ACTIVE ORDERS , YOU CAN'T ACTIVATE THIS FOOD!");
            else if (Food.findFoodByID(foodID).active)
                System.out.println("FOOD IS ALREADY ACTIVATED!");
            else {
                try {
                    if (SQL.connection != null) {
                        Statement stm = SQL.connection.createStatement();
                        if (stm.executeUpdate("update food set activity = true where ID = " + foodID) > 0)
                            System.out.println("FOOD ACTIVATED SUCCESSFULLY!");
                        Food.findFoodByID(foodID).active = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void SetDiscountFood(int foodID, int amount, int duration) throws SQLException/////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                System.out.println("NO SUCH FOOD IN MENU!");
            else if (amount > 50)
                System.out.println("SORRY THE AMOUNT OF DISCOUNT CAN NOT EXCEED 50% !");
            else {
                if (Food.findFoodByID(foodID).discount != null) {
                    String query = "SELECT TIMESTAMPDIFF(MINUTE, activationTime, NOW()) AS minutes_difference " + "FROM discount " + "WHERE ID = ?";
                    try (PreparedStatement statement = SQL.connection.prepareStatement(query)) {
                        statement.setInt(1, Food.findFoodByID(foodID).discount.ID);
                        ResultSet resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            int minutesDifference = resultSet.getInt("minutes_difference");
                            int Duration = 0;
                            String query1 = "SELECT DURATION FROM discount WHERE ID = ?";
                            try (PreparedStatement statement1 = SQL.connection.prepareStatement(query1)) {
                                statement1.setInt(1, Food.findFoodByID(foodID).discount.ID);
                                ResultSet resultSet1 = statement1.executeQuery();
                                if (resultSet1.next()) {
                                    Duration = resultSet1.getInt("DURATION");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (Duration - minutesDifference > 0)
                                System.out.println("THERE IS AN ACTIVE DISCOUNT!");
                            else {
                                Food.findFoodByID(foodID).discount.active = false;
                                Discount New = new Discount(Discount.allDiscounts.size() + 1, amount, true, duration);
                                Food.findFoodByID(foodID).discount = New;
                                Discount.allDiscounts.add(New);
                                New.activationTime = Instant.now();
                                query1 = "INSERT INTO discount(activationTime,activity,duration,percent) VALUES ( ?, ?,?,?)";
                                PreparedStatement statement1 = SQL.connection.prepareStatement(query1);
                                statement1.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                                statement1.setBoolean(2, true);
                                statement1.setInt(3, duration);
                                statement1.setInt(4, amount);
                                int rowsAffected = statement1.executeUpdate();
                                if (rowsAffected <= 0)
                                    System.out.println("Failed to make connection!");
                                query1 = "INSERT INTO food_discount(discountID, foodID) VALUES ( ?, ?)";
                                statement1 = SQL.connection.prepareStatement(query1);
                                statement1.setInt(1, Discount.allDiscounts.size());
                                statement1.setInt(2, foodID);
                                rowsAffected = statement1.executeUpdate();
                                if (rowsAffected > 0)
                                    System.out.println("DISCOUNT SET SUCCESSFULLY!");
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Discount New = new Discount(Discount.allDiscounts.size() + 1, amount, true, duration);
                    Food.findFoodByID(foodID).discount = New;
                    Discount.allDiscounts.add(New);
                    New.activationTime = Instant.now();
                    String query1 = "INSERT INTO discount(activationTime,activity,duration,percent) VALUES ( ?, ?,?,?)";
                    PreparedStatement statement1 = SQL.connection.prepareStatement(query1);
                    statement1.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    statement1.setBoolean(2, true);
                    statement1.setInt(3, duration);
                    statement1.setInt(4, amount);
                    int rowsAffected = statement1.executeUpdate();
                    if (rowsAffected <= 0)
                        System.out.println("Failed to make connection!");
                    query1 = "INSERT INTO food_discount(discountID, foodID) VALUES ( ?, ?)";
                    statement1 = SQL.connection.prepareStatement(query1);
                    statement1.setInt(1, Discount.allDiscounts.size());
                    statement1.setInt(2, foodID);
                    rowsAffected = statement1.executeUpdate();
                    if (rowsAffected > 0)
                        System.out.println("DISCOUNT SET SUCCESSFULLY!");
                }
            }
        }
    }
    public static void addRestaurant(String name , int location)throws SQLException/////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else if(location<1 || location>1000)
            System.out.println("INVALID LOCATION!");
        else
        {
            Restaurant NEW = new Restaurant(Restaurant.restaurants.size()+1,name,null,Node.getNodeByID(location),(Owner)User.loggedInUser);
            Restaurant.restaurants.add(NEW);
            ((Owner)User.loggedInUser).ownedRestaurants.add(NEW);
            try
            {
                if (SQL.connection != null)
                {
                    PreparedStatement stm = SQL.connection.prepareStatement("insert into restaurants (location,ownerID,title) values (?,?,?);");
                    stm.setInt(1, location);
                    stm.setInt(2, User.loggedInUser.ID);
                    stm.setString(3, name);
                    if (stm.executeUpdate() > 0) {
                        System.out.println("RESTAURANT ADDED SUCCESSFULLY!");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try
            {
                if (SQL.connection != null)
                {
                    PreparedStatement stm = SQL.connection.prepareStatement("insert into user_restaurant (restaurantID,userID) values (?,?);");
                    stm.setInt(1,Restaurant.restaurants.size());
                    stm.setInt(2, User.loggedInUser.ID);
                    stm.executeUpdate();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void updateOrderActivity()throws SQLException ///////////////////////////////////////////////
    {
        for(int i=0 ; i<Order.allOrders.size() ; i++)
            if(Order.allOrders.get(i).status.equals("taken"))
            {
                String query = "SELECT TIMESTAMPDIFF(MINUTE, whenTaken, NOW()) AS minutes_difference " + "FROM orders " + "WHERE ID = ?";
                try (PreparedStatement statement = SQL.connection.prepareStatement(query)) {
                    statement.setInt(1, Order.allOrders.get(i).ID);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int minutesDifference = resultSet.getInt("minutes_difference");
                        int Duration = 0;
                        String query1 = "SELECT DURATION FROM orders WHERE ID = ?";
                        try (PreparedStatement statement1 = SQL.connection.prepareStatement(query1)) {
                            statement1.setInt(1, Order.allOrders.get(i).ID);
                            ResultSet resultSet1 = statement1.executeQuery();
                            if (resultSet1.next()) {
                                Duration = resultSet1.getInt("DURATION");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (Duration - minutesDifference <= 0)
                        {
                            Order.allOrders.get(i).status = "sent";
                            String query2 = "UPDATE orders SET orderStatus = ? WHERE ID = ?";
                            PreparedStatement statement1 = SQL.connection.prepareStatement(query2);
                            statement1.setString(1, "sent");
                            statement1.setInt(2, Order.allOrders.get(i).ID);
                            int rowsAffected = statement1.executeUpdate();
                            if(rowsAffected<=0)
                                System.out.println("Failed to make connection!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    public static void updateDiscountActivity()throws SQLException////////////////////////////////
    {
        for(int i=0 ; i<Food.foods.size() ; i++)
            if(Food.foods.get(i).discount!=null) {
                String query = "SELECT TIMESTAMPDIFF(MINUTE, activationTime, NOW()) AS minutes_difference " + "FROM discount " + "WHERE ID = ?";
                try (PreparedStatement statement = SQL.connection.prepareStatement(query)) {
                    statement.setInt(1, Food.foods.get(i).discount.ID);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int minutesDifference = resultSet.getInt("minutes_difference");
                        int Duration = 0;
                        String query1 = "SELECT DURATION FROM discount WHERE ID = ?";
                        try (PreparedStatement statement1 = SQL.connection.prepareStatement(query1)) {
                            statement1.setInt(1, Food.foods.get(i).discount.ID);
                            ResultSet resultSet1 = statement1.executeQuery();
                            if (resultSet1.next()) {
                                Duration = resultSet1.getInt("DURATION");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (Duration - minutesDifference <= 0)
                        {
                            Food.foods.get(i).discount.active = false;
                            String query2 = "UPDATE discount SET activity = ? WHERE ID = ?";
                            PreparedStatement statement1 = SQL.connection.prepareStatement(query2);
                            statement1.setBoolean(1, false);
                            statement1.setInt(2, Food.foods.get(i).discount.ID);
                            int rowsAffected = statement1.executeUpdate();
                            if(rowsAffected<=0)
                                System.out.println("Failed to make connection!");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }
    public static void changeOrderStatusManually(int orderID, String status)throws SQLException//////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else
        {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Order.findOrderByID(orderID) == null || !Restaurant.restaurantInuse.restaurantHistory.contains(Order.findOrderByID(orderID)))
                System.out.println("THIS ORDER IS NOT RELATED TO YOUR RESTAURANT!");
            else if(!(status.equals("sending") || status.equals("taken") || status.equals("sent")))
                System.out.println("INVALID STATUS!");
            else
            {
                String query = "UPDATE orders SET orderStatus = ? WHERE ID = ?";
                PreparedStatement statement = SQL.connection.prepareStatement(query);
                statement.setString(1, status);
                statement.setInt(2, orderID);
                int rowsAffected = statement.executeUpdate();
                if(rowsAffected>0)
                    System.out.println("STATUS UPDATED SUCCESSFULLY!");
                Order.findOrderByID(orderID).status = status;
            }
        }
    }
/// map _ suggestion _ tashvighi
    public static void selectFoodToResponse(int foodID)///////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.findFoodByID(foodID) == null || !Restaurant.restaurantInuse.menu.contains(Food.findFoodByID(foodID)))
                System.out.println("NO FOOD WITH THIS ID!");
            else {
                Food.foodInuse = Food.findFoodByID(foodID);
                System.out.println("FOOD SELECTED SUCCESSFULLY!");
            }
        }
    }

    public static void displayRating() ////////////////////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU DO NOT OWN ANY RESTAURANT!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.foodInuse == null || !Restaurant.restaurantInuse.menu.contains(Food.foodInuse))
                System.out.println("PLEASE SELECT THE FOOD FIRST!");
            else {
                if (Food.foodInuse.ratings.size() != 0)
                    System.out.println("FOOD RATING : " + String.format("%.2f", NormalFuncs.calculateTotalRestaurantRating(Restaurant.restaurantInuse)));
                else
                    System.out.println("NO RATING YET!");
            }
        }
    }

    public static void displayComments()//////////////////////////////////////
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.foodInuse == null || !Restaurant.restaurantInuse.menu.contains(Food.foodInuse))
                System.out.println("PLEASE SELECT THE FOOD FIRST!");
            else if (Food.foodInuse.comments.size() == 0)
                System.out.println("NO COMMENTS YET!");
            else {
                for (int i = 0; i < Food.foodInuse.comments.size(); i++) {
                    System.out.println("COMMENT ID: " + Food.foodInuse.comments.get(i).ID + "  COMMENT: " + Food.foodInuse.comments.get(i).text);
                }
            }
        }
    }
    public static void addResponse(int commentID, String[] splitted) throws SQLException ///////////////////////////////////////
    {
        String message = "";
        for(int i=4; i< splitted.length; i++)
            message+=(splitted[i]+" ");
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else
        {
            Owner inuse = (Owner)User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.foodInuse == null || !Restaurant.restaurantInuse.menu.contains(Food.foodInuse) )
                System.out.println("PLEASE SELECT THE FOOD FIRST!");
            else if (Food.foodInuse.comments.size() == 0)
                System.out.println("THERE ARE NO COMMENTS YET!");
            else if (Comment.findCommentByID(commentID)==null || !Food.foodInuse.comments.contains(Comment.findCommentByID(commentID)))
                System.out.println("COMMENT WITH THIS ID DOESN'T EXIST HERE!");
            else if(Comment.findCommentByID(commentID).response!=null)
                System.out.println("YOU HAVE ALREADY ANSWERED THIS COMMENT!");
            else
            {
                Comment.commentInUse = Comment.findCommentByID(commentID);
                Comment.commentInUse.response = new Response(Response.allResponses.size()+1 ,message, commentID, (Owner)User.loggedInUser);
                Response.allResponses.add(Comment.commentInUse.response);
                try
                {
                   if (SQL.connection != null)
                   {
                    PreparedStatement stm = SQL.connection.prepareStatement("insert into response (commentID,ownerID,responsedText) values (?,?,?);");
                    stm.setInt(1, commentID);
                    stm.setInt(2, User.loggedInUser.ID);
                    stm.setString(3, message);
                    if (stm.executeUpdate() > 0) {
                        Statement statement = SQL.connection.createStatement();
                        if (statement.executeUpdate("update comments" + " set responseID = " + Comment.commentInUse.response.ID + " where ID = " + commentID) > 0)
                            System.out.println("RESPONSE ADDED SUCCESSFULLY!");
                    }
                   }
                }
                catch (Exception e)
                {
                e.printStackTrace();
                }
            }
        }
    }
    public static void editResponse(int commentID,String[] splitted) throws SQLException///////////////////////////
    {
        String message = "";
        for(int i=3; i< splitted.length; i++)
            message+=(splitted[i]+" ");
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else
        {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Food.foodInuse == null || !Restaurant.restaurantInuse.menu.contains(Food.foodInuse) )
                System.out.println("PLEASE SELECT THE FOOD FIRST!");
            else if (Food.foodInuse.comments.size() == 0)
                System.out.println("THERE ARE NO COMMENTS YET!");
            else if (Comment.findCommentByID(commentID)==null || !Food.foodInuse.comments.contains(Comment.findCommentByID(commentID)))
                System.out.println("COMMENT WITH THIS ID DOESN'T EXIST HERE!");
            else if(Comment.findCommentByID(commentID).response==null)
                System.out.println("YOU HAVE NOT ANSWERED THIS COMMENT!");
            else
            {
                Comment.commentInUse = Comment.findCommentByID(commentID);
                Comment.commentInUse.response.massage=message;
                try {
                    if (SQL.connection != null) {
                        Statement statement = SQL.connection.createStatement();
                        statement.executeUpdate("SET SQL_SAFE_UPDATES = 0");
                        PreparedStatement stm = SQL.connection.prepareStatement("update response set responsedText = ? where commentID = "+commentID);
                        stm.setString(1,message);
                        if(stm.executeUpdate()>0)
                            System.out.println("RESPONSE EDITED SUCCESSFULLY!");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void activeOrders() throws SQLException//////////////////////////
    {
        updateOrderActivity();
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else
        {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Restaurant.restaurantInuse.restaurantHistory.size() == 0)
                System.out.println("NO ORDERS YET!");
            else
            {
                int count =0;
                for(int i=0; i<Restaurant.restaurantInuse.restaurantHistory.size(); i++)
                    if(!Restaurant.restaurantInuse.restaurantHistory.get(i).status.equals("sent"))
                    {
                       System.out.print("ACTIVE ORDER ID : "+Restaurant.restaurantInuse.restaurantHistory.get(i).ID+"  FOODS :");
                       for(int k=0;k<Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.size();k++)
                        System.out.print(Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.get(k).name+" ");
                    System.out.println();
                    count++;
                    }
                if(count==0)
                    System.out.println("THERE ARE NO ACTIVE ORDERS!");
            }
        }
    }
    public static void orderHistory()//////////////////////////////
    {
        if (User.loggedInUser == null)
           System.out.println("LOGIN FIRST!");
        else if (!User.loggedInUser.type.equals("Owner"))
           System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!");
        else
        {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU OWN NO RESTAURANTS!");
            else if (Restaurant.restaurantInuse == null || !inuse.ownedRestaurants.contains(Restaurant.restaurantInuse))
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!");
            else if (Restaurant.restaurantInuse.restaurantHistory.size() == 0)
                System.out.println("NO ORDERS YET!");
            else
            {
                 for(int i=0;i<Restaurant.restaurantInuse.restaurantHistory.size();i++)
                 {
                      System.out.print("ORDER ID : "+Restaurant.restaurantInuse.restaurantHistory.get(i).ID+" "+"ORDERED FOODS : ");
                      for (int k = 0; k < Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.size(); k++)
                          System.out.print(Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.get(k).name+" ");
                      System.out.println();
                 }
            }
        }
    }
}
