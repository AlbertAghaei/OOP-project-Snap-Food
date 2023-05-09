public class Rate
{
    int ID;
    Rate rateInUse;
    int stars;///1-5
    Normal user;
    Rate(int stars, Normal user)
    {
        this.stars = stars;
        this.user = user;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
}
