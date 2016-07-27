package data;

import java.awt.*;

public class DataCollection implements DataCollectionInterface {
    protected Node head; // the first node in the collection
    protected Node selected; // the current selected node in the collection
    protected Node userSelected; // the last node that was selected by the user

    @Override
    public void reset() {
        selected = head;
    }

    @Override
    public void reset(Object data) {

    }

    @Override
    public void add(Object data) {
        Node temp = new Node(); // creates a new node to hold the student
        temp.data = data; // puts the student into the node
        if (head == null) // if the list is empty
            head = temp; // temp is the first student

        else { // if the list is not empty
            Node current = head.next;// a current node to go through the collection
            Node previous = head; // a previous node to follow the current node
            while (current != null) { // go through the collection once
                previous = current; // sets previous to be the current node
                current = current.next; // gets the next node
            }
            previous.next = temp; // sets the last node to points to temp
        }
    }

    @Override
    public void paint(Graphics g) {

    }

    @Override
    public boolean hasNext() {
        return selected != null;
    }

    @Override
    public Object next() {
        Object result = selected.data; // result is the current object
        selected = selected.next; // go to the next node

        return result; // returns the object
    }

    @Override
    public void remove() {
        Node current = head; // a node to go through the collection
        Node previous = null; // another node to follow current
        while (userSelected != null) { // only remove if the user actually
            // selected a student
            while (current != userSelected) {// until we found a match
                previous = current; // sets previous to be current
                current = current.next; // gets the next node
            }

            current = current.next; // gets the node after the userSelected node

            if (head == userSelected) // if head is the node that the user selected
                head = head.next; // the new head will be the node after head
            else
                previous.next = current; // sets previous to point to the
            // current node to bypass the node in between the previous node
            // and its next node

            userSelected = null; // the userSelected node is gone
        }
    }

    protected class Node {
        protected Object data; // The student information
        protected Node next; // the reference to the next node

        public Node() {
            data = null;
            next = null;
        }
    }
}
