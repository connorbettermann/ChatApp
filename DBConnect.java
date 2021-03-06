package sample;

/*
This class controls the MySQL database connection on the local machine. It utilizes the
mysql-connector-java-5.1 external library.
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DBConnect()
    {
        try
        {
            //connect to th database
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false","root","l(#mid)");
            st = con.createStatement();
        }catch(Exception ex)
        {
            System.out.println(ex);
        }


    }
    //method to retrieve all data from the database
    public void getData() {
        try
        {
            String query = "SELECT * from accounts";
            rs = st.executeQuery(query);
            System.out.println("Record from database");
            while(rs.next())
            {
                String name = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("Username: " + name + "	" + "Password: " + password);
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    //method to add a nw user to the database
    public void addUser(String in_user, String in_password)
    {
        try
        {
            int i;
            String query = "INSERT INTO accounts (username, password) VALUES ('" + in_user + "', '" + in_password + "')";
            i = st.executeUpdate(query);
            System.out.println(i + " rows affected in table");
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    //method to retrieve a user from the database based on username
    public void getUser(String in_user)
    {
        try
        {
            String query = "SELECT * FROM accounts WHERE username='" + in_user + "'";
            rs = st.executeQuery(query);
            if(rs.next())
            {
                String username = rs.getString("username");
                System.out.println("Username: " + username);
            }
            else
            {
                System.out.println("No matching account found!");
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    //method to retrieve user from database based on username and password. returns a User object
    public User loginUser(String in_user, String in_password)
    {
        try
        {
            String query = "SELECT * FROM accounts WHERE username='" + in_user + "'AND password='" + in_password + "'";
            rs = st.executeQuery(query);
            if(rs.next())
            {
                String username = rs.getString("username");
                String password = rs.getString("password");
                User temp = new User(username, password);
                return temp;
            }
            else
            {
                System.out.println("No matching account found");
                return null;
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }

    //method to remove a user from the database
    public void removeUser(User in_user)
    {
        try
        {
            String username = in_user.getUserName();
            String password = in_user.getPassword();
            String query = "DELETE FROM accounts WHERE username='" + username + "' AND password='" + password + "'";

            int i;
            i = st.executeUpdate(query);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
