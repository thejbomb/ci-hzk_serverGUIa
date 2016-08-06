package data;

import java.util.LinkedList;

/**
 * Created by quang on 08/06/16.
 */
public class UserDataLevel3 extends UserData{

    private LinkedList<String> round2Answers;
    private LinkedList<Integer> round2Points; // last element is the total point

    public UserDataLevel3(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
    }
}
