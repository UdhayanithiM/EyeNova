package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameIntroActivity extends AppCompatActivity {

    private TextView gameTitle, gameDesc, gamePoints, gameTime;
    private Button startGameBtn;
    private LinearLayout pointsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_intro);

        // Set up the Toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        gameTitle = findViewById(R.id.gameTitle);
        gameDesc = findViewById(R.id.gameDesc);
        gamePoints = findViewById(R.id.gamePoints);
        gameTime = findViewById(R.id.gameTime);
        startGameBtn = findViewById(R.id.startGameBtn);
        pointsLayout = findViewById(R.id.pointsLayout);

        Intent intent = getIntent();
        String title = intent.getStringExtra("GAME_TITLE");
        String desc = intent.getStringExtra("GAME_DESC");
        String points = intent.getStringExtra("GAME_POINTS");
        String time = intent.getStringExtra("GAME_TIME");
        String gameType = intent.getStringExtra("GAME_TYPE");
        final String gameActivityName = intent.getStringExtra("GAME_ACTIVITY");

        gameTitle.setText(title);
        gameDesc.setText(desc);
        gamePoints.setText(points);
        gameTime.setText(time);

        if ("Movement".equals(gameType)) {
            pointsLayout.setVisibility(View.GONE);
        }

        startGameBtn.setOnClickListener(v -> {
            try {
                Class<?> gameActivityClass = Class.forName(gameActivityName);
                Intent gameIntent = new Intent(GameIntroActivity.this, gameActivityClass);
                startActivity(gameIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    // Handle back button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // This is the standard way to handle the back button in the toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
