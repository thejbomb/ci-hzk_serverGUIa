package data.round4;

import sun.awt.image.ImageWatched;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by quang on 08/18/16.
 */
public class Level1DataStructure {
    private String INSTRUCTION_ZH;
    private String INSTRUCTION_EN;
    private int TIME_LIMIT;
    private ArrayList<String> EXAMPLES;
    private ArrayList<String> QUESTIONS1;
    private ArrayList<String> CHOICES;
    private ArrayList<String> QUESTIONS2;

    private LinkedList<String> questions;
    private LinkedList<ArrayList<String>> choices;
    private LinkedList<String> correctAnswers;


    private long seed = 0;

    public void init() {
        seed = System.nanoTime();
        correctAnswers = parseCorrectAnswers(QUESTIONS1);
        choices = parseChoices(CHOICES);
        questions = combineQuestionLists(QUESTIONS1, QUESTIONS2);

    }

    private LinkedList<String> parseCorrectAnswers(ArrayList<String> questions1) {
        LinkedList<String> result = new LinkedList<>();
        ArrayList<String> newQuestions = new ArrayList<>();
        for (String s : questions1) {
            String newQuestion = "";
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
        System.out.println("Round 4 correct answers: " + result);
        QUESTIONS1 = newQuestions;
        return result;
    }

    private LinkedList<String> combineQuestionLists(ArrayList<String> questions1, ArrayList<String> questions2) {
        LinkedList<String> result = new LinkedList<>();
        result.addAll(questions1);
        result.addAll(questions2);

        Collections.shuffle(result, new Random(seed));

        System.out.println("Round 4 questions: " + result);

        return result;
    }

    private LinkedList<ArrayList<String>> parseChoices(ArrayList<String> choices) {
        LinkedList<ArrayList<String>> result = new LinkedList<>();
        for (String s : choices) {
            ArrayList<String> string = new ArrayList<>();
            int index1 = 0;
            while (index1 < s.length()) {
                index1 = s.indexOf(',', index1 + 1);
                if (index1 == -1)
                    break;
                string.add(s.substring(0, index1));
                string.add(s.substring(index1 + 1));
            }
            long seed = System.nanoTime();
            Collections.shuffle(string, new Random(seed));
            result.add(string);
        }
        for (int i = 0; i < QUESTIONS2.size(); i++) {
            result.add(new ArrayList<>());
        }
        Collections.shuffle(result, new Random(seed));
        System.out.println("Round 4 choices: " + result);
        return result;
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

    public long getSeed() {
        return seed;
    }

}
