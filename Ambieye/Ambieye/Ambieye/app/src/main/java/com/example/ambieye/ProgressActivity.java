package com.example.ambieye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProgressActivity extends AppCompatActivity {

    private LinearLayout layoutTodayGames;
    private TextView tvDate, tvGamesPlayed, tvAvgScore, tvAvgAccuracy, tvOverallProgress;
    private ProgressBar progressOverall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Set up the Toolbar for back navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Progress Summary");
        }

        tvDate = findViewById(R.id.tvDate);
        tvGamesPlayed = findViewById(R.id.tvGamesPlayed);
        tvAvgScore = findViewById(R.id.tvAvgScore);
        tvAvgAccuracy = findViewById(R.id.tvAvgAccuracy);
        tvOverallProgress = findViewById(R.id.tvOverallProgress);
        progressOverall = findViewById(R.id.progressOverall);
        layoutTodayGames = findViewById(R.id.layoutTodayGames);

        // Set today's date
        String currentDate = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date());
        tvDate.setText(currentDate);

        // Example data
        int gamesPlayed = 3;
        int avgScore = 74;
        int avgAccuracy = 74;
        int completed = 3;
        int totalGames = 12;

        tvGamesPlayed.setText(String.valueOf(gamesPlayed));
        tvAvgScore.setText(String.valueOf(avgScore));
        tvAvgAccuracy.setText(avgAccuracy + "%");
        tvOverallProgress.setText(completed + " of " + totalGames + " games completed (" + (completed * 100 / totalGames) + "%)");
        progressOverall.setProgress(completed * 100 / totalGames);

        addGame("Follow the ball in anti-clockwise direction", 100, 100, "1.0 min");
        addGame("Follow the ball in clockwise direction", 100, 100, "1.0 min");
        addGame("Direction of the target", 22, 100, "0.3 min");
    }

    private void addGame(String name, int score, int accuracy, String time) {
        View gameView = LayoutInflater.from(this).inflate(R.layout.item_game_progress, layoutTodayGames, false);

        TextView tvGameName = gameView.findViewById(R.id.tvGameName);
        TextView tvScore = gameView.findViewById(R.id.tvScore);
        TextView tvTime = gameView.findViewById(R.id.tvTime);
        TextView tvAccuracy = gameView.findViewById(R.id.tvAccuracy);
        ProgressBar progressBar = gameView.findViewById(R.id.progressBarGame);

        tvGameName.setText(name);
        tvScore.setText("Score: " + score);
        tvTime.setText(time);
        tvAccuracy.setText(accuracy + "%");
        progressBar.setProgress(accuracy);

        layoutTodayGames.addView(gameView);
    }

    // Handle the back button click in the Toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
