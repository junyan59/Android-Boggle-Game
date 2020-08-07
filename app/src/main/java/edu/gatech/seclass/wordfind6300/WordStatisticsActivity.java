package edu.gatech.seclass.wordfind6300;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordStatisticsActivity extends AppCompatActivity {
    private Button mainButton;
    private GameController gameController;
    private List<Word> playedWords;
    private LinkedHashMap<String, Integer> sortedFreqMap;

    // create word statistics page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_word_statistics, null);
        TableLayout tableLayout = v.findViewById(R.id.tableLayout);

        // get playedWord list and sort by frequency descending
        gameController = GameController.getInstance(this);
        playedWords = gameController.getPlayedWords();
        sortedFreqMap = sortByFrequency(playedWords);
        System.out.println(sortedFreqMap);

        // dynamically generate table rows
        for (Map.Entry<String, Integer> entry : sortedFreqMap.entrySet()) {
            // Create a TableRow element
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
            // add wordValue
            AppCompatTextView tv = new AppCompatTextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.drawable.cell_shape);
            tv.setTextSize(22);
            tv.setText(entry.getKey());
            tableRow.addView(tv);
            // add frequency
            AppCompatTextView tv1 = new AppCompatTextView(this);
            tv1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundResource(R.drawable.cell_shape);
            tv1.setTextSize(22);
            tv1.setText(Integer.toString(entry.getValue()));
            tableRow.addView(tv1);

            // Add the tableRow element to the tableLayout
            tableLayout.addView(tableRow);
        }

        // Display the view
        setContentView(v);

        // config main button
        mainButton = findViewById(R.id.mainButton);
        addListenerOnButton();
    }

    // sort playedWord list by frequency descending
    private LinkedHashMap<String, Integer> sortByFrequency(List<Word> playedWords) {
        // count word frequency and store in hashmap
        Map<String, Integer> freqMap = new HashMap<>();
        for (Word w : playedWords) {
            String wordValue = w.getWordValue();
            Integer n = freqMap.get(wordValue);
            n = (n == null) ? 1 : ++n;
            freqMap.put(wordValue, n);
        }
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(freqMap.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()) - (o1.getValue());
            }
        });
        // put data from sorted list to hashmap
        LinkedHashMap<String, Integer> sortedFreqMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            sortedFreqMap.put(aa.getKey(), aa.getValue());
        }
        return sortedFreqMap;
    }

    // trigger below logic when pressing main button
    private void addListenerOnButton() {
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordStatisticsActivity.this, GameControllerActivity.class);
                startActivity(intent);
            }
        });
    }
}