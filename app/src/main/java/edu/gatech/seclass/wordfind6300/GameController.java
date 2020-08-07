package edu.gatech.seclass.wordfind6300;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

// use Singleton pattern to maintain gameController object, so that other classes such as SettingActivity,
// WordGameActivity and GameControllerActivity can use or modify the same object
public class GameController {
    // static variable single_instance of type Singleton
    private static GameController gameController = null;
    private static Context context;

    // variables
    private List<Game> playedGames;
    private List<Word> playedWords;
    private Game currentGame;
    private int numMinuteSetting;
    private int boardDimensionSetting;
    private ArrayList<Letter> alphabet;

    private static final int DEFAULT_TIME = 3;
    private static final int DEFAULT_BOARD_SIZE = 4;
    private static final int DEFAULT_POINT_VALUE = 1;
    private static final int QU_POINT_VALUE = 2;
    private static final int DEFAULT_LETTER_WEIGHT = 1;
    private static final String FILE_NAME = "savedAppStates.json";

    // private constructor restricted to this class itself
    public GameController() {
        this.playedGames = new ArrayList<>();
        this.playedWords = new ArrayList<>();
        this.currentGame = null;
        this.numMinuteSetting = DEFAULT_TIME;
        this.boardDimensionSetting = DEFAULT_BOARD_SIZE;
        this.alphabet = new ArrayList<>();
        // set default alphabet values
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            int pointValue = DEFAULT_POINT_VALUE;
            Boolean isVowel = false;
            String letterString = "";
            if (letter == 'Q') {
                letterString = Character.toString(letter) + "u";
            } else {
                letterString = Character.toString((letter));
            }
            if ("AEIOU".indexOf(letterString) != -1) {
                isVowel = true;
            }
            if (letterString.equals("Qu")) {
                pointValue = QU_POINT_VALUE;
            }
            alphabet.add(new Letter(letterString, DEFAULT_LETTER_WEIGHT, pointValue, isVowel));
        }
    }

    // static method to create instance of Singleton class
    public static synchronized GameController getInstance(Context context) {
        if (gameController == null) {
            gameController = new GameController();
            GameController.context = context;
            //read savedGameController object from file
            GameController savedGameController = readFile();
            if (savedGameController != null) {
                gameController = savedGameController;
            }
        }
        return gameController;
    }

    // read gameController from file
    private static GameController readFile() {
        try (InputStream inputStream = context.openFileInput(FILE_NAME)) {
            if (inputStream == null) {
                return null;
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }
            String ret = stringBuilder.toString();
            System.out.println("read" + ret);
            Gson gson = new Gson();
            return gson.fromJson(ret, GameController.class);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        return null;
    }

    // write gameController to file
    public void writeToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(gameController);
        try (OutputStreamWriter output = new OutputStreamWriter(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE))) {
            output.write(json);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    // setters and getters
    public void setPlayedGames(List<Game> playedGames) {
        this.playedGames = playedGames;
    }

    public void setPlayedWords(List<Word> playedWords) {
        this.playedWords = playedWords;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public void setNumMinuteSetting(int numMinuteSetting) {
        this.numMinuteSetting = numMinuteSetting;
    }

    public void setBoardDimensionSetting(int boardDimensionSetting) {
        this.boardDimensionSetting = boardDimensionSetting;
    }

    public void setAlphabet(ArrayList<Letter> alphabet) {
        this.alphabet = alphabet;
    }

    public List<Game> getPlayedGames() {
        return playedGames;
    }

    public List<Word> getPlayedWords() {
        return playedWords;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public int getNumMinuteSetting() {
        return numMinuteSetting;
    }

    public int getBoardDimensionSetting() {
        return boardDimensionSetting;
    }

    public ArrayList<Letter> getAlphabet() {
        return alphabet;
    }

    // print gameController object
    public void logGameController() {
        System.out.println("Time: " + numMinuteSetting);
        System.out.println("Board Size: " + boardDimensionSetting);
        for (Letter letter : alphabet) {
            System.out.println(letter);
        }
        System.out.println("Current Game: " + currentGame);
        System.out.println("Played Game List: " + playedGames);
        System.out.println("Played Word List: " + playedWords);
    }
}
