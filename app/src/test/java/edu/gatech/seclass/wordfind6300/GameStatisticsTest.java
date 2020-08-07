package edu.gatech.seclass.wordfind6300;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStatisticsTest {

    @Test
    public void get_setFinalScore() {
        GameStatistics gs = new GameStatistics(5,4);
        int expected=100;
        gs.setFinalScore(expected);
        int output = gs.getFinalScore();
        assertEquals(expected, output);
    }

    @Test
    public void get_setNumberOfResets() {
        GameStatistics gs = new GameStatistics(5,4);
        int expected=100;
        gs.setNumberOfResets(expected);
        int output = gs.getNumberOfResets();
        assertEquals(expected, output);
    }

    @Test
    public void get_setNumberOfWords() {
        GameStatistics gs = new GameStatistics(5,4);
        int expected=12;
        gs.setNumberOfWords(expected);
        int output = gs.getNumberOfWords();
        assertEquals(expected, output);
    }

    @Test
    public void get_setHighestScoringWord() {
        GameStatistics gs = new GameStatistics(5,4);
        Word expected = new Word("MXYZPTLK", 3);
        gs.setHighestScoringWord(expected);
        Word output = gs.getHighestScoringWord();
        assertEquals(expected, output);
    }
}