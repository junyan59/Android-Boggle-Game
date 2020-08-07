package edu.gatech.seclass.wordfind6300;

import java.util.*;

public class Board {
    private int boardDimension;
    private ArrayList<Letter> letterList;
    private Random randomGen = new Random();

    public Board(int boardDimension,ArrayList<Letter> alphabet) {
        this.boardDimension = boardDimension;
        this.letterList = new ArrayList<Letter>();
        //for (int i = 0; i < this.boardDimension*this.boardDimension; i++) {
        //    letterList.add(getRandomLetter(alphabet));
        //}
        letterList = getRandomAlphabet(alphabet);
    }

    public ArrayList<Letter> getLetterList() {
        return letterList;
    }

    //this is the code that will return a random alphabet that is boardDimension*2
    public ArrayList<Letter> getRandomAlphabet(ArrayList<Letter> alphabet) {

        ArrayList<Letter> randomAlphabet = new ArrayList<>();
        ArrayList<Letter> weightedConsonants = new ArrayList<>();
        ArrayList<Letter> weightedVowels = new ArrayList<>();

        //split alphabet into consonants and vowels
        for (int i = 0; i < alphabet.size(); i++) {
            for (int j = 1; j <= alphabet.get(i).getWeight();j++) {
                if (alphabet.get(i).isVowel()) {
                    weightedVowels.add(alphabet.get(i));
                }
                if (!alphabet.get(i).isVowel()) {
                    weightedConsonants.add(alphabet.get(i));
                }
            }
        }

        // Total number of letters needed to fill the board
        int totalLetters = this.boardDimension*this.boardDimension;

        // Number of Consonants needed (rounded down)
        double numConsonants = Math.floor(totalLetters * .8);

        // Number of Vowels needed (rounded up)
        double numVowels = Math.ceil(totalLetters * .2);

        // List of Letters to display on the board
        ArrayList<Letter> boardLetters = new ArrayList<>();

        // Initialize temp variables
        int randomIndex = 0;

        //Get the random Vowels
        for (int i = 1; i <= numVowels; i++) {
            randomIndex = randomGen.nextInt(weightedVowels.size());
            boardLetters.add(weightedVowels.get(randomIndex));
        }

        //Get the random Consonants
        for (int i = 1; i <= numConsonants; i++) {
            randomIndex = randomGen.nextInt(weightedConsonants.size());
            boardLetters.add(weightedConsonants.get(randomIndex));
        }

        //Add letters to the board
        for (int i = 0; i <= totalLetters-1; i++) {
            randomIndex = randomGen.nextInt(boardLetters.size());
            randomAlphabet.add(boardLetters.get(randomIndex));
            boardLetters.remove(randomIndex);
        }

        return randomAlphabet;
    }

    public Letter getRandomLetter(ArrayList<Letter> alphabet) {
        int index = randomGen.nextInt(alphabet.size());
        Letter randomLetter = alphabet.get(index);

        return randomLetter;
    }

}
