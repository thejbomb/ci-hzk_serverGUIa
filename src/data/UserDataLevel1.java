package data;

import data.round2.Round2Level1Data;

import java.util.LinkedList;

public class UserDataLevel1 extends UserData {
    private long threadId = -1;
    private LinkedList<String> round2Answers;
    private LinkedList<Integer> round2Points;

    public UserDataLevel1(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        threadId = id;
        System.out.println("User with ID " + USER_ID + " threadId is " + threadId);
    }

    public long getThreadId() {

        return threadId;
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

    public LinkedList<String> getRound2Answers() {
        return round2Answers;
    }

    public void setPointRound2(int point, int index) {
        if (round2Points == null) {
            round2Points = new LinkedList<>();
            for (int i = 0; i < Round2Level1Data.NUM_OF_QUESTIONS; i++)
                round2Points.add(0);
        }

        round2Points.set(index, point);

        int total = 0;

        for (int i = 0; i < Round2Level1Data.NUM_OF_QUESTIONS; i++)
                total += round2Points.get(i);
        if (round2Points.size() == Round2Level1Data.NUM_OF_QUESTIONS)
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
