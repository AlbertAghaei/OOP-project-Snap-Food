import java.util.ArrayList;
public class Normal extends User
{
    Double charge;
    Node location;
    ArrayList<Order> userHistory = new ArrayList<>();
    Normal(String username, String password, String type, Node location)
    {
        super(username, password, type);
        this.location = location;
    }
}
