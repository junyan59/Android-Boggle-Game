package edu.gatech.seclass.wordfind6300;

public class GameStatistics {
    private int finalScore;
    private int numberOfResets;
    private int numberOfWords;
    private int boardSize;
    private int numberOfMinutes;
    private Word highestScoringWord;

    public GameStatistics(int boardSize, int numberOfMinutes) {
        this.boardSize = boardSize;
        this.numberOfMinutes = numberOfMinutes;
        this.numberOfResets = 0;
        this.numberOfWords = 0;
        //initialize highestScoringWord
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getNumberOfResets() {
        return numberOfResets;
    }

    public void setNumberOfResets(int numberOfResets) {
        this.numberOfResets = numberOfResets;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public Word getHighestScoringWord() {
        return highestScoringWord;
    }

    public void setHighestScoringWord(Word highestScoringWord) {
        this.highestScoringWord = highestScoringWord;
    }
}
