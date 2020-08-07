package edu.gatech.seclass.wordfind6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameStatsDetailActivity extends AppCompatActivity {
    private Button backButton;

    // create game statistics detail page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_stats_detail);

        // receive data from game statistics page
        String finalScore = getIntent().getStringExtra("finalScore");
        String boardSize = getIntent().getStringExtra("boardSize");
        String numberOfMinutes = getIntent().getStringExtra("numberOfMinutes");
        String highestScoringWord = getIntent().getStringExtra("highestScoringWord");

        // display data to GUI
        TextView score = findViewById(R.id.score);
        score.setText(finalScore);
        TextView size = findViewById(R.id.boardSize);
        size.setText(boardSize);
        TextView time = findViewById(R.id.time);
        time.setText(numberOfMinutes);
        TextView highestWord = findViewById(R.id.highestScoreWord);
        highestWord.setText(highestScoringWord);

        // config back button
        backButton = findViewById(R.id.backButton);
        addListenerOnButton();
    }

    // trigger below logic when pressing back button
    private void addListenerOnButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameStatsDetailActivity.this, GameStatisticsActivity.class);
                startActivity(intent);
            }
        });
    }

}