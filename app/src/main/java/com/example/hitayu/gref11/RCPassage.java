package com.example.hitayu.gref11;

/**
 * Created by Hitayu on 01-10-2017.
 */

public class RCPassage {
    private String passage;
    private int questionNumbers;

    public RCPassage() {}

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public int getQuestionNumbers() {
        return questionNumbers;
    }

    public void setQuestionNumbers(int questionNumbers) {
        this.questionNumbers = questionNumbers;
    }

    public RCPassage(String passage, int questionNumbers) {

        this.passage = passage;
        this.questionNumbers = questionNumbers;
    }
}
