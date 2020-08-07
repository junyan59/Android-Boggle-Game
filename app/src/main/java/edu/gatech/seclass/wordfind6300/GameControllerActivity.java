package edu.gatech.seclass.wordfind6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameControllerActivity extends AppCompatActivity {
    private Button playButton;
    private Button settingButton;
    private Button gameStatisticsButton;
    private Button wordStatisticsButton;

    private GameController gameController;

    // create main menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_controller);

        // instantiating GameController class
        gameController = GameController.getInstance(this);

        playButton = findViewById(R.id.playButton);
        // trigger below logic if play game button is pressed
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playWordGame();
            }
        });

        settingButton = findViewById(R.id.settingButton);
        // trigger below logic if setting button is pressed
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustSettings();
            }
        });

        gameStatisticsButton = findViewById(R.id.gameStatisticsButton);
        // trigger below logic if game statistics button is pressed
        gameStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewGameStatistics();
            }
        });

        wordStatisticsButton = findViewById(R.id.wordStatisticsButton);
        // trigger below logic if word statistics button is pressed
        wordStatisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWordStatistics();
            }
        });
    }

    // instantiating game object, add game object to playedGames list, go to word game page
    protected void playWordGame() {
        Game currentGame = new Game(gameController.getNumMinuteSetting(), gameController.getBoardDimensionSetting(), gameController.getAlphabet());
        gameController.setCurrentGame(currentGame);
        Intent intent = new Intent(GameControllerActivity.this, WordGameActivity.class);
        startActivity(intent);
    }

    // go to settings page
    protected void adjustSettings() {
        Intent intent = new Intent(GameControllerActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    // go to game statistics page
    protected void viewGameStatistics() {
        Intent intent = new Intent(GameControllerActivity.this, GameStatisticsActivity.class);
        startActivity(intent);
    }

    // go to word statistics page
    protected void viewWordStatistics() {
        Intent intent = new Intent(GameControllerActivity.this, WordStatisticsActivity.class);
        startActivity(intent);
    }
}
