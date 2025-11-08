package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectAlphabetActivity extends AppCompatActivity {

    private TextView scoreTextView, timerTextView, instructionTextView;
    private GridLayout lettersGrid;

    private int score = 0;
    private int currentRound = 0;
    private char[] lettersToFind = {'A', 'B', 'C', 'D', 'E'};
    private char correctLetter;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_alphabet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        lettersGrid = findViewById(R.id.lettersGrid);

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
        if (currentRound >= lettersToFind.length) {
            endGame();
            return;
        }

        correctLetter = lettersToFind[currentRound];
        instructionTextView.setText("Select the Letter " + correctLetter);

        lettersGrid.removeAllViews();
        generateLetters();
    }

    private void generateLetters() {
        List<Character> letters = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Add 5 correct letters
            letters.add(correctLetter);
        }
        for (int i = 0; i < 20; i++) { // Add 20 random incorrect letters
            char randomLetter;
            do {
                randomLetter = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
            } while (randomLetter == correctLetter);
            letters.add(randomLetter);
        }

        Collections.shuffle(letters);

        for (char letter : letters) {
            TextView letterView = new TextView(this);
            letterView.setText(String.valueOf(letter));
            letterView.setTextSize(24);
            letterView.setGravity(Gravity.CENTER);
            letterView.setOnClickListener(this::onLetterClicked);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            letterView.setLayoutParams(params);

            lettersGrid.addView(letterView);
        }
    }

    public void onLetterClicked(View view) {
        TextView clickedLetter = (TextView) view;
        if (clickedLetter.getText().charAt(0) == correctLetter) {
            score++;
            scoreTextView.setText("Score: " + score);
            clickedLetter.setVisibility(View.INVISIBLE);
            checkIfRoundOver();
        } else {
            // Optional: Penalize for wrong clicks
        }
    }

    private void checkIfRoundOver() {
        boolean allFound = true;
        for (int i = 0; i < lettersGrid.getChildCount(); i++) {
            TextView letterView = (TextView) lettersGrid.getChildAt(i);
            if (letterView.getText().charAt(0) == correctLetter && letterView.getVisibility() == View.VISIBLE) {
                allFound = false;
                break;
            }
        }

        if (allFound) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
