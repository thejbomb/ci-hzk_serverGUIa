package data;

import java.io.Serializable;

public class UserData implements Serializable {
    private static final long serialVersionUID = 3L;
    private final String USER_ID;
    private final String USER_NAME;
    private final String USER_LEVEL;
    private final int _USER_LEVEL;
    private String user_status;
    private boolean userStatus = false;
    private int threadID = -1;

    public UserData(String name, int level, int ID, boolean status) {
        USER_NAME = name;
        _USER_LEVEL = level;
        if (level == 1)
            USER_LEVEL = "初级 Beginner";
        else if (level == 2)
            USER_LEVEL = "中级 Intermediate";
        else
            USER_LEVEL = "高级 Advance";

        USER_ID = Integer.toString(ID);
        setUserStatus(status);
    }

    public String getUser_status() {
        return user_status;
    }

    public int get_USER_LEVEL() {
        return _USER_LEVEL;
    }

    public int getUSER_ID() {
        return Integer.parseInt(USER_ID);
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUSER_LEVEL() {
        return USER_LEVEL;
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
