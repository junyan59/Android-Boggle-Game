package edu.gatech.seclass.wordfind6300;

import org.junit.Test;

import static org.junit.Assert.*;

public class LetterTest {

    @Test
    public void getLetterValue() {
        Letter l = new Letter("Q", 1, 2, false);
        String expected = "Q";
        String output = l.getLetterValue();
        assertEquals(expected, output);
    }

    @Test
    public void setLetterValue() {
        Letter l = new Letter("Q", 1, 2, false);
        String expected = "Z";
        l.setLetterValue("Z");
        String output = l.getLetterValue();
        assertEquals(expected, output);
    }

    @Test
    public void getWeight() {
        Letter l = new Letter("Q", 3, 2, false);
        int expected = 3;
        int output = l.getWeight();
        assertEquals(expected, output);
    }

    @Test
    public void setWeight() {
        Letter l = new Letter("Q", 3, 2, false);
        int expected = 1;
        l.setWeight(1);
        int output = l.getWeight();
        assertEquals(expected, output);
    }

    @Test
    public void getPointValue() {
        Letter l = new Letter("Q", 3, 2, false);
        int expected = 2;
        int output = l.getPointValue();
        assertEquals(expected, output);
    }

    @Test
    public void setPointValue() {
        Letter l = new Letter("Q", 3, 2, false);
        int expected = 1;
        l.setPointValue(1);
        int output = l.getPointValue();
        assertEquals(expected, output);
    }

    @Test
    public void isVowel() {
        Letter l = new Letter("E", 3, 2, true);
        boolean expected = true;
        boolean output = l.isVowel();
        assertEquals(expected, output);
    }

    @Test
    public void setVowel() {
        Letter l = new Letter("E", 3, 2, true);
        boolean expected = false;
        l.setVowel(false);
        boolean output = l.isVowel();
        assertEquals(expected, output);
    }
}