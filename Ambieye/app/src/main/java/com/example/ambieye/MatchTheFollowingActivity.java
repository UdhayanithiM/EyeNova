package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchTheFollowingActivity extends AppCompatActivity {

    private TextView scoreTextView, timerTextView;
    private LinearLayout column1, column2;

    private int score = 0;
    private Button selectedButton = null;
    private int matchedPairs = 0;

    private Map<String, String> pairs;
    private List<String> column1Items;
    private List<String> column2Items;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_the_following);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Match the Following");
        }

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        column1 = findViewById(R.id.column1);
        column2 = findViewById(R.id.column2);

        initializeGameData();
        startNewRound();
        startTimer();
    }

    private void initializeGameData() {
        pairs = new HashMap<>();
        pairs.put("5", "5");
        pairs.put("B", "B");
        pairs.put("Cat", "Cat");
        pairs.put("Sun", "Sun");
        pairs.put("Apple", "Apple");

    }

    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) {
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
        column1.removeAllViews();
        column2.removeAllViews();
        selectedButton = null;
        matchedPairs = 0;

        column1Items = new ArrayList<>(pairs.keySet());
        column2Items = new ArrayList<>(pairs.values());

        Collections.shuffle(column1Items);
        Collections.shuffle(column2Items);

        for (String item : column1Items) {
            addButtonToLayout(column1, item);
        }
        for (String item : column2Items) {
            addButtonToLayout(column2, item);
        }
    }

    private void addButtonToLayout(LinearLayout layout, String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(this::onButtonClick);
        layout.addView(button);
    }

    public void onButtonClick(View view) {
        Button clickedButton = (Button) view;
        if (selectedButton == null) {
            selectedButton = clickedButton;
            selectedButton.setEnabled(false); // Disable the first selected button
        } else {
            String selectedText1 = selectedButton.getText().toString();
            String selectedText2 = clickedButton.getText().toString();

            if (pairs.get(selectedText1) != null && pairs.get(selectedText1).equals(selectedText2)) {
                score++;
                matchedPairs++;
                scoreTextView.setText("Score: " + score);
                selectedButton.setVisibility(View.INVISIBLE);
                clickedButton.setVisibility(View.INVISIBLE);
                selectedButton = null;

                if (matchedPairs == pairs.size()) {
                    new Handler().postDelayed(this::startNewRound, 1000);
                }
            } else {
                Toast.makeText(this, "Wrong Match!", Toast.LENGTH_SHORT).show();
                selectedButton.setEnabled(true); // Re-enable the first button on wrong match
                selectedButton = null;
            }
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
