package data;

import data.round2.Level3DataStructure;
import javafx.scene.shape.Polyline;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by quang on 08/06/16.
 */
public class UserDataLevel3 extends UserData {

    private long seed;

    private int[] round5PointState;

    private LinkedList<LinkedList<Polyline>> round2Answers;

    private int round2Points = 0;

    public UserDataLevel3(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
    }

    public LinkedList<LinkedList<Polyline>> getRound2Answers() {
        return round2Answers;
    }

    public LinkedList<Integer> getRound5AnswerIndices() {
        return round5AnswerIndices;
    }

    public LinkedList<String> getRound5Answers() {
        return round5Answers;
    }

    public void setRound2Answers(LinkedList<String> answers) {
        if (round2Answers == null)
            round2Answers = new LinkedList<>();
        answers.removeFirst();
        while (true) {
            LinkedList<Polyline> llpl = new LinkedList<>();
            String s = answers.removeFirst();
            int index1 = s.indexOf("points=", 0);
            int index2 = s.indexOf("]", index1);
            while (index1 != -1) {
                Polyline pl = new Polyline();
                String s2 = s.substring(index1 + 8, index2);
                List<String> list = Arrays.asList(s2.split(","));
                LinkedList<Double> dList = new LinkedList<>();
                for (String string : list)
                    dList.add(Double.parseDouble(string));
                pl.getPoints().addAll(dList);
                llpl.add(pl);
                index1 = s.indexOf("points=", index2);
                index2 = s.indexOf("]", index1);
            }
            round2Answers.add(llpl);
            try {
                answers.removeFirst();
            } catch (NoSuchElementException ex) {
                break;
            }

        }
        System.out.println("User on thread " + threadId + " with ID " + USER_ID + " answers for round 2 are " + round2Answers);
    }

    public void setPointRound2(int points) {
        round2Points = points;

    }

    public int getROund2Points() {
        return round2Points;
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

    public void setSeed(LinkedList<String> data) {
        String d = data.getFirst();
        seed = Long.parseLong(d);
    }

    public long getSeed() {
        return seed;
    }

    public int[] getRound5PointState() {
        if (round5PointState == null)
            round5PointState = new int[round5AnswerIndices.size()];
        return round5PointState;
    }

}
