import java.util.ArrayList;
public class Normal extends User
{
    Double charge;
    Node location;
    ArrayList<Food> cart = new ArrayList<>();
    ArrayList<Order> userHistory = new ArrayList<>();
    Normal(int ID,String username, String password, String type, Node location,Double charge)
    {
        super(ID,username, password, type);
        this.location = location;
        this.charge = charge;
    }
}
