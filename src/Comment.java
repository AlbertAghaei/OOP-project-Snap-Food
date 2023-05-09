import java.util.ArrayList;
public class Comment
{
    int ID;
    static Comment commentInUse;
    static ArrayList<Comment> allComments = new ArrayList<>();
    String text;
    Normal user;
    Response response;
    Comment(String text,Normal user)
    {
        this.text=text;
        this.user=user;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
}
