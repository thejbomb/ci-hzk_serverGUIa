package data.round4;

import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by quang on 08/18/16.
 */
public class DataStructure {
    public String INSTRUCTION_ZH;
    public String INSTRUCTION_EN;
    public int TIME_LIMIT;
    public ArrayList<String> EXAMPLES;
    protected ArrayList<String> QUESTIONS1;
    protected ArrayList<String> CHOICES;
    protected ArrayList<String> QUESTIONS2;
    public final String QUESTION_INSTRUCTION1_ZH = "选字填空";
    public final String QUESTION_INSTRUCTION1_EN = "Fill in the blank with the correct answer";
    public final String QUESTION_INSTRUCTION2_ZH = "读出汉字意思，并说出英文意思";
    public final String QUESTION_INSTRUCTION2_EN = "Read aloud the characters and say the meaning of the word in English";
    protected LinkedList<Integer> questionType;

    protected LinkedList<String> questions;
    protected LinkedList<ArrayList<String>> choices;
    protected LinkedList<String> correctAnswers;


    protected long seed = 0;

    public void init() {
        seed = System.nanoTime();
        if (questionType == null)
            questionType = new LinkedList<>();
        for (int i = 0; i < QUESTIONS1.size(); i++)
            questionType.add(0); // type 0 = fill in the blank
        for (int i = 0; i < QUESTIONS2.size(); i++)
            questionType.add(1); // type 1 = read aloud
        Collections.shuffle(questionType, new Random(seed));
        correctAnswers = parseCorrectAnswers(QUESTIONS1);
        choices = parseChoices(CHOICES);
        questions = combineQuestionLists(QUESTIONS1, QUESTIONS2);
        System.out.println("Round 4 Q: " + questions);
        System.out.println("Round 4 C: " + choices);
        System.out.println("Round 4 A: " + correctAnswers);
        System.out.println("Seed: " + seed);
    }

    protected LinkedList<String> parseCorrectAnswers(ArrayList<String> questions1) {
        LinkedList<String> result = new LinkedList<>();
        ArrayList<String> newQuestions = new ArrayList<>();
        for (String s : questions1) {
            String newQuestion = "";
            s = s.replace('（','(');
            s = s.replace('）',')');
            s = s.replaceAll(" ", "");
            int index1 = s.indexOf('(');
            int index2 = s.indexOf(')');

            newQuestion += s.substring(0, index1) + "_" + s.substring(index2 + 1);
            String answer = s.substring(index1 + 1, index2);
            newQuestions.add(newQuestion);
            result.add(answer);
        }
        for (int i = 0; i < QUESTIONS2.size(); i++) {
            result.add("");
        }
        Collections.shuffle(result, new Random(seed));
        QUESTIONS1 = newQuestions;
        return result;
    }

    protected LinkedList<String> combineQuestionLists(ArrayList<String> questions1, ArrayList<String> questions2) {
        LinkedList<String> result = new LinkedList<>();
        result.addAll(questions1);
        result.addAll(questions2);

        Collections.shuffle(result, new Random(seed));

        return result;
    }

    protected LinkedList<ArrayList<String>> parseChoices(ArrayList<String> choices) {
        LinkedList<ArrayList<String>> result = new LinkedList<>();
        for (String s : choices) {
            s = s.replace('，',',');
            s = s.replaceAll(" ", "");
            result.add(splitChoices(s));
        }
        for (int i = 0; i < QUESTIONS2.size(); i++) {
            result.add(new ArrayList<>());
        }
        Collections.shuffle(result, new Random(seed));
        return result;
    }

    protected ArrayList<String> splitChoices(String s) {
        ArrayList<String> string = new ArrayList<>();

        int index2 = -1;
        while (index2 < s.length()) {
            int index1 = index2 + 1;
            index2 = s.indexOf(',',index1);
            if (index2 == -1) {
                string.add(s.substring(index1));
                break;
            }

            string.add(s.substring(index1, index2));
        }

        Collections.shuffle(string, new Random(seed));

        return string;
    }

    public boolean isCorrect(String answer, int questionNumber) {
        System.out.println("User answer: " + answer);
        System.out.println("System answer: " + correctAnswers.get(questionNumber));
        if (answer.compareTo(correctAnswers.get(questionNumber)) == 0)
            return true;
        else
            return false;
    }

    public String getINSTRUCTION_ZH() {
        return INSTRUCTION_ZH;
    }

    public String getINSTRUCTION_EN() {
        return INSTRUCTION_EN;
    }

    public ArrayList<String> getEXAMPLES() {
        return EXAMPLES;
    }

    public LinkedList<String> getChoices(int questionNumber) {
        return new LinkedList<>(choices.get(questionNumber));
    }

    public String getQuestions(int questionNumber) {
        return questions.get(questionNumber);
    }

    public LinkedList<String> getQuestions() {
        return questions;
    }

    public int getQuestionType(int questionNumber){
        return questionType.get(questionNumber);
    }

    public long getSeed() {
        return seed;
    }

}
