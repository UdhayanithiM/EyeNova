package com.example.ambieye;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FollowBallClockwiseActivity extends AppCompatActivity {

    private TextView timerTextView, scoreTextView;
    private ImageView backButton, ball;
    private FrameLayout animationCanvas;
    private CountDownTimer timer;
    private ValueAnimator animator;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_ball_clockwise);

        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        backButton = findViewById(R.id.backButton);
        ball = findViewById(R.id.ball);
        animationCanvas = findViewById(R.id.animationCanvas);

        backButton.setOnClickListener(v -> onBackPressed());

        startTimer();
        startAnimation();
    }

    private void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
                score++;
                scoreTextView.setText("Score: " + score);
            }

            public void onFinish() {
                timerTextView.setText("Time: 0s");
                if (animator != null) {
                    animator.cancel();
                }
                endGame();
            }
        }.start();
    }

    private void startAnimation() {
        animationCanvas.post(() -> {
            int canvasWidth = animationCanvas.getWidth();
            int canvasHeight = animationCanvas.getHeight();
            int centerX = canvasWidth / 2;
            int centerY = canvasHeight / 2;
            int radius = Math.min(centerX, centerY) - (ball.getWidth() / 2) - 20;

            animator = ValueAnimator.ofFloat(0, (float) (2 * Math.PI));
            animator.setDuration(5000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());

            animator.addUpdateListener(animation -> {
                float angle = (float) animation.getAnimatedValue();
                float x = (float) (centerX + radius * Math.cos(angle)) - (ball.getWidth() / 2);
                float y = (float) (centerY + radius * Math.sin(angle)) - (ball.getHeight() / 2);
                ball.setX(x);
                ball.setY(y);
            });
            animator.start();
        });
    }

    private void endGame() {
        new AlertDialog.Builder(this)
            .setTitle("Game Over!")
            .setMessage("Your score: " + score)
            .setPositiveButton("OK", (dialog, which) -> finish())
            .setCancelable(true)
            .show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
        }
        if (animator != null) {
            animator.cancel();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (animator != null) {
            animator.cancel();
        }
    }
}
