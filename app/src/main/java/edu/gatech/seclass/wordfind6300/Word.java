package edu.gatech.seclass.wordfind6300;

public class Word {
    private String wordValue;
    private int score;

    public Word(String wordValue, int score) {
        this.wordValue = wordValue;
        this.score = score;
    }

    public String getWordValue() {
        return wordValue;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
