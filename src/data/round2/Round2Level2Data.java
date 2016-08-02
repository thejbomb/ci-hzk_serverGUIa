package data.round2;

import java.util.ArrayList;

public class Round2Level2Data {
    public final String INSTRUCTION_ZH;
    public final String INSTRUCTION_EN;
    public final int TIME_LIMIT;
    public final ArrayList<String> EXAMPLES;
    public final ArrayList<String> QUESTIONS;

    public Round2Level2Data(String cnInstruction, String enInstruction, int timeLimit, ArrayList<String> examples, ArrayList<String> questions) {
        INSTRUCTION_ZH = cnInstruction;
        INSTRUCTION_EN = enInstruction;
        TIME_LIMIT = timeLimit;
        EXAMPLES = examples;
        QUESTIONS = questions;
    }

}
