package edu.gatech.seclass.wordfind6300;

import org.junit.Test;

import static org.junit.Assert.*;
public class WordTest {
    @Test
    public void getScore() {
        Word w = new Word("xyz", 3);
        int expected = 3;
        int output = w.getScore();
        assertEquals(expected, output);
    }

    @Test
    public void getWordValue() {
        Word w = new Word("xyz", 3);
        String expected = "xyz";
        String output = w.getWordValue();
        assertEquals(expected, output);
    }

    @Test
    public void setScore() {
        Word w = new Word("xyz", 3);
        int expected = 5;
        w.setScore(5);
        int output = w.getScore();
        assertEquals(expected, output);
    }
}