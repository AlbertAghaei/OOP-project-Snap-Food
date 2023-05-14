import java.util.ArrayList;
public class Food
{
    static ArrayList<Food> foods = new ArrayList<>();
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
    public static Food findFoodByID(int ID)
    {
        for(int i=0; i<foods.size(); i++)
            if(foods.get(i).ID==ID)
                return foods.get(i);
        return null;
    }
}
