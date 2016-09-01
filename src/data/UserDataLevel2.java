package data;

import data.round2.Level2DataStructure;
import javafx.scene.shape.Polyline;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by quang on 08/06/16.
 */
public class UserDataLevel2 extends UserData {
    private LinkedList<LinkedList<Polyline>>[] round2Answers;

    public UserDataLevel2(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
    }

    public LinkedList<LinkedList<Polyline>>[] getRound2Answers() {
        return round2Answers;
    }

    public LinkedList<Integer> getRound5AnswerIndices() {
        return round5AnswerIndices;
    }

    public LinkedList<String> getRound5Answers() {
        return round5Answers;
    }

    public void setRound2Answers(LinkedList<String> answers) {
        round2Answers = new LinkedList[answers.size() / 2];
        for (int i = 0; i < round2Answers.length; i++)
            round2Answers[i] = new LinkedList<>();

        answers.removeFirst();
        int index = 0;
        while (true) {

            String s = answers.removeFirst();
            int index0;
            int index00 = 0;
            index0 = s.indexOf("[Polyline", index00);
            index00 = s.indexOf("[Polyline", index0 + 1);
            while (index0 != -1) {
                LinkedList<Polyline> llpl = new LinkedList<>();
                String sString;
                if (index00 != -1)
                    sString = s.substring(index0, index00);
                else {
                    sString = s.substring(index0);
                    index00 = index0 + 1;
                }
                int index1;
                int index2 = 0;
                index1 = sString.indexOf("points=", index2);
                index2 = sString.indexOf("]", index1);
                while (index1 != -1) {
                    Polyline pl = new Polyline();
                    String s2 = sString.substring(index1 + 8, index2);
                    List<String> list = Arrays.asList(s2.split(","));
                    LinkedList<Double> dList = new LinkedList<>();
                    for (String string : list)
                        dList.add(Double.parseDouble(string));
                    pl.getPoints().addAll(dList);
                    llpl.add(pl);
                    index1 = sString.indexOf("points=", index2);
                    index2 = sString.indexOf("]", index1);

                }
                index0 = s.indexOf("[Polyline", index00);
                index00 = s.indexOf("[Polyline", index0 + 1);
                round2Answers[index].add(llpl);
            }
            try {
                answers.removeFirst();
                index++;
            } catch (NoSuchElementException ex) {
                break;
            }
        }
        System.out.println("User on thread " + threadId + " with ID " + USER_ID + " answers for round 2 are " + round2Answers);
    }


    public void setRound5Answers(LinkedList<String> answers) {
        if (round5AnswerIndices == null)
            round5AnswerIndices = new LinkedList<>();
        if (round5Answers == null)
            round5Answers = new LinkedList<>();
        answers.removeFirst();
        while (answers.size() != 1) {
            if (answers.getFirst().compareTo("CANS") != 0) {
                round5AnswerIndices.add(Integer.parseInt(answers.getFirst()));
                answers.removeFirst();
            }
            if (answers.getFirst().compareTo("CANS") == 0) {
                round5Answers.add(answers.get(1));
                answers.remove(1);
            }
        }

        System.out.println("User on thread " + threadId + " with ID " + USER_ID + " answers for round 5 are " + round5Answers);
    }

    public void setPointRound2(int point, int index) {
        if (round2Points == null) {
            round2Points = new LinkedList<>();
            for (int i = 0; i < Level2DataStructure.NUM_OF_QUESTIONS; i++)
                round2Points.add(0);
        }

        round2Points.set(index, point);

        int total = 0;

        for (int i = 0; i < Level2DataStructure.NUM_OF_QUESTIONS; i++)
            total += round2Points.get(i);
        if (round2Points.size() == Level2DataStructure.NUM_OF_QUESTIONS)
            round2Points.add(total);
        else {
            round2Points.removeLast();
            round2Points.add(total);
        }

    }

    public LinkedList<Integer> getRound2Points() {
        return round2Points;
    }

    public int[] getRound5PointState() {
        if (round5PointState == null)
            round5PointState = new int[round5AnswerIndices.size()];
        return round5PointState;
    }

}
