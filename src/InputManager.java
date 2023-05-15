import java.sql.SQLException;

public class InputManager
{
   public static void funcCaller(String input) throws SQLException
   {
       String[] splitted = input.split(" ");
       if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("ADD ADMIN"))///////////////
         Entrance.registerOwner(splitted[2],splitted[3]);
       else //////////////////
           System.out.println("invalid command");
   }
}
