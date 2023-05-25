import java.sql.*;
import java.time.LocalTime;

public class Discount
{    static int count =0;
    int ID;
    int percent;
    java.sql.Timestamp setTime;//??
    int duration; //minute
    boolean active;
    Discount(int percent, Timestamp time,int duration)
    {
        this.percent = percent;
        this.setTime = time;
        this.duration = duration;
        this.active = true;
        ///read from database the last ID and give the next one to this comment
        ///write this one in database
    }
    public void isTimeExpired() throws SQLException
    {java.util.Date date = new java.util.Date();
        Timestamp timestamp1 = this.setTime;
        Timestamp timestamp2 = new Timestamp(date.getTime());
        long milliseconds = timestamp2.getTime() - timestamp1.getTime();
        int seconds = (int) milliseconds / 1000;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = (seconds % 3600) % 60;
        minutes+=hours*60+seconds/60;
        if(this.duration-minutes>0)
            this.active=true;
        else this.active=false;
        //update in sql
        String url;
        String username;
        String password;
        Connection connection;
        url = "jdbc:mysql://localhost:3306/oop-project-snapfood";
        username = "root";
        password = "W@2915djkq#";
        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("update discount" + " set activity = " +this.active+ "where ID = " + this.ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
