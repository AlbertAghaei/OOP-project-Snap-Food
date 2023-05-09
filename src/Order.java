import java.util.ArrayList;
public class Order
{
    int ID;
    ArrayList<Food> orderedFoods = new ArrayList<>();
    Double totalPrice;
    String status;///sent or in the way
    String timeLeft;
    String timeDelivered;
}
