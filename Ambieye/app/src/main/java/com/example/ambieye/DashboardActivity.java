package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private CardView cardActivities, cardProgress, cardReminders, cardReview, cardQueries, cardHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        cardActivities = findViewById(R.id.cardActivities);
        cardProgress = findViewById(R.id.cardProgress);
        cardReminders = findViewById(R.id.cardReminders);
        cardReview = findViewById(R.id.cardReview);
        cardQueries = findViewById(R.id.cardQueries);
        cardHistory = findViewById(R.id.cardHistory);

        cardActivities.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, GamesDashboardActivity.class);
            startActivity(intent);
        });

        cardProgress.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProgressActivity.class);
            startActivity(intent);
        });

        cardReminders.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

        cardReview.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ReviewDatesActivity.class);
            startActivity(intent);
        });

        cardQueries.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, QueriesActivity.class);
            startActivity(intent);
        });

        cardHistory.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }
}
