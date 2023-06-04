import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Response
{
    static ArrayList<Response> allResponses = new ArrayList<>();
    int ID;
    String massage;
    int commentID;
    Owner owner;
    Response(int ID, String massage, int commentID, Owner user)
    {
        this.ID = ID;
        this.massage = massage;
        this.commentID = commentID;
        this.owner = user;
    }
    public static Response findResponseByID(int ID)
    {
        for(int i=0; i<allResponses.size(); i++)
          if(allResponses.get(i).ID==ID)
              return allResponses.get(i);
        return null;
    }
    public static void getAllResponsesFromDataBase()
    {
        try {
            String query = "SELECT * FROM response;";
            Statement statement = SQL.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String text = resultSet.getString("responsedText");
                int ownerID = resultSet.getInt("ownerID");
                int commentID = resultSet.getInt("commentID");
                allResponses.add(new Response(id, text, commentID, (Owner) User.findUserByID(ownerID)));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
