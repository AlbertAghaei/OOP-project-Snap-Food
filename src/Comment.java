import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Comment
{
    int ID;
    static Comment commentInUse;
    static ArrayList<Comment> allComments = new ArrayList<>();
    String text;
    Normal user;
    Response response;
    Comment(int ID,String text,Normal user,Response response)
    {
        this.ID=ID;
        this.text=text;
        this.user=user;
        this.response = response;
    }
    public static Comment findCommentByID(int ID)
    {
        for(int i=0; i<allComments.size(); i++)
            if(allComments.get(i).ID==ID)
                return allComments.get(i);
        return null;
    }
    public static void getAllCommentsFromDataBase()throws SQLException
    {
            try
            {
                String query = "SELECT * FROM comments;";
                Statement statement = SQL.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    int id = resultSet.getInt("ID");
                    String text = resultSet.getString("commentedText");
                    int responseID = resultSet.getInt("responseID");
                    int userID = resultSet.getInt("userID");/////////////get users from database and responses
                    allComments.add(new Comment(id,text,(Normal)User.findUserByID(userID),Response.findResponseByID(responseID)));
                }
                resultSet.close();
                statement.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
    }
}
