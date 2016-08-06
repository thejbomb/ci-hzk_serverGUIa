package data;

import data.round2.Round2Level2Data;

import java.util.LinkedList;

/**
 * Created by quang on 08/06/16.
 */
public class UserDataLevel2 extends UserData {
    private LinkedList<String> round2Answers;
    private LinkedList<Integer> round2Points; // last element is the total point

    public UserDataLevel2(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
    }

    public LinkedList<String> getRound2Answers() {
        return round2Answers;
    }

    public void setRound2Answers(LinkedList<String> answers) {
        if (round2Answers == null)
            round2Answers = new LinkedList<>();

        int i = 2;
        while (answers.size() != 0) {
            String answer = "";
            answers.removeFirst();
            while (answers.getFirst().compareTo("ANS" + i) != 0) {
                answer += answers.getFirst();
                answers.removeFirst();
                if (answers.size() == 0)
                    break;
            }
            round2Answers.add(answer.replaceAll("\\s", ""));
            i++;
        }
        System.out.println("User on thread " + threadId + "with ID " + USER_ID + " answers are " + round2Answers);
    }

    public void setPointRound2(int point, int index) {
        if (round2Points == null) {
            round2Points = new LinkedList<>();
            for (int i = 0; i < Round2Level2Data.NUM_OF_QUESTIONS; i++)
                round2Points.add(0);
        }

        round2Points.set(index, point);

        int total = 0;

        for (int i = 0; i < Round2Level2Data.NUM_OF_QUESTIONS; i++)
            total += round2Points.get(i);
        if (round2Points.size() == Round2Level2Data.NUM_OF_QUESTIONS)
            round2Points.add(total);
        else {
            round2Points.removeLast();
            round2Points.add(total);
        }

    }

    public LinkedList<Integer> getPoints() {
        return round2Points;
    }
}
