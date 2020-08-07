package edu.gatech.seclass.wordfind6300;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private Spinner timeSpinner;
    private Spinner boardSizeSpinner;
    private Button saveButton;
    private Button mainButton;

    private GameController gameController;

    // create setting page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // instantiating GameController class
        gameController = GameController.getInstance(this);

        // config time spinner
        timeSpinner = findViewById(R.id.timeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time, android.R.layout.simple_spinner_item);
        // Specify the layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // apply data adapter to spinner
        timeSpinner.setAdapter(adapter);
        // get last saved numMinuteSetting in gameController object
        int numMinuteSetting = gameController.getNumMinuteSetting();
        // display numMinuteSetting in time spinner
        timeSpinner.setSelection(numMinuteSetting - 1); // index of numMinuteSetting start from 0


        // config board size spinner
        boardSizeSpinner = findViewById(R.id.boardSizeSpinner);
        ArrayAdapter<CharSequence> boardSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.boardSize, android.R.layout.simple_spinner_item);
        boardSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardSizeSpinner.setAdapter(boardSizeAdapter);
        // get last saved boardDimensionSetting in gameController object
        int boardDimensionSetting = gameController.getBoardDimensionSetting();
        // display boardDimensionSetting in board size spinner
        boardSizeSpinner.setSelection(boardDimensionSetting - 4); // index of boardDimensionSetting start from 0

        // config letter spinners (26 letters)
        for(char alphabet = 'A'; alphabet <='Z'; alphabet++ ) {
            String letterID = "letter" + alphabet + "Spinner";
            int id = getResources().getIdentifier(letterID, "id", getPackageName());
            Spinner letterSpinner = findViewById(id);
            ArrayAdapter<CharSequence> letterAdapter = ArrayAdapter.createFromResource(this,
                    R.array.letter, android.R.layout.simple_spinner_item);
            letterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            letterSpinner.setAdapter(letterAdapter);

            //convert char to string
            String letterString = "";
            if (alphabet == 'Q') {
                letterString = Character.toString(alphabet) + "u";
            } else {
                letterString = Character.toString((alphabet));
            }

            // get last saved letter weight in gameController object and display in letterSpinner
            for (Letter letter : gameController.getAlphabet()){
                if (letter.getLetterValue().equals(letterString)) {
                    letterSpinner.setSelection(letter.getWeight() - 1); // index of letter weight start from 0
                }
            }
        }

        // config save button
        saveButton = findViewById(R.id.saveButton);
        addListenerOnSaveButton();

        // config main button
        mainButton = findViewById(R.id.mainButton);
        addListenerOnMainButton();
    }

    // trigger below logic when pressing save button
    private void addListenerOnSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected Time
                int numMinuteSetting = Integer.parseInt(timeSpinner.getSelectedItem().toString());

                // get selected Board Size
                int boardDimensionSetting = Integer.parseInt(boardSizeSpinner.getSelectedItem().toString());

                // get selected letter weights and store in letterWeightMap
                Map<String, Integer> letterWeightMap = new HashMap<>();
                String letterString = "";
                for(char alphabet = 'A'; alphabet <='Z'; alphabet++ ) {
                    String letterID = "letter" + alphabet + "Spinner";
                    int id = getResources().getIdentifier(letterID, "id", getPackageName());
                    Spinner letterSpinner = findViewById(id);
                    int weight = Integer.parseInt(letterSpinner.getSelectedItem().toString());
                    if (alphabet == 'Q') {
                        letterString = Character.toString(alphabet) + "u";
                    } else {
                        letterString = Character.toString((alphabet));
                    }
                    letterWeightMap.put(letterString, weight);
                }

                // update gameController
                // update numMinuteSetting
                gameController.setNumMinuteSetting(numMinuteSetting);
                // update boardDimensionSetting
                gameController.setBoardDimensionSetting(boardDimensionSetting);
                // update letter weight in alphabet list
                for (Letter letter : gameController.getAlphabet()) {
                    letter.setWeight(letterWeightMap.get(letter.getLetterValue()));
                }

                //write gameController list to file
                gameController.writeToFile();

                // show pop-up notification after press save button
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Settings are saved!");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    // trigger below logic when pressing main button
    private void addListenerOnMainButton() {
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, GameControllerActivity.class);
                startActivity(intent);
            }
        });
    }
}