package sample;

public class User {
    public String userName;
    protected String password;
    public User[] friends; //unused element that could potentially hold a list of other users
    public int SIZE = 50;

    public User() {
        userName = null;
        password = null;
        friends = new User[SIZE];
    }

    public User(User in_user) {
        userName = in_user.userName;
        password = in_user.password;
        friends = in_user.friends;
    }

    public User(String in_username, String in_password)
    {
        userName = in_username;
        password = in_password;
        friends = new User[SIZE];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String in_name) {
        userName = in_name;
    }

    public void setPassword(String in_password) {
        password = in_password;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String in_name, String in_pass)
    {
        userName = in_name;
        password = in_pass;
    }

}
