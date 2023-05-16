public class Discount
{
    int ID;
    int percent;
    String time;//??
    boolean active;
    Discount(int percent, String time)
    {
        this.percent = percent;
        this.time = time;
        this.active = true;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
}
