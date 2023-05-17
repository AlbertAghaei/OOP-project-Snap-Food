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

}
