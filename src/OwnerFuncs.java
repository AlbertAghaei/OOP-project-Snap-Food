import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class OwnerFuncs {
    public static void PrintRestaurants()
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner") )
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() > 1) {
                Collections.sort(inuse.ownedRestaurants, Restaurant.sortRestaurants());
                for (int i = 0; i < inuse.ownedRestaurants.size(); i++)
                    System.out.println("Name : " + inuse.ownedRestaurants.get(i).name + " ID : " + inuse.ownedRestaurants.get(i).ID);
            } else if (inuse.ownedRestaurants.size() == 1) {
                Restaurant.restaurantInuse = inuse.ownedRestaurants.get(0);
                System.out.println("YOU LOGIN TO YOUR RESTAURANT : " + inuse.ownedRestaurants.get(0).name);
            } else if (inuse.ownedRestaurants.size() == 0)
                System.out.println("You have not registered your restaurants yet");
        }
    }
    public static Restaurant selectRestaurant(int restaurantID) //determine restaurantInuse
    {
        boolean exist = true;
        if (User.loggedInUser == null) {
            System.out.println("LOGIN FIRST!!");
            return null;
        } else if (!User.loggedInUser.type.equals("Owner")) {
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
            return null;
        } else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS!!!");
            else if (inuse.ownedRestaurants.size() == 1)
                System.out.println("YOU HAVE ALREADY LOGIN IN YOUR RESTAURANT AUTOMATICALLY!!");
            else if (inuse.ownedRestaurants.size() > 1) {
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++) {
                    if (inuse.ownedRestaurants.get(i).ID == restaurantID) {
                        exist = false;
                        return inuse.ownedRestaurants.get(i);
                    }
                }
                if (exist) {
                    System.out.println("THERE IS NO RESTAURANTS WITH THIS ID IN YOUR ACCOUNT!!");
                    return null;
                }
            }
        }
        return null;
    }
    public static void editLocation(int nodeID) throws SQLException
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if (nodeID > 1000 || nodeID < 1)
                System.out.println("NEW NODE ID IS INVALID!!");
            else {
                int u = Restaurant.restaurantInuse.ID;
                boolean exist = true;
                for (int i = 0; i < inuse.ownedRestaurants.size() && exist; i++) {
                    if (inuse.ownedRestaurants.get(i).ID == u) {
                        exist = false;
                        inuse.ownedRestaurants.get(i).location.ID = nodeID;
                        Restaurant.restaurantInuse.location.ID = nodeID;
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
                                    statement.executeUpdate("update restaurants" + " set Location = " + nodeID + "where ID = " + u);
                                    System.out.println("Location edits successfully");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (exist)
                    System.out.println("YOU HAVE NOT SELECTED YOUR RESTAURANT!!");
            }
        }


    }
    public static void showFoodType()
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if(Restaurant.restaurantInuse.foodTypes.size()!=0)
                System.out.println("Restaurant's FoodType : " +Restaurant.restaurantInuse.foodTypes);
            else if(Restaurant.restaurantInuse.foodTypes.size()==0)
                System.out.println("YOU HAVE NOT CHOOSE YOUR RESTAURANT's FOOD TYPE");
        }
    }
    public static boolean DeleteFoodToEditFOODTYPE() throws SQLException
    {   if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Restaurant.restaurantInuse.restaurantHistory.size()!=0) //check if there is no order
            System.out.println("Sorry you are not able to change the foodType , There are still some active orders");
        else{
        //confirmation about foodType in OwnerFunctions
            System.out.println("Are you sure you want to change your restaurant type ?!"+"\n"+"PLEASE ANSWER BY YES Or NO !!!");
         { String s = new String("");
            s=Main.scanner.nextLine();
        ////// after confirmation
        //delete foodList in sql
             if(s.equals("YES"))
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
                    statement.executeUpdate("delete from restaurant_food"+"where restaurntID = "+Restaurant.restaurantInuse.ID);
                    statement.executeUpdate("delete from restaurant_type"+"where restaurntID = "+Restaurant.restaurantInuse.ID);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Restaurant.restaurantInuse.menu.clear();
            Restaurant.restaurantInuse.foodTypes.clear();
            System.out.println("PLEASE ENTER YOUR NEW FOOD LIST LIKE THIS PATTERN : "+"\n"+"FOOD TITLE & FOOD PRICE & FOOD TYPE ");
            return true;
        }

            }
            }}
    return false;
    }
    public static void insertNewFoodListInEditFoodType(String [] newType, ArrayList<Food> newMenu)  throws SQLException //if deleteFOODLISt = true;
    {   //add new type in sql
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
                PreparedStatement stm = connection.prepareStatement("insert into restaurant_type (foodType,restaurantID) values (?,?);");
                for(int i=0;i<newType.length;i++) //foodType
                {stm.setString(1,newType[i]);
                 stm.setInt(2,Restaurant.restaurantInuse.ID);
                 stm.executeUpdate();
                }
                // food menu
                stm = connection.prepareStatement("insert into restaurant_food (foodID,restaurantID) values (?,?);");
                for(int i=0;i<newMenu.size();i++)
                {stm.setInt(1,newMenu.get(i).ID);
                 stm.setInt(2,Restaurant.restaurantInuse.ID);
                 stm.executeUpdate();
                }
                stm = connection.prepareStatement("insert into food (ID,activity,foodType,price,title) values (?,?,?,?,?);");
                for (int i=0;i<newMenu.size();i++) //food sql
                {   stm.setInt(1,newMenu.get(i).ID);
                    stm.setBoolean(2,true); // activity : default true
                    stm.setString(3,newMenu.get(i).foodType);
                    stm.setDouble(4,newMenu.get(i).price);
                    stm.setString(5,newMenu.get(i).name);
                    stm.executeUpdate();
                }
                // update in intellij
                {   //update new type in inuse restaurant
                    for (int i = 0; i < newType.length; i++)
                        Restaurant.restaurantInuse.foodTypes.add(newType[i]);
                    //copy new menu in inuse restaurant
                    for (int i = 0; i < newMenu.size(); i++) {
                        Restaurant.restaurantInuse.menu.add(new Food(newMenu.get(i).name, newMenu.get(i).price, newMenu.get(i).foodType));
                        Restaurant.restaurantInuse.menu.get(i).ID = newMenu.get(i).ID;
                        Restaurant.restaurantInuse.menu.get(i).active = true;
                    }
                    System.out.println("YOUR NEW FOOD MENU ADDED SUCCESSFULLY!!");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showMenu()
    {  if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Restaurant.restaurantInuse.menu.size()!=0)
        {for(int i=0;i<Restaurant.restaurantInuse.menu.size();i++)
        {
            System.out.println("ID : "+Restaurant.restaurantInuse.menu.get(i).ID+" NAME : "+Restaurant.restaurantInuse.menu.get(i).name
           +" PRICE : " +Restaurant.restaurantInuse.menu.get(i).price+" DISCOUNT :"+Restaurant.restaurantInuse.menu.get(i).discount.active);

        }

        }
        else if(Restaurant.restaurantInuse.menu.size()==0)
            System.out.println("YOU HAVE NOT ENTER YOUR FOOD MENU!!");
    }

    }
    public static void editFoodName(int foodID,String newName) throws SQLException
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (User.loggedInUser.type != "Owner")
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {boolean exist = true;
           for(int j=0;j<Restaurant.restaurantInuse.menu.size()&&exist;j++)
        {
            if(Restaurant.restaurantInuse.menu.get(j).ID==foodID)
        {Restaurant.restaurantInuse.menu.get(j).name = newName;
         // update sql in food
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
              if(statement.executeUpdate("update food" + " set title = " +"'"+ newName + "'" + "where ID = " + foodID)>0)
              System.out.println("FOOD NAME EDITED SUCCESSFULLY!");
                    }
            } catch (Exception e) {
                 e.printStackTrace();
            }
           exist=false;
        }
        }
           if(exist)
               System.out.println("something is going wrong in edit food name");

        }
    }

    }
    public static void editFoodPrice(int foodID,Double newPrice) throws SQLException
    {if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (User.loggedInUser.type != "Owner")
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {boolean exist = true;
            for(int j=0;j<Restaurant.restaurantInuse.menu.size()&&exist;j++)
            {
                if(Restaurant.restaurantInuse.menu.get(j).ID==foodID)
                {Restaurant.restaurantInuse.menu.get(j).price = newPrice;
                    // update sql in food
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
                            if(statement.executeUpdate("update food" + " set price = " + newPrice + "where ID = " + foodID)>0)
                                System.out.println("FOOD PRICE EDITED SUCCESSFULLY!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    exist=false;
                }
            }
            if(exist)
                System.out.println("something is going wrong in edit food name");

        }
    }

    }
    public static void addFood(String name,Double price) throws SQLException
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner") )
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {int i = Restaurant.restaurantInuse.menu.size();
            Restaurant.restaurantInuse.menu.add(new Food(name,price,null));
            Restaurant.restaurantInuse.menu.get(i).ID=Restaurant.restaurantInuse.menu.size();
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
                    PreparedStatement stm = connection.prepareStatement("insert into food (activity,ID,price,title) values (?,?,?,?);");
                        stm.setBoolean(1, true);
                        stm.setInt(2, Restaurant.restaurantInuse.menu.get(i).ID);
                        stm.setDouble(3,price);
                        stm.setString(4,name);
                       if(stm.executeUpdate()>0)
                       {stm = connection.prepareStatement("insert into restaurant_food (foodID,restaurantID) values (?,?);");
                           stm.setInt(2,Restaurant.restaurantInuse.ID);
                           stm.setInt(1,Restaurant.restaurantInuse.menu.size());
                           if(stm.executeUpdate()>0)
                               System.out.println("FOOD ADDED SUCCESSFULLY!!");
                           else System.out.println("THERE ARE SOMETHING WRONG IN DATABASE");
                       }
                       else System.out.println("THERE ARE SOMETHING WRONG IN DATABASE");
                }
            }
            catch (Exception e)
            {e.printStackTrace();}
        }
    }

    }
    public static void deleteFood(int foodID) throws SQLException
    {if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner") )
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {
            boolean exist = true;
            String url;
            String username;
            String password;
            Connection connection;
            url = "jdbc:mysql://localhost:3306/oop-project-snapfood";
            username = "root";
            password = "W@2915djkq#";
            for (int i=0;i<Restaurant.restaurantInuse.menu.size()&&exist;i++)
            {
              boolean active = true;
                if(Restaurant.restaurantInuse.menu.get(i).ID==foodID)
            {
                for(int j=0;j<Restaurant.restaurantInuse.restaurantHistory.size()&&active;j++)
                {
                for (int k=0;k<Restaurant.restaurantInuse.restaurantHistory.get(j).orderedFoods.size()&&active;k++)
                  {    if(Restaurant.restaurantInuse.restaurantHistory.get(j).orderedFoods.get(k).ID==foodID)
                  {
                      try {
                    connection = DriverManager.getConnection(url, username, password);
                    if (connection != null) {
                  PreparedStatement stm = connection.prepareStatement("select orderStatus from orders where ID = ?");
                  ResultSet rl;
                   stm.setInt(1,Restaurant.restaurantInuse.restaurantHistory.get(j).ID);
                   rl = stm.executeQuery();
                    if(rl.next())
                    {Restaurant.restaurantInuse.restaurantHistory.get(j).status = rl.getString("orderStatus");
                        if(Restaurant.restaurantInuse.restaurantHistory.get(j).status.equals("sending"))
                        {active=false;
                        }
                    }
                    }
                   }
                  catch (Exception e)
                  {e.printStackTrace();}
                  }
                  }
            }
                if(!active)
                    System.out.println("THERE ARE SOME ACTIVE ORDERS , YOU CAN'T DELETE THIS FOOD!!");
                else if(active) {
                    System.out.println("ARE YOU SURE TO DELETE THIS FOOD?" + "\n" + "PLEASE ANSWER BY YES Or NO!!");
                    String s = new String("");
                    s = Main.scanner.nextLine();
                    if(s.equals("YES"))
                    {Restaurant.restaurantInuse.menu.remove(i);
                    try {
                        connection = DriverManager.getConnection(url, username, password);
                        if (connection != null) {
                            Statement statement = connection.createStatement();
                        if (statement.executeUpdate("DELETE FROM food where ID = " + foodID) > 0) {
                        if (statement.executeUpdate("DELETE FROM food_comment where foodID = " + foodID) > 0)
                        if (statement.executeUpdate("DELETE FROM food_discount where foodID = " + foodID) > 0)
                        if (statement.executeUpdate("DELETE FROM food_rating where foodID = " + foodID) > 0)
                        if (statement.executeUpdate("DELETE FROM order_food where foodID = " + foodID) > 0)
                        if (statement.executeUpdate("DELETE FROM restaurant_food where foodID = " + foodID) > 0)
                          System.out.println("FOOD DELETED SUCCESSFULLY!!!!");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                    }
                exist =false;
                }

            }
            if(exist)
                System.out.println("FOOD WITH THIS ID DOESN'T EXIST!!");
            }

        }
    }
    public static void deActiveFood(int foodID) throws SQLException
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner") )
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {boolean exist = true;
            String url;
            String username;
            String password;
            Connection connection;
            url = "jdbc:mysql://localhost:3306/oop-project-snapfood";
            username = "root";
            password = "W@2915djkq#";
            for (int i=0;i<Restaurant.restaurantInuse.menu.size()&&exist;i++)
            {
                boolean active = true;
                if(Restaurant.restaurantInuse.menu.get(i).ID==foodID)
                {
                    for(int j=0;j<Restaurant.restaurantInuse.restaurantHistory.size()&&active;j++)
                    {
                        for (int k=0;k<Restaurant.restaurantInuse.restaurantHistory.get(j).orderedFoods.size()&&active;k++)
                        {    if(Restaurant.restaurantInuse.restaurantHistory.get(j).orderedFoods.get(k).ID==foodID)
                        {
                            try {
                                connection = DriverManager.getConnection(url, username, password);
                                if (connection != null) {
                                    PreparedStatement stm = connection.prepareStatement("select orderStatus from orders where ID = ?");
                                    ResultSet rl;
                                    stm.setInt(1,Restaurant.restaurantInuse.restaurantHistory.get(j).ID);
                                    rl = stm.executeQuery();
                                    if(rl.next())
                                    {Restaurant.restaurantInuse.restaurantHistory.get(j).status = rl.getString("orderStatus");
                                        if(Restaurant.restaurantInuse.restaurantHistory.get(j).status.equals("sending"))
                                        {active=false;
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {e.printStackTrace();}
                        }
                        }
                    }
                    if(!active)
                        System.out.println("THERE ARE SOME ACTIVE ORDERS , YOU CAN'T DEACTIVATE THIS FOOD!!");
                    else if(active) {
                        Restaurant.restaurantInuse.menu.get(i).active=false;
                            try {
                           connection = DriverManager.getConnection(url, username, password);
                        if (connection != null) {
                           Statement stm= connection.createStatement();
                           if(stm.executeUpdate("update food set activity = false where ID = "+foodID)>0)
                               System.out.println("FOOD DEACTIVATED SUCCESSFULLY!!");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    }
                    exist =false;
                }
            }
            if(exist)
                System.out.println("FOOD WITH THIS ID DOESN'T EXIST!!");
        }
    }
    public static void activeFood(int foodID) throws SQLException
    {if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else { boolean exist = true;
            for(int i=0;i<Restaurant.restaurantInuse.menu.size()&&exist;i++)
        {if(Restaurant.restaurantInuse.menu.get(i).ID==foodID)
        {exist = false;
            Restaurant.restaurantInuse.menu.get(i).active = true;
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
                    statement.executeUpdate("update food" + " set activity = true" + "where ID = " + foodID);
                    System.out.println("FOOD ACTIVATED SUCCESSFULLY!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        }
            if(exist)
                System.out.println("FOOD WITH THIS ID DOESN'T EXIST!!");
        }
    }

    }
    public static void SetDiscountFood(int foodID,int amount,int duration) throws SQLException
    {if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else { boolean exist = true;
            for(int i=0;i<Restaurant.restaurantInuse.menu.size()&&exist;i++)
            {if(Restaurant.restaurantInuse.menu.get(i).ID==foodID)
            {exist = false;
                if(Restaurant.restaurantInuse.menu.get(i).discount!=null) {
                    Restaurant.restaurantInuse.menu.get(i).discount.isTimeExpired();
                    if(Restaurant.restaurantInuse.menu.get(i).discount.active)
                    System.out.println("SORRY YOU CAN NOT SET NEW DISCOUNT FOR THIS FOOD , SOME DISCOUNT HAS BEEN ALREADY SET FOR THIS FOOD!!");
                    else if(amount>50)
                        System.out.println("SORRY THE AMOUNT OF DISCOUNT CAN NOT EXCEED 50%!!!");
                    else{java.util.Date date = new java.util.Date();
                        Timestamp current = new Timestamp(date.getTime());
                        Restaurant.restaurantInuse.menu.get(i).discount=new Discount(amount,current,duration);
                        Discount.count++;
                        Restaurant.restaurantInuse.menu.get(i).ID=Discount.count;
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
                                PreparedStatement stm = connection.prepareStatement("insert into discount (activationTime,activity,duration,percent,ID) values (?,?,?,?,?);");
                                stm.setTimestamp(1,current);
                                stm.setBoolean(2,true);
                                stm.setInt(3,duration);
                                stm.setInt(4,amount);
                                stm.setInt(5,Discount.count);
                                if(stm.executeUpdate()>0)
                                {stm = connection.prepareStatement("insert into food_discount (discountID,foodID) values (?,?);");
                                    stm.setInt(1,Discount.count);
                                    stm.setInt(2,foodID);
                                    if(stm.executeUpdate()>0)
                                        System.out.println("DISCOUNT SET SUCCESSFULLY!!");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                    else if(amount>50)
                    System.out.println("SORRY THE AMOUNT OF DISCOUNT CAN NOT EXCEED 50%!!!");
                else{java.util.Date date = new java.util.Date();
                     Timestamp current = new Timestamp(date.getTime());
                    Restaurant.restaurantInuse.menu.get(i).discount=new Discount(amount,current,duration);
                    Discount.count++;
                    Restaurant.restaurantInuse.menu.get(i).ID=Discount.count;
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
                        PreparedStatement stm = connection.prepareStatement("insert into discount (activationTime,activity,duration,percent,ID) values (?,?,?,?,?);");
                        stm.setTimestamp(1,current);
                        stm.setBoolean(2,true);
                        stm.setInt(3,duration);
                        stm.setInt(4,amount);
                        stm.setInt(5,Discount.count);
                        if(stm.executeUpdate()>0)
                        {stm = connection.prepareStatement("insert into food_discount (discountID,foodID) values (?,?);");
                            stm.setInt(1,Discount.count);
                            stm.setInt(2,foodID);
                            if(stm.executeUpdate()>0)
                                System.out.println("DISCOUNT SET SUCCESSFULLY!!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            }
            }
            if(exist)
                System.out.println("FOOD WITH THIS ID DOESN'T EXIST!!");
        }
    }
    }
    public static void selectFoodToResponse(int foodID)
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {boolean exist =true;
            for(int i=0;i<Restaurant.restaurantInuse.menu.size()&&exist;i++)
               {if(Restaurant.restaurantInuse.menu.get(i).ID==foodID)
               {exist=false;
                 Food.foodInuse = Restaurant.restaurantInuse.menu.get(i);
                 System.out.println("YOU ENTER FOOD PAGE SUCCESSFULLY!!");
               }
               }
            if(exist)
            {
                System.out.println("THERE IS NO FOOD WITH THIS ID!!!");
            }
        }
    }
    }
    public static void displayRating()
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if(Food.foodInuse==null)
                System.out.println("PLEASE SELECT THE FOOD FIRST!!");
            else {double rating =0;
                for(int i=0;i<Food.foodInuse.ratings.size();i++)
                {rating+=Food.foodInuse.ratings.get(i).stars;
                }
                if(Food.foodInuse.ratings.size()!=0)
                    rating=rating/Food.foodInuse.ratings.size();
                System.out.println("FOOD RATING : "+String.format("%.2f", rating));
            }

        }
    }
    public static void displayComments()
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Food.foodInuse==null)
            System.out.println("PLEASE SELECT THE FOOD FIRST!!");
        else if(Food.foodInuse.comments.size()==0)
            System.out.println("NO COMMENTS YET!!");
        else {
            for(int i=0;i<Food.foodInuse.comments.size();i++)
            {
             System.out.println("COMMENT ID :"+Food.foodInuse.comments.get(i).ID+"  COMMENT : "+Food.foodInuse.comments.get(i).text);
            }
        }
    }
    }
    public static void addResponse(int commentID,String message) throws SQLException
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Food.foodInuse==null)
            System.out.println("PLEASE SELECT THE FOOD FIRST!!");
        else {
            boolean exist =true;
            for (int i=0;i<Food.foodInuse.comments.size()&&exist;i++)
        {if(Food.foodInuse.comments.get(i).ID==commentID)
         {exist=false;
             Comment.commentInUse=Food.foodInuse.comments.get(i);
             if(Food.foodInuse.comments.get(i).response!=null)
                 System.out.println("YOU HAVE ALREADY RESPONSED TO THIS COMMENT!!");
             else{Food.foodInuse.comments.get(i).response=new Response(message,commentID,(Owner) User.loggedInUser);
                 Response.responseID++;
             //add in sql
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
          PreparedStatement stm = connection.prepareStatement("insert into response (commentID,ID,ownerID,responsedText) values (?,?,?,?);");
          stm.setInt(1,commentID);
          stm.setInt(2,Response.responseID);
          stm.setInt(3,User.loggedInUser.ID);
          stm.setString(4,message);
          if(stm.executeUpdate()>0)
          { Statement statement = connection.createStatement();
              if(statement.executeUpdate("update comments" + " set responseID = " + Response.responseID + "where ID = " + commentID)>0)
                  System.out.println("RESPONSE ADDED SUCCESSFULLY!!");
          }
             }
                 }
          catch (Exception e) {
                     e.printStackTrace();
                 }

             }

         }
        }
            if(Food.foodInuse.comments.size()==0)
                System.out.println("THERE ARE NO COMMENTS YET!!");
            if(exist&&Food.foodInuse.comments.size()!=0)
                System.out.println("COMMENT WITH THIS ID DOESN'T EXIST!!");
        }
    }

    }
    public static void editResponse(int commentID,String message) throws SQLException
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if(Food.foodInuse==null)
                System.out.println("PLEASE SELECT THE FOOD FIRST!!");
            else {
                boolean exist =true;
                for (int i=0;i<Food.foodInuse.comments.size()&&exist;i++)
                {if(Food.foodInuse.comments.get(i).ID==commentID)
                {exist=false;
              Comment.commentInUse=Food.foodInuse.comments.get(i);
               if(Food.foodInuse.comments.get(i).response==null)
             System.out.println("NO RESPONSE TO THIS COMMENTS YET!!");
              else{
                  Food.foodInuse.comments.get(i).response.massage=message;
               //add in sql
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
               PreparedStatement stm = connection.prepareStatement("update response set responsedText = ? where commentID = "+commentID);
                stm.setString(4,message);
                 if(stm.executeUpdate()>0)
                  System.out.println("RESPONSE EDITED SUCCESSFULLY!!");
                       }
                        }
                catch (Exception e) {
                  e.printStackTrace();
                   }
                    }
                }
                }

                if(Food.foodInuse.comments.size()==0)
                    System.out.println("THERE ARE NO COMMENTS YET!!");
                if(exist&&Food.foodInuse.comments.size()!=0)
                    System.out.println("COMMENT WITH THIS ID DOESN'T EXIST!!");
            }
        }

    }
    public static void activeOrders() throws SQLException
    {
        if (User.loggedInUser == null)
            System.out.println("LOGIN FIRST!!");
        else if (!User.loggedInUser.type.equals("Owner"))
            System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
        else {
            Owner inuse = (Owner) User.loggedInUser;
            if (inuse.ownedRestaurants.size() == 0)
                System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
            else if (Restaurant.restaurantInuse == null)
                System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
            else if (Restaurant.restaurantInuse.restaurantHistory.size() == 0)
                System.out.println("NO ORDERS YET!!");
            else {  int count =0;
                    String state = new String("");
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
                            PreparedStatement stm = connection.prepareStatement("select orderStatus from orders where ID = ?");
                            ResultSet rl;
                           for (int i=0;i<Restaurant.restaurantInuse.restaurantHistory.size();i++)
                           {stm.setInt(1,Restaurant.restaurantInuse.restaurantHistory.get(i).ID);
                               rl = stm.executeQuery();
                               if(rl.next())
                               {state = rl.getString("orderStatus");
                               if(state.equals("sending"))
                               {
                                   System.out.print("ACTIVE ORDER ID : "+Restaurant.restaurantInuse.restaurantHistory.get(i).ID+"  FOOD :");
                                   for(int k=0;k<Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.size();k++)
                                       System.out.print(Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.get(k).name+" ");
                                   System.out.print("\n");
                                   count++;

                               }
                               }
                           }
                           if(count==0)
                               System.out.println("THERE ARE NO ACTIVE ORDERS!!");
                        }
                    }
                    catch (Exception e)
                    {e.printStackTrace();}

            }
        }
    }
    public static void editOrderStatus(int orderID) //faghat tahvil be pake ???????
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else {boolean exist = true;
            for (int i=0;i<Restaurant.restaurantInuse.restaurantHistory.size()&&exist;i++)
                if(orderID==Restaurant.restaurantInuse.restaurantHistory.get(i).ID)
                    exist=false;
            if(exist)
                System.out.println("NO ORDERS WITH THIS ID!!");
            //////////////////////////////???????
            else{
                System.out.println("ORDER STATUS EDITED SUCCESSFULLY!!");
            }
        }
    }

    }
    public static void orderHistory()
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Restaurant.restaurantInuse.restaurantHistory==null)
            System.out.println("NO ORDERS YET!!");
        else { for(int i=0;i<Restaurant.restaurantInuse.restaurantHistory.size();i++) {
            System.out.print("ORDER ID : "+Restaurant.restaurantInuse.restaurantHistory.get(i).ID+" "+"ORDERED FOOD : ");
            for (int k = 0; k < Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.size(); k++)
                System.out.print(Restaurant.restaurantInuse.restaurantHistory.get(i).orderedFoods.get(k).name+" ");
            System.out.print("\n");
        }

        }
    }
    }
    public static void showLocation()
    { if (User.loggedInUser == null)
        System.out.println("LOGIN FIRST!!");
    else if (!User.loggedInUser.type.equals("Owner"))
        System.out.println("YOU ARE A CUSTOMER NOT AN OWNER!!");
    else {
        Owner inuse = (Owner) User.loggedInUser;
        if (inuse.ownedRestaurants.size() == 0)
            System.out.println("YOU HAVE NOT REGISTERED YOUR RESTAURANTS YET!!");
        else if (Restaurant.restaurantInuse == null)
            System.out.println("PLEASE SELECT YOUR RESTAURANT FIRST!!");
        else if(Restaurant.restaurantInuse.location==null)
            System.out.println("LOCATION HAS NOT BEEN DETERMINED YET!!");
        else System.out.println("LOCATION : NODE "+Restaurant.restaurantInuse.location.ID);
    }
    }






}