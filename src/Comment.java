import java.util.ArrayList;
public class Comment
{
    int ID;
    static Comment commentInUse;
    static ArrayList<Comment> allComments = new ArrayList<>();
    String text;
    Normal user;
    Response response;
}
