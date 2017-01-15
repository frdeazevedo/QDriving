package com.romeraze.qdriving;

/**
 * Created by fabiano on 14-Jan-17.
 */

public class Question {
    public Question(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return this.question;
    }

    public boolean getAnswer() {
        return this.answer;
    }

    //Returns TRUE if the question was correctly answered
    public boolean answerTheQuestion(boolean proposedAnswer) {
        return (this.answer == proposedAnswer);
    }

    private String question;
    private boolean answer;
}
