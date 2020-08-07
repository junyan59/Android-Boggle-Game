package edu.gatech.seclass.wordfind6300;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GameStatisticsActivity extends AppCompatActivity {
    private Button mainButton;
    private GameController gameController;
    private List<Game> playedGames;

    // create game statistics page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_game_statistics, null);
        TableLayout tableLayout = v.findViewById(R.id.tableLayout);

        // get playedGames list and sort by final score descending
        gameController = GameController.getInstance(this);
        playedGames = gameController.getPlayedGames();
        sortByScore(playedGames);

        // dynamically generate table rows
        int order = 1;
        for (Game game : playedGames) {
            // Create a TableRow element
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);

            // get gameStats of a game
            List<Integer> gameStats = new ArrayList<>();
            gameStats.add(game.getGameStats().getFinalScore());
            gameStats.add(game.getGameStats().getNumberOfResets());
            gameStats.add(game.getGameStats().getNumberOfWords());

            // Add cell
            AppCompatTextView tv1 = new AppCompatTextView(this);
            tv1.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT));
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            tv1.setTextSize(22);
            tv1.setText(Integer.toString(order));
            tv1.setPaintFlags(tv1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tv1.setTextColor(Color.parseColor("#c94d4d"));
            tableRow.addView(tv1);

            for (Integer record : gameStats) {
                AppCompatTextView tv = new AppCompatTextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setTextSize(22);
                tv.setText(Integer.toString(record));
                tableRow.addView(tv);
            }
            // Add the tableRow element to the tableLayout
            tableLayout.addView(tableRow);
            order ++;

            // config view detail button
            addListenerOnViewDtl(tv1, game);
        }

        // Display the view
        setContentView(v);

        // config main button
        mainButton = findViewById(R.id.mainButton);
        addListenerOnButton();
    }

    // sort playedGame list by final score descending
    private void sortByScore(List<Game> playedGame) {
        Collections.sort(playedGame, new Comparator<Game>() {
            @Override
            public int compare(Game u1, Game u2) {
                return u2.getGameStats().getFinalScore() - u1.getGameStats().getFinalScore();
            }
        });
    }

    // trigger below logic when pressing main button
    private void addListenerOnButton() {
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameStatisticsActivity.this, GameControllerActivity.class);
                startActivity(intent);
            }
        });
    }

    // trigger below logic when pressing number button
    // pass game stats to detail page
    private void addListenerOnViewDtl(AppCompatTextView tv1, final Game game) {
        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GameStatisticsActivity.this, GameStatsDetailActivity.class);
                // get game stats from game object
                String finalScore = Integer.toString(game.getGameStats().getFinalScore());
                String boardSize = Integer.toString(game.getBoardDimension());
                String numberOfMinutes = Integer.toString(game.getNumMinutes());
                String highestScoringWord = "";
                if (game.getGameStats().getHighestScoringWord() == null) {
                    highestScoringWord = "No word found";
                } else {
                    highestScoringWord = game.getGameStats().getHighestScoringWord().getWordValue();
                }

                // pass game stats to detail page
                intent.putExtra("finalScore", finalScore);
                intent.putExtra("boardSize", boardSize);
                intent.putExtra("numberOfMinutes", numberOfMinutes);
                intent.putExtra("highestScoringWord", highestScoringWord);
                startActivity(intent);
            }
        });
    }
}