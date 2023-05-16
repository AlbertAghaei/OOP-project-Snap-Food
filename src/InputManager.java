import java.sql.SQLException;

public class InputManager
{
   public static void funcCaller(String input) throws SQLException
   {
       String[] splitted = input.split(" ");
       if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("ADD ADMIN"))///////////////
         Entrance.registerOwner(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("ADD USER"))///////////////
           Entrance.registerNormal(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("LOGIN ADMIN"))///////////////
           Entrance.loginOwner(splitted[2],splitted[3]);
       else if(splitted.length==4 && (splitted[0]+" "+splitted[1]).equals("LOGIN USER"))///////////////
           Entrance.loginNormal(splitted[2],splitted[3]);
       else if(splitted.length==1 && splitted[0].equals("LOGOUT"))///////////////
           Entrance.logout();
       else //////////////////
           System.out.println("invalid command");
   }
}
