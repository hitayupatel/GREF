package com.example.hitayu.gref11;

/**
 * Created by Hitayu on 01-10-2017.
 */

public class RCPassageQuestions {

    private String question;
    private String OptionA;
    private String OptionB;
    private String OptionC;
    private String OptionD;
    private String OptionE;
    private String answer;
    private String explanation;

    public RCPassageQuestions() {}

    public RCPassageQuestions(String question, String optionA, String optionB, String optionC,
                              String optionD, String optionE, String answer, String explanation) {
        this.question = question;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        OptionE = optionE;
        this.answer = answer;
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public String getOptionE() {
        return OptionE;
    }

    public void setOptionE(String optionE) {
        OptionE = optionE;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
