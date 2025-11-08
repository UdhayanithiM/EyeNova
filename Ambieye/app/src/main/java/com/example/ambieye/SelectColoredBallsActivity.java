package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectColoredBallsActivity extends AppCompatActivity {

    private TextView scoreTextView, timerTextView, instructionTextView;
    private GridLayout ballsGrid;

    private int score = 0;
    private int currentRound = 0;
    private String[] colorsToFind = {"Red", "Green", "Blue", "Black"};
    private int[] colorResources = {R.drawable.ball_red, R.drawable.ball_green, R.drawable.ball_blue, R.drawable.ball_black};
    private int correctColor;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_colored_balls);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        ballsGrid = findViewById(R.id.ballsGrid);

        startNewRound();
        startTimer();
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
        if (currentRound >= colorsToFind.length) {
            endGame();
            return;
        }

        String colorToFind = colorsToFind[currentRound];
        correctColor = colorResources[currentRound];
        instructionTextView.setText("Select the " + colorToFind + " Balls");

        ballsGrid.removeAllViews();
        generateBalls();
    }

    private void generateBalls() {
        List<Integer> balls = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // Add 5 correct balls
            balls.add(correctColor);
        }
        for (int i = 0; i < 15; i++) { // Add 15 random incorrect balls
            int randomColor;
            do {
                randomColor = colorResources[(int) (Math.random() * colorResources.length)];
            } while (randomColor == correctColor);
            balls.add(randomColor);
        }

        Collections.shuffle(balls);

        for (int ballColor : balls) {
            ImageView ball = new ImageView(this);
            ball.setImageResource(ballColor);
            ball.setOnClickListener(this::onBallClicked);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            ball.setLayoutParams(params);

            ballsGrid.addView(ball);
        }
    }

    public void onBallClicked(View view) {
        ImageView clickedBall = (ImageView) view;

        if (clickedBall.getDrawable().getConstantState() == getResources().getDrawable(correctColor).getConstantState()) {
            score++;
            scoreTextView.setText("Score: " + score);
            clickedBall.setVisibility(View.INVISIBLE);
            checkIfRoundOver();
        }
    }

    private void checkIfRoundOver() {
        boolean allFound = true;
        for (int i = 0; i < ballsGrid.getChildCount(); i++) {
            ImageView ball = (ImageView) ballsGrid.getChildAt(i);
            if (ball.getDrawable().getConstantState() == getResources().getDrawable(correctColor).getConstantState()
                    && ball.getVisibility() == View.VISIBLE) {
                allFound = false;
                break;
            }
        }

        if (allFound) {
            currentRound++;
            new Handler().postDelayed(this::startNewRound, 1000);
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
