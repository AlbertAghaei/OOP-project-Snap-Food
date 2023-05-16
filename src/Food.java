import java.util.ArrayList;
public class Food
{
    String name;
    int ID;
    Double price;
    String foodType;
    ArrayList<Comment> comments = new ArrayList<>();
    ArrayList<Rate> ratings = new ArrayList<>();
    Boolean active;
    static Food foodInuse;
    Discount discount;
    Food(String name,Double price,String type)
    {
        this.name = name;
        this.price = price;
        this.foodType = type;
        this.active = true;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
}
