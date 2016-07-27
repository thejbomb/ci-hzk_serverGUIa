package data;

public class UserDataCollection extends DataCollection {

    public void reset(Object _data) {
        Node temp = head;
        UserData data = (UserData) _data;
        if (data == null) { // if this method received a null parameter
            selected = head;
        } else { // if we received a student
            while (temp != null) { // while there are still students
                // when we found a match
                if (((UserData) temp.data).compareTo(data) == 0) {
                    userSelected = temp; // the user selected this node
                    selected = temp; // the current selected node in the
                    // collection
                }
                temp = temp.next; // gets the next node
            }
        }
    }
}
