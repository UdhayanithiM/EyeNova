package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView usernameText;
    private ImageButton settingsBtn;
    private LinearLayout cardActivities, cardProgress, cardReminders, cardReview, cardQueries, cardHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        usernameText = findViewById(R.id.usernameText);
        settingsBtn = findViewById(R.id.settingsBtn);

        cardActivities = findViewById(R.id.cardActivities);
        cardProgress = findViewById(R.id.cardProgress);
        cardReminders = findViewById(R.id.cardReminders);
        cardReview = findViewById(R.id.cardReview);
        cardQueries = findViewById(R.id.cardQueries);
        cardHistory = findViewById(R.id.cardHistory);

        usernameText.setText("usertester");

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        cardActivities.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, GamesDashboardActivity.class);
            startActivity(intent);
        });

        cardProgress.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProgressActivity.class);
            startActivity(intent);
        });

        cardReminders.setOnClickListener(v ->
                Toast.makeText(this, "Opening Reminders", Toast.LENGTH_SHORT).show()
        );
        cardReview.setOnClickListener(v ->
                Toast.makeText(this, "Opening Review Dates", Toast.LENGTH_SHORT).show()
        );
        cardQueries.setOnClickListener(v ->
                Toast.makeText(this, "Opening Queries", Toast.LENGTH_SHORT).show()
        );
        cardHistory.setOnClickListener(v ->
                Toast.makeText(this, "Opening History", Toast.LENGTH_SHORT).show()
        );
    }
}
