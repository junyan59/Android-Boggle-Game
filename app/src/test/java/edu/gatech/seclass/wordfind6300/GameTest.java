package edu.gatech.seclass.wordfind6300;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void enterWord() {
        GameController gc = new GameController();
        Word w = new Word("xyz", 3);
        Game g = new Game(4,5, gc.getAlphabet());
        boolean expected = true;
        boolean output = g.enterWord(w);
        assertEquals(expected, output);
    }


    @Test
    public void get_setCurrentWord() {
        GameController gc = new GameController();
        Word w = new Word("xyz", 3);
        Game g = new Game(4,5, gc.getAlphabet());
        g.setCurrentWord(w);
        Word expected = w;
        Word output = g.getCurrentWord();
        assertEquals(expected, output);
    }

    @Test
    public void verifyWord_true() {
        GameController gc = new GameController();
        Game g = new Game(4,5, gc.getAlphabet());
        boolean expected = true;
        boolean output = g.verifyWord("abc");
        assertEquals(expected, output);
    }

    @Test
    public void verifyWord_false() {
        GameController gc = new GameController();
        Game g = new Game(4,5, gc.getAlphabet());
        Word w = new Word("xyz", 3);
        boolean expected = false;
        g.enterWord(w);
        boolean output = g.verifyWord("xyz");
        assertEquals(expected, output);
    }

    @Test
    public void get_setCurrentBoard() {
        GameController gc = new GameController();
        ArrayList alphabet = gc.getAlphabet();
        Game g = new Game(4,5, alphabet);
        Board b = new Board(5, alphabet);
        g.setCurrentBoard(b);
        Board expected = b;
        Board output = g.getCurrentBoard();
        assertEquals(expected, output);
    }

    @Test
    public void getNumMinutes() {
        GameController gc = new GameController();
        Game g = new Game(4,5, gc.getAlphabet());
        int expected = 4;
        int output = g.getNumMinutes();
        assertEquals(expected, output);
    }

    @Test
    public void getBoardDimension() {
        GameController gc = new GameController();
        Game g = new Game(4,5, gc.getAlphabet());
        int expected = 5;
        int output = g.getBoardDimension();
        assertEquals(expected, output);
    }
}