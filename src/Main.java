import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        SQL.connect();
        User.getAllUsersFromDataBase();
        Graph.showPath(1,1000);
        Scanner input = new Scanner (System.in);
        while (true)
        {
            String to_be_checked = input.nextLine();
            if(to_be_checked.equals("EXIT"))
                break;
            InputManager.funcCaller(to_be_checked);
        }
    }
}