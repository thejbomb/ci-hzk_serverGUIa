package data;

import data.round2.Level1DataStructure;
import javafx.scene.shape.Polyline;
import main.Main;
import sun.awt.image.ImageWatched;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDataLevel1 extends UserData {
    private LinkedList<LinkedList<Polyline>>[] round2Answers;
    private LinkedList<String>[] round1Answers;

    public UserDataLevel1(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
    }

    public void setPointRound1(int point, int index) {
        if (round1Points == null) {
            round1Points = new LinkedList<>();
            for (int i = 0; i < data.round1.Level1DataStructure.NUM_OF_QUESTIONS; i++)
                round1Points.add(0);
        }

        round1Points.set(index, point);

        int total = 0;

        for (int i = 0; i < data.round1.Level1DataStructure.NUM_OF_QUESTIONS; i++)
            total += round1Points.get(i);
        if (round1Points.size() == data.round1.Level1DataStructure.NUM_OF_QUESTIONS)
            round1Points.add(total);
        else {
            round1Points.removeLast();
            round1Points.add(total);
        }

        round1TotalPoints = round1Points.getLast();
    }

    public void setRound1Answers(LinkedList<String> answers) {
            round1Answers = new LinkedList[answers.size() / 2];

            for (int k = 0; k < round1Answers.length; k++) {
                round1Answers[k] = new LinkedList<>();
                String answer = "";
                answers.removeFirst();

                answer += answers.getFirst();

                answers.removeFirst();

                round1Answers[k].add(answer);
            }


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

                int index1 = sString.indexOf("points=", 0);
                int index2 = sString.indexOf("]", index1);
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

    public void setRound3Answers(LinkedList<String> answers) {
        round3Answers = answers;
        //System.out.println("User on thread " + threadId + " with ID " + USER_ID + " answers for round 3 are " + round3Answers);
        System.out.println("User " + USER_ID + " answers for round 3 are " + round3Answers);

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

        System.out.println("User" + USER_ID + " answers for round 5 are " + round5Answers);
    }

    public LinkedList<String>[] getRound1Answers() {return round1Answers;}

    public LinkedList<LinkedList<Polyline>>[] getRound2Answers() {
        return round2Answers;
    }

    public void setPointRound3(int point, int index) {
        if (round3Points == null) {
            round3Points = new LinkedList<>();
            for (int i = 0; i < Level1DataStructure.NUM_OF_QUESTIONS; i++)
                round3Points.add(0);
        }

        round3Points.set(index, point);

        int total = 0;

        for (int i = 0; i < Level1DataStructure.NUM_OF_QUESTIONS; i++)
            total += round3Points.get(i);
        if (round3Points.size() == Level1DataStructure.NUM_OF_QUESTIONS)
            round3Points.add(total);
        else {
            round3Points.removeLast();
            round3Points.add(total);
        }

    }

    public LinkedList<String> getRound3Answers() {
        return round3Answers;
    }

    public LinkedList<Integer> getRound5AnswerIndices() {
        return round5AnswerIndices;
    }

    public LinkedList<String> getRound5Answers() {
        return round5Answers;
    }

    public void setPointRound2(int point, int index) {
        if (round2Points == null) {
            round2Points = new LinkedList<>();
            for (int i = 0; i < Level1DataStructure.NUM_OF_QUESTIONS; i++)
                round2Points.add(0);
        }

        round2Points.set(index, point);

        int total = 0;

        for (int i = 0; i < Level1DataStructure.NUM_OF_QUESTIONS; i++)
            total += round2Points.get(i);
        if (round2Points.size() == Level1DataStructure.NUM_OF_QUESTIONS)
            round2Points.add(total);
        else {
            round2Points.removeLast();
            round2Points.add(total);
        }

        round2TotalPoints = round2Points.getLast();

    }

    public LinkedList<Integer> getRound1Points() {
        return round1Points;
    }

    public LinkedList<Integer> getRound2Points() {
        return round2Points;
    }

    public LinkedList<Integer> getRound3Points() {
        return round3Points;
    }

    public int[] getRound5PointState() {
        if (round5PointState == null)
            round5PointState = new int[round5AnswerIndices.size()];
        return round5PointState;
    }

}
