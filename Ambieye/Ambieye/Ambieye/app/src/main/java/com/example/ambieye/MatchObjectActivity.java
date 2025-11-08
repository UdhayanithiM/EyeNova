package com.example.ambieye;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchObjectActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreTextView, timerTextView, objectTextView;
    private Button choice1, choice2, choice3, choice4;
    private ImageView backButton;

    private int score = 0;
    private int currentRound = 0;
    private List<String> objectList;
    private Map<String, Character> objectMap;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_object);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        objectTextView = findViewById(R.id.objectTextView);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        backButton = findViewById(R.id.backButton);

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
        backButton.setOnClickListener(v -> finish());

        initializeGameData();
        startNewRound();
        startTimer();
    }

    private void initializeGameData() {
        objectMap = new HashMap<>();
        objectMap.put("APPLE", 'A');
        objectMap.put("BALL", 'B');
        objectMap.put("CAT", 'C');
        objectMap.put("DOG", 'D');
        objectMap.put("ELEPHANT", 'E');
        objectList = new ArrayList<>(objectMap.keySet());
        Collections.shuffle(objectList);
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
        if (currentRound >= objectList.size()) {
            endGame();
            return;
        }

        String currentObject = objectList.get(currentRound);
        char correctLetter = objectMap.get(currentObject);
        objectTextView.setText(currentObject);

        List<Character> choices = new ArrayList<>();
        choices.add(correctLetter);

        while (choices.size() < 4) {
            char randomLetter = (char) ('A' + Math.random() * ('Z' - 'A' + 1));
            if (randomLetter != correctLetter && !choices.contains(randomLetter)) {
                choices.add(randomLetter);
            }
        }

        Collections.shuffle(choices);

        choice1.setText(String.valueOf(choices.get(0)));
        choice2.setText(String.valueOf(choices.get(1)));
        choice3.setText(String.valueOf(choices.get(2)));
        choice4.setText(String.valueOf(choices.get(3)));
    }

    @Override
    public void onClick(View v) {
        Button selectedButton = (Button) v;
        char selectedLetter = selectedButton.getText().charAt(0);
        String currentObject = objectList.get(currentRound);
        char correctLetter = objectMap.get(currentObject);

        if (selectedLetter == correctLetter) {
            score++;
            scoreTextView.setText("Score: " + score);
            currentRound++;
            new Handler().postDelayed(this::startNewRound, 500);
        } else {
            // Optional: Penalize for wrong clicks or show feedback
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            currentRound++;
            new Handler().postDelayed(this::startNewRound, 500);
        }
    }

    private void endGame() {
        timer.cancel();
        Toast.makeText(this, "Game Over! Your score: " + score, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(() -> finish(), 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
