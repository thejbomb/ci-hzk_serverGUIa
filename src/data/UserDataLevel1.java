package data;

import data.round2.Level1DataStructure;

import java.util.LinkedList;

public class UserDataLevel1 extends UserData {


    public UserDataLevel1(String name, int level, int ID) {
        super(name, level, ID);
    }

    public void setThreadId(long id) {
        super.setThreadId(id);
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

    public LinkedList<String> getRound2Answers() {
        return round2Answers;
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
