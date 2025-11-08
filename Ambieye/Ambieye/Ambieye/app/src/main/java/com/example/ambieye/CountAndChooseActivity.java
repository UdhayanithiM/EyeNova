package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CountAndChooseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreTextView, timerTextView;
    private GridLayout objectsGrid;
    private ImageView backButton;
    private Button choice1, choice2, choice3, choice4;

    private int score = 0;
    private int correctCount;
    private Random random = new Random();

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_and_choose);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        objectsGrid = findViewById(R.id.objectsGrid);
        backButton = findViewById(R.id.backButton);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);
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
        objectsGrid.removeAllViews();
        correctCount = random.nextInt(10) + 5; // Random count between 5 and 14
        generateObjects();

        List<Integer> choices = new ArrayList<>();
        choices.add(correctCount);

        while (choices.size() < 4) {
            int randomChoice = random.nextInt(20) + 1;
            if (!choices.contains(randomChoice)) {
                choices.add(randomChoice);
            }
        }

        Collections.shuffle(choices);

        choice1.setText(String.valueOf(choices.get(0)));
        choice2.setText(String.valueOf(choices.get(1)));
        choice3.setText(String.valueOf(choices.get(2)));
        choice4.setText(String.valueOf(choices.get(3)));
    }

    private void generateObjects() {
        int[] ballDrawables = {R.drawable.ball_red, R.drawable.ball_green, R.drawable.ball_blue, R.drawable.ball_yellow};
        for (int i = 0; i < correctCount; i++) {
            ImageView ball = new ImageView(this);
            ball.setImageResource(ballDrawables[random.nextInt(ballDrawables.length)]);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            ball.setLayoutParams(params);
            objectsGrid.addView(ball);
        }
    }

    @Override
    public void onClick(View v) {
        Button selectedButton = (Button) v;
        int selectedCount = Integer.parseInt(selectedButton.getText().toString());

        if (selectedCount == correctCount) {
            score++;
            scoreTextView.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        startNewRound();
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
