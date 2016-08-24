package data.round4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by quang on 08/23/16.
 */
public class Level3DataStructure extends DataStructure{

    private ArrayList<String> QUESTIONS3;
    private ArrayList<String> CHOICES3;

    public final String QUESTION_INSTRUCTION3_ZH = "";
    public final String QUESTION_INSTRUCTION3_EN = "";

    public void init() {
        seed = System.nanoTime();
        if (questionType == null)
            questionType = new LinkedList<>();
        for (int i = 0; i < QUESTIONS1.size(); i++)
            questionType.add(0); // type 0 = fill in the blank
        for (int i = 0; i < QUESTIONS2.size(); i++)
            questionType.add(1); // type 1 = read aloud
        for (int i = 0; i < QUESTIONS3.size(); i++)
            questionType.add(2); // type 3 = chinese idiom
        Collections.shuffle(questionType, new Random(seed));
        correctAnswers = parseCorrectAnswers(QUESTIONS1, CHOICES3);
        choices = parseChoices(CHOICES, CHOICES3);
        questions = combineQuestionLists(QUESTIONS1, QUESTIONS2, QUESTIONS3);

        System.out.println("Round 4 Q: " + questions);
        System.out.println("Round 4 C: " + choices);
        System.out.println("Round 4 A: " + correctAnswers);
        System.out.println("Seed: " + seed);
    }

    protected LinkedList<String> parseCorrectAnswers(ArrayList<String> questions1, ArrayList<String> choices3) {
        LinkedList<String> result = super.parseCorrectAnswers(questions1);
        ArrayList<String> newChoices = new ArrayList<>();
        for (String s : choices3) {
            s = s.replace('，',',');
            s = s.replace('（','(');
            s = s.replace('）',')');
            s = s.replaceAll(" ", "");
            int index1 = s.indexOf('(');
            int index2 = s.indexOf(')');

            String answer = s.substring(index1 + 1, index2);
            String choices = "";
            if (index1 <= 0) {
                choices += answer + s.substring(index2+1);
            } else if (index2 >= s.length()) {
                choices += s.substring(0, index1) + answer;
            } else {
                choices += s.substring(0, index1) + answer + s.substring(index2+1);
            }
            result.add(answer);
            newChoices.add(choices);
        }
        Collections.shuffle(result, new Random(seed));
        CHOICES3 = newChoices;
        return result;
    }

    protected LinkedList<String> combineQuestionLists(ArrayList<String> questions1, ArrayList<String> questions2, ArrayList<String> questions3) {
        LinkedList<String> result = super.combineQuestionLists(questions1,questions2);
        result.addAll(questions3);

        Collections.shuffle(result, new Random(seed));
        return result;
    }

    protected LinkedList<ArrayList<String>> parseChoices(ArrayList<String> choices1, ArrayList<String> choices3) {
        LinkedList<ArrayList<String>> result = super.parseChoices(choices1);
        for (String s : choices3) {
            s = s.replaceAll(" ", "");
            result.add(splitChoices(s));
        }
        Collections.shuffle(result, new Random(seed));
        return result;
    }

}
