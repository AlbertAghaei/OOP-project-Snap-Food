import java.util.ArrayList;
public class Order
{
    int ID;
    ArrayList<Food> orderedFoods = new ArrayList<>();
    Double totalPrice;
    String status;///sent or in the way
    String timeLeft;
    String timeDelivered;
    Order(ArrayList<Food> cart,Double totalPrice)
    {
        this.orderedFoods = cart;
        this.totalPrice = totalPrice;
        this.status = "to_be_delivered";
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
}
