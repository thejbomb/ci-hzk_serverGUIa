package data;

import java.io.Serializable;

public class UserData implements Serializable {
    private static final long serialVersionUID = 3L;
    private final String USER_NAME;
    private final int USER_LEVEL;
    private final String USER_ID;
    private String userLevel;
    private String user_status;
    private boolean userStatus = false;
    private int threadID = -1;

    public UserData(String name, int level, int ID, boolean status) {
        USER_NAME = name;
        USER_LEVEL = level;
        USER_ID = Integer.toString(ID);
        setUserLevel(level);
        setUserStatus(status);
    }

    public String getUser_status() {
        return user_status;
    }

    public int getUSER_LEVEL() {
        return USER_LEVEL;
    }

    public int getUSER_ID() {
        return Integer.parseInt(USER_ID);
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public boolean getUserStatus() {
        return userStatus;
    }

    public void setThreadID(int theadID) {
        this.threadID = theadID;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setUserLevel(int level) {
        if (level == 1)
            userLevel = "初级 Beginner";
        else if (level == 2)
            userLevel = "中级 Intermediate";
        else
            userLevel = "高级 Advance";
    }

    public void setUserStatus(boolean status) {
        userStatus = status;
        if (status)
            user_status = "Connected";
        else
            user_status = "Offline";
    }

    public int compareTo(UserData data) {
        if (data.getUSER_ID() == Integer.parseInt(USER_ID))
            return 0;
        else
            return -1;
    }
}
