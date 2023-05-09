import java.util.ArrayList;
public class Food
{
    String name;
    int ID;
    Double price;
    String foodType;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<Rate> ratings = new ArrayList<>();
    Boolean Active;
    static Food foodInuse;
    Discount discount;
}
