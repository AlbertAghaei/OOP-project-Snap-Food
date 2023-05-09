import java.util.ArrayList;
abstract public class User
{
    int ID;
    String username;
    String password;
    String type;///owner or normal
    static User loggedInUser;
    static ArrayList<User> allUsers = new ArrayList<>();
}
