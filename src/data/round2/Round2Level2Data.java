package data.round2;

import java.util.ArrayList;

public class Round2Level2Data {
    public final String CN_INSTRUCTION;
    public final String EN_INSTRUCTION;
    public final int TIME_LIMIT;
    public final ArrayList<String> EXAMPLES;
    public final ArrayList<String> QUESTIONS;

    public Round2Level2Data(String cnInstruction, String enInstruction, int timeLimit, ArrayList<String> examples, ArrayList<String> questions) {
        CN_INSTRUCTION = cnInstruction;
        EN_INSTRUCTION = enInstruction;
        TIME_LIMIT = timeLimit;
        EXAMPLES = examples;
        QUESTIONS = questions;
    }

}
