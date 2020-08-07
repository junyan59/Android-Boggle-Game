package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;

public class WordGameActivity extends AppCompatActivity {

    private GameController gameController;
    private int boardDimension;
    private int numMinuteSetting;
    private Game currentGame;
    private TextView wordTextPane;
    private TextView currentTotalScoreTextPane;
    private TextView counterView;
    private int counter;
    private Button rerollButton;
    private Button quitButton;
    private Button cancelButton;
    private Button confirmButton;
    private GridView boardGrid;
    private String displayWord = "";
    private int currentWordScore = 0;
    private int startingPosition = -1;
    private String[] testAlpha;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);

        gameController = GameController.getInstance(this);

        //create Game
        currentGame = gameController.getCurrentGame();
        this.boardDimension = gameController.getBoardDimensionSetting();
        this.numMinuteSetting = gameController.getNumMinuteSetting();

        counterView=findViewById(R.id.counttime);
        countDownTimer = new CountDownTimer(this.numMinuteSetting*60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeleft = millisUntilFinished/60000%60+":"+millisUntilFinished/1000%60;
                //String timeleft = millisUntilFinished/60000%60+1+" min";
                counterView.setText(timeleft);
                counter++;
            }
            @Override
            public void onFinish() {
                counterView.setText("Finished");
                gameController.getPlayedGames().add(currentGame.exitGame());
                gameController.getPlayedWords().addAll(currentGame.getWordsPlayed());
                //write gameController object to file
                gameController.writeToFile();
                startingPosition = -1;

                // show final score after timer runs out
                AlertDialog.Builder builder = new AlertDialog.Builder(WordGameActivity.this);
                builder.setTitle("Times up! Your Final Score:");
                builder.setMessage(Integer.toString(currentGame.getGameStats().getFinalScore()));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        // go back to main menu
                        Intent intent = new Intent(WordGameActivity.this, GameControllerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        }.start();


        //load up labels for first board
        testAlpha = new String[boardDimension*boardDimension];
        for (int i = 0; i < boardDimension*boardDimension; i++) {
            testAlpha[i] = currentGame.getCurrentBoard().getLetterList().get(i).getLetterValue();
        }



        wordTextPane = findViewById(R.id.wordTextPane);
        currentTotalScoreTextPane = findViewById(R.id.currentScoreView);

        this.boardGrid = findViewById(R.id.boardGrid);
        this.boardGrid.setNumColumns(boardDimension);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, testAlpha);
        this.boardGrid.setAdapter(adapter);

        boardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (isOKposition(startingPosition,position)) {
                    displayWord = displayWord + testAlpha[position];
                    currentWordScore = currentWordScore + currentGame.getCurrentBoard().getLetterList().get(position).getPointValue();
                    wordTextPane.setText(displayWord);
                    startingPosition = position;
                } else {
                    // pop-up notification if the wrong square is pushed
                    AlertDialog.Builder builder = new AlertDialog.Builder(WordGameActivity.this);
                    builder.setMessage("You pressed an invalid square.");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });

        //Re-roll button
        rerollButton = findViewById(R.id.reroll);
        rerollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentGame.setCurrentBoard(new Board(boardDimension));
                currentGame.rerollBoard(boardDimension);
                for (int i = 0; i < boardDimension*boardDimension; i++) {
                    testAlpha[i] = currentGame.getCurrentBoard().getLetterList().get(i).getLetterValue();
                }
                adapter.notifyDataSetChanged();
                displayWord = "";
                currentTotalScoreTextPane.setText(Integer.toString(currentGame.getScore()));
                wordTextPane.setText(displayWord);
                currentWordScore = 0;
                startingPosition = -1;
            }
        });

        //Quit Button
        quitButton = findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stop countdown timer
                countDownTimer.cancel();
                gameController.getPlayedGames().add(currentGame.exitGame());
                gameController.getPlayedWords().addAll(currentGame.getWordsPlayed());
                displayWord = "";
                wordTextPane.setText(displayWord);
                currentWordScore = 0;
                startingPosition = -1;
                //write gameController object to file
                gameController.writeToFile();
                // show final score after pressing quit button
                AlertDialog.Builder builder = new AlertDialog.Builder(WordGameActivity.this);
                builder.setTitle("Your Final Score:");
                builder.setMessage(Integer.toString(currentGame.getGameStats().getFinalScore()));
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        // go back to main menu
                        Intent intent = new Intent(WordGameActivity.this, GameControllerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        });

        //Cancel Button
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayWord = "";
                wordTextPane.setText(displayWord);
                startingPosition = -1;
                currentWordScore = 0;
            }
        });

        //Confirm Button
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if the length of input word less than 2, pop-up warning message and return
                if (displayWord.length() < 2 || displayWord.equals("Qu")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WordGameActivity.this);
                    builder.setMessage("The length of the word can not be less than 2.");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                    return;
                }

                if (currentGame.enterWord(new Word(displayWord, currentWordScore))) {
                    displayWord = "";
                    currentTotalScoreTextPane.setText(Integer.toString(currentGame.getScore()));
                    wordTextPane.setText(displayWord);
                    startingPosition = -1;
                    currentWordScore = 0;
                } else {
                    // show pop-up message "Word already be played"
                    AlertDialog.Builder builder = new AlertDialog.Builder(WordGameActivity.this);
                    builder.setMessage("Word already played!");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });


    }

    private boolean isOKposition(int currentPosition, int newPosition) {
        if (startingPosition == -1) { return true; }

        if (currentPosition % this.boardDimension == 0) {
            //rules for first column
            if (newPosition == currentPosition + 1) { return true; }
            if (newPosition == currentPosition + this.boardDimension) { return true; }
            if (newPosition == currentPosition - this.boardDimension) { return true; }
            if (newPosition == currentPosition + 1 + this.boardDimension) { return true; }
            if (newPosition == currentPosition + 1 - this.boardDimension) { return true; }
        } else if (currentPosition % this.boardDimension == this.boardDimension-1) {
            //rules for last column
            if (newPosition == currentPosition - 1) { return true; }
            if (newPosition == currentPosition + this.boardDimension) { return true; }
            if (newPosition == currentPosition - this.boardDimension) { return true; }
            if (newPosition == currentPosition - 1 + this.boardDimension) { return true; }
            if (newPosition == currentPosition - 1 - this.boardDimension) { return true; }
        } else {
            //rules for middle columns
            if (newPosition == currentPosition + 1) { return true; }
            if (newPosition == currentPosition - 1) { return true; }
            if (newPosition == currentPosition + this.boardDimension) { return true; }
            if (newPosition == currentPosition - this.boardDimension) { return true; }
            if (newPosition == currentPosition + 1 + this.boardDimension) { return true; }
            if (newPosition == currentPosition - 1 + this.boardDimension) { return true; }
            if (newPosition == currentPosition + 1 - this.boardDimension) { return true; }
            if (newPosition == currentPosition - 1 - this.boardDimension) { return true; }
        }

        return false;
    }
}
