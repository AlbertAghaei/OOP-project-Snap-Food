import java.util.ArrayList;
abstract public class User
{
    int ID;
    String username;
    String password;
    String type;///owner or normal
    static User loggedInUser;
    static ArrayList<User> allUsers = new ArrayList<>();
    String securityQuestion;///enums might be needed
    String securityAnswer;
    User(String username, String password, String type)
    {
        this.username = username;
        this.password = password;
        this.type = type;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
    User(){}
    public static User findUserByID(int ID)
    {
        for(int i=0; i<allUsers.size(); i++)
            if(allUsers.get(i).ID==ID)
                return allUsers.get(i);
        return null;
    }
}
