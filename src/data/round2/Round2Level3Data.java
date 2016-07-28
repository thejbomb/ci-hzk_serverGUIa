package data.round2;

import java.util.ArrayList;

public class Round2Level3Data {
    public final String CN_INSTRUCTION;
    public final String EN_INSTRUCTION;
    public final int TIME_LIMIT;
    public final ArrayList<String> EXAMPLES;
    public final ArrayList<String> QUESTIONS;
    public final ArrayList<String> ANSWERS;

    public Round2Level3Data(String cnInstruction, String enInstruction, int timeLimit, ArrayList<String> examples, ArrayList<String> questions, ArrayList<String> answers) {
        CN_INSTRUCTION = cnInstruction;
        EN_INSTRUCTION = enInstruction;
        TIME_LIMIT = timeLimit;
        EXAMPLES = examples;
        QUESTIONS = questions;
        ANSWERS = answers;
    }

}
