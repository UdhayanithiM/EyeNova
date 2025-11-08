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

public class IdentifyColorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreTextView, timerTextView;
    private ImageView objectImageView, backButton;
    private Button choice1, choice2, choice3, choice4;

    private int score = 0;
    private int currentRound = 0;
    private List<String> colorNameList;
    private Map<String, Integer> colorMap;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_color);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        objectImageView = findViewById(R.id.objectImageView);
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
        colorMap = new HashMap<>();
        colorMap.put("Red", R.drawable.ball_red);
        colorMap.put("Green", R.drawable.ball_green);
        colorMap.put("Blue", R.drawable.ball_blue);
        colorMap.put("Yellow", R.drawable.ball_yellow);
        colorNameList = new ArrayList<>(colorMap.keySet());
        Collections.shuffle(colorNameList);
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
        if (currentRound >= colorNameList.size()) {
            endGame();
            return;
        }

        String currentColorName = colorNameList.get(currentRound);
        int currentColorResId = colorMap.get(currentColorName);
        objectImageView.setImageResource(currentColorResId);

        List<String> choices = new ArrayList<>();
        choices.add(currentColorName);

        while (choices.size() < 4) {
            int randomIndex = (int) (Math.random() * colorNameList.size());
            String randomColorName = colorNameList.get(randomIndex);
            if (!choices.contains(randomColorName)) {
                choices.add(randomColorName);
            }
        }

        Collections.shuffle(choices);

        choice1.setText(choices.get(0));
        choice2.setText(choices.get(1));
        choice3.setText(choices.get(2));
        choice4.setText(choices.get(3));
    }

    @Override
    public void onClick(View v) {
        Button selectedButton = (Button) v;
        String selectedColorName = selectedButton.getText().toString();
        String correctColorName = colorNameList.get(currentRound);

        if (selectedColorName.equals(correctColorName)) {
            score++;
            scoreTextView.setText("Score: " + score);
            currentRound++;
            new Handler().postDelayed(this::startNewRound, 500);
        } else {
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
