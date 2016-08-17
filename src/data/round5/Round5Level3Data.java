package data.round5;

import java.util.ArrayList;
import java.util.LinkedList;

public class Round5Level3Data {
    public final String INSTRUCTION_ZH;
    public final String INSTRUCTION_EN;
    public final int TIME_LIMIT;
    public final ArrayList<String> EXAMPLES;
    public final ArrayList<String> QUESTIONS;
    public final ArrayList<String> ANSWERS;
    public static final int NUM_OF_QUESTIONS = 10;

    public Round5Level3Data(String cnInstruction, String enInstruction, int timeLimit, ArrayList<String> examples, ArrayList<String> questions, ArrayList<String> answers) {
        INSTRUCTION_ZH = cnInstruction;
        INSTRUCTION_EN = enInstruction;
        TIME_LIMIT = timeLimit;
        EXAMPLES = examples;
        QUESTIONS = questions;
        ANSWERS = answers;
    }

    public LinkedList<Character> parseText(ArrayList<String> questionsText) {
        LinkedList<Character> result = new LinkedList<>();
        for (String s : questionsText) {
            for (int i = 0; i < s.length(); i++) {
                result.add(s.charAt(i));
            }
            result.add('\n');
        }
        return result;
    }
}
