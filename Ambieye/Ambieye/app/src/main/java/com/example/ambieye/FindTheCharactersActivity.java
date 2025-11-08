package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindTheCharactersActivity extends AppCompatActivity {

    private TextView scoreTextView, timerTextView, instructionTextView;
    private GridLayout charactersGrid;
    private ImageView backButton;

    private int score = 0;
    private int currentRound = 0;
    private char[] charactersToFind = {'A', 'B', 'C', 'D', 'E'};
    private char correctCharacter;
    private int correctCharacterCount = 0;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_characters);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        charactersGrid = findViewById(R.id.charactersGrid);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        startNewRound();
        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                timerTextView.setText("Time: 0s");
                endGame();
            }
        }.start();
    }

    private void startNewRound() {
        if (currentRound >= charactersToFind.length) {
            endGame();
            return;
        }

        correctCharacter = charactersToFind[currentRound];
        instructionTextView.setText("Find all the letter " + correctCharacter + "'s");

        charactersGrid.removeAllViews();
        generateCharacters();
    }

    private void generateCharacters() {
        List<Character> characters = new ArrayList<>();
        correctCharacterCount = 0;
        for (int i = 0; i < 5; i++) { // Add 5 correct characters
            characters.add(correctCharacter);
            correctCharacterCount++;
        }
        for (int i = 0; i < 59; i++) { // Add 59 random incorrect characters
            char randomChar;
            do {
                randomChar = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
            } while (randomChar == correctCharacter);
            characters.add(randomChar);
        }

        Collections.shuffle(characters);

        for (char character : characters) {
            TextView charView = new TextView(this);
            charView.setText(String.valueOf(character));
            charView.setTextSize(20);
            charView.setGravity(Gravity.CENTER);
            charView.setOnClickListener(this::onCharacterClicked);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            charView.setLayoutParams(params);

            charactersGrid.addView(charView);
        }
    }

    public void onCharacterClicked(View view) {
        TextView clickedChar = (TextView) view;
        if (clickedChar.getText().charAt(0) == correctCharacter) {
            score++;
            scoreTextView.setText("Score: " + score);
            clickedChar.setVisibility(View.INVISIBLE);
            correctCharacterCount--;
            checkIfRoundOver();
        } else {
            // Optional: Penalize for wrong clicks
        }
    }

    private void checkIfRoundOver() {
        if (correctCharacterCount == 0) {
            currentRound++;
            new Handler().postDelayed(this::startNewRound, 1000); // 1-second delay
        }
    }

    private void endGame() {
        timer.cancel();
        new AlertDialog.Builder(this)
            .setTitle("Game Over!")
            .setMessage("Your score: " + score)
            .setPositiveButton("OK", (dialog, which) -> finish())
            .setCancelable(false)
            .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
