package data;

public class UserData {
    protected final String USER_NAME;
    protected final int USER_LEVEL;
    protected final int USER_ID;
    private String userLevel;
    private String user_status;

    public UserData(String name, int level, int ID) {
        USER_NAME = name;
        USER_LEVEL = level;
        USER_ID = ID;
        setUserStatus(false);
    }

    public void setUserLevel() {
        if (USER_LEVEL == 1)
            userLevel = "初级 Beginner";
        else if (USER_LEVEL == 2)
            userLevel = "中级 Intermediate";
        else
            userLevel = "高级 Advance";
    }

    public void setUserStatus(boolean status) {
        if (status)
            user_status = "Connected";
        else
            user_status = "Offline";
    }

    public int compareTo(UserData data) {
        if (data.USER_ID == USER_ID)
            return 0;
        else
            return -1;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public int getUSER_LEVEL(){
        return USER_LEVEL;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public String getUser_status() {
        return user_status;
    }

}
