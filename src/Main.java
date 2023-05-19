import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    static Scanner input = new Scanner (System.in);
    public static void main(String[] args) throws SQLException
    {
        SQL.connect();
        Graph.readGraph();
        User.getOnlyUserFromDtaBase();
        Response.getAllResponsesFromDataBase();
        Comment.getAllCommentsFromDataBase();
        Discount.getAllDiscountsFromDataBase();
        Rate.getAllRatingsFromDataBase();
        Food.getAllFoodsFromDataBaseAndRatesAndCommentsAndDiscount();
        Order.getAllOrdersFromDataBase();
        Restaurant.getAllRestaurantsFromDataBaseAndTypesAndMenuAndHistory();
        User.getAllUsersFromDataBaseAndHistoryAndOwnedRestaurants();
        while (true)
        {
            String to_be_checked = input.nextLine();
            if(to_be_checked.equals("EXIT"))
                break;
            InputManager.funcCaller(to_be_checked);
        }
    }
}