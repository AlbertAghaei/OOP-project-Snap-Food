public class Response
{
    String massage;
    int commentID;
    Owner owner;
    Response(String massage, int commentID, Owner user)
    {
        this.massage = massage;
        this.commentID = commentID;
        this.owner = user;
        ///write this one in database for the commentInUse
    }
}
