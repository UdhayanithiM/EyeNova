package com.example.ambieye;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Game History");
        }

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        historyItemList = new ArrayList<>();
        historyItemList.add(new HistoryItem("10", "Fri, Oct", "Select the colored balls", "1.0 min", "86", 100));
        historyItemList.add(new HistoryItem("29", "Wed, Oct", "Follow the ball in anti-clockwise direction", "29362416.8 min", "100", 100));
        historyItemList.add(new HistoryItem("29", "Wed, Oct", "Follow the ball in clockwise direction", "29362416.6 min", "100", 100));
        historyItemList.add(new HistoryItem("28", "Tue, Oct", "Select the alphabet", "0.5 min", "95", 95));

        adapter = new HistoryAdapter(historyItemList);
        historyRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
