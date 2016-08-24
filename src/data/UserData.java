package data;

import java.util.LinkedList;

public class UserData {
    protected final String USER_NAME;
    protected final int USER_LEVEL;
    protected final int USER_ID;
    private String userLevel;
    private String user_status;
    protected long threadId = -1;

    protected LinkedList<String> round2Answers;
    protected LinkedList<Integer> round2Points; // last element is the total point

    protected int round4Points = 0;

    protected LinkedList<Integer> round5AnswerIndices;
    protected LinkedList<String> round5Answers;
    protected int[] round5PointState;
    protected int round5Points = 0;

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

    public int getUSER_LEVEL() {
        return USER_LEVEL;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public String getUser_status() {
        return user_status;
    }

    public long getThreadId() {
        return threadId;
    }

    public LinkedList<String> getBasicInfo() {
        LinkedList<String> result = new LinkedList<>();
        result.add(USER_NAME);
        result.add(userLevel);
        return result;
    }

    public void setThreadId(long id) {
        threadId = id;
        System.out.println("User with ID " + USER_ID + " threadId is " + threadId);
    }

    public void setRound4Points(int point) {
        round4Points += point;
    }

    public int getRound4Points() {
        return round4Points;
    }

    public void setRound5Points(int point) {
        round5Points += point;
    }

    public void setRound5PointState(int index, int state) {
        if (round5PointState == null)
            round5PointState = new int[round5AnswerIndices.size()];

        round5PointState[index] = state;
    }

    public int getRound5Points() {
        return round5Points;
    }
}
