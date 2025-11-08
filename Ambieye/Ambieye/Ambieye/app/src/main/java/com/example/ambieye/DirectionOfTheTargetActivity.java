package com.example.ambieye;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class DirectionOfTheTargetActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreTextView, timerTextView;
    private ImageView backButton, ball;
    private FrameLayout animationCanvas;
    private Button upButton, downButton, leftButton, rightButton;

    private int score = 0;
    private int currentDirection;
    private Random random = new Random();

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_of_the_target);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        backButton = findViewById(R.id.backButton);
        ball = findViewById(R.id.ball);
        animationCanvas = findViewById(R.id.animationCanvas);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
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
        ball.setVisibility(View.VISIBLE);
        animationCanvas.post(() -> {
            int canvasWidth = animationCanvas.getWidth();
            int canvasHeight = animationCanvas.getHeight();
            ball.setX(canvasWidth / 2 - ball.getWidth() / 2);
            ball.setY(canvasHeight / 2 - ball.getHeight() / 2);

            currentDirection = random.nextInt(4); // 0: up, 1: down, 2: left, 3: right
            ObjectAnimator animator;
            switch (currentDirection) {
                case 0: // Up
                    animator = ObjectAnimator.ofFloat(ball, "y", ball.getY(), 0);
                    break;
                case 1: // Down
                    animator = ObjectAnimator.ofFloat(ball, "y", ball.getY(), canvasHeight - ball.getHeight());
                    break;
                case 2: // Left
                    animator = ObjectAnimator.ofFloat(ball, "x", ball.getX(), 0);
                    break;
                default: // Right
                    animator = ObjectAnimator.ofFloat(ball, "x", ball.getX(), canvasWidth - ball.getWidth());
                    break;
            }
            animator.setDuration(1000);
            animator.start();

            new Handler().postDelayed(() -> ball.setVisibility(View.INVISIBLE), 1000);
        });
    }

    @Override
    public void onClick(View v) {
        int selectedDirection = -1;
        int vId = v.getId();
        if (vId == R.id.upButton) {
            selectedDirection = 0;
        } else if (vId == R.id.downButton) {
            selectedDirection = 1;
        } else if (vId == R.id.leftButton) {
            selectedDirection = 2;
        } else if (vId == R.id.rightButton) {
            selectedDirection = 3;
        }

        if (selectedDirection == currentDirection) {
            score++;
            scoreTextView.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        startNewRound();
    }

    private void endGame() {
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
