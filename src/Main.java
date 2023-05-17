import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    static Scanner input = new Scanner (System.in);
    public static void main(String[] args) throws SQLException
    {
        SQL.connect();
        User.getAllUsersFromDataBase();
        Graph.readGraph();
        while (true)
        {
            String to_be_checked = input.nextLine();
            if(to_be_checked.equals("EXIT"))
                break;
            InputManager.funcCaller(to_be_checked);
        }
    }
}