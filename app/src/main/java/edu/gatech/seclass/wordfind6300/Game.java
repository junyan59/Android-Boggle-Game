package edu.gatech.seclass.wordfind6300;

import java.util.ArrayList;

public class Game {
    private int numMinutes;
    private int gameID;
    private int boardDimension;
    private Board currentBoard;
    private Word currentWord;

    private int score = 0;
    private ArrayList<Word> wordsPlayed;
    private GameStatistics gameStats;
    private ArrayList<Letter> alphabet;


    public Game(int numMinutes, int boardDimension, ArrayList<Letter> alphabet) {
        this.numMinutes = numMinutes;
        this.boardDimension = boardDimension;
        this.alphabet = alphabet;
        this.currentBoard = new Board(boardDimension,alphabet);
        this.wordsPlayed = new ArrayList<Word>();
        this.gameStats = new GameStatistics(boardDimension,numMinutes);
    }

    public boolean enterWord(Word enteredWord) {
        if (verifyWord(enteredWord.getWordValue())) {
            this.score = this.score + enteredWord.getScore();
            gameStats.setNumberOfWords(gameStats.getNumberOfWords() + 1);
            if (isHighestScoring(enteredWord.getScore())) {
                gameStats.setHighestScoringWord(enteredWord);
            }
            wordsPlayed.add(enteredWord);
            return true;
        } else {
            return false;
        }
    }

    private boolean isHighestScoring(int enteredWordScore) {
        //iterate through list of wordsPlayed and compare String
        for (int i = 0; i < this.wordsPlayed.size(); i++) {
            if (wordsPlayed.get(i).getScore() >= enteredWordScore) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyWord(String enteredWord) {
        //iterate through list of wordsPlayed and compare String
        for (int i = 0; i < this.wordsPlayed.size(); i++) {
            if (wordsPlayed.get(i).getWordValue().equals(enteredWord)) {
                return false;
            }
        }
        return true;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(Word currentWord) {
        this.currentWord = currentWord;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public Board rerollBoard(int bd) {
        currentBoard = new Board(bd,this.getAlphabet());
        score = score - 5;
        gameStats.setNumberOfResets(gameStats.getNumberOfResets()+1);
        return currentBoard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Word> getWordsPlayed() {
        return wordsPlayed;
    }

    public void setWordsPlayed(ArrayList<Word> wordsPlayed) {
        this.wordsPlayed = wordsPlayed;
    }

    public Game exitGame() {
        gameStats.setFinalScore(getScore());
        return this;
    }

    @Override
    public String toString() {
        return ("numMinutes: "+this.getNumMinutes()+
                " boardDimension: "+ this.getBoardDimension() +
                " alphabet: "+ this.getAlphabet());
    }

    public int getNumMinutes() {
        return numMinutes;
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    public ArrayList<Letter> getAlphabet() {
        return alphabet;
    }

    public GameStatistics getGameStats() {
        return gameStats;
    }
}
