package com.example.ambieye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GamesDashboardActivity extends AppCompatActivity {

    private RecyclerView gamesRecyclerView;
    private GameAdapter gameAdapter;
    private List<Game> gameList = new ArrayList<>();
    private Button tabIdentify, tabMovement, tabCognitive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_dashboard);

        gamesRecyclerView = findViewById(R.id.gamesRecyclerView);
        tabIdentify = findViewById(R.id.tabIdentify);
        tabMovement = findViewById(R.id.tabMovement);
        tabCognitive = findViewById(R.id.tabCognitive);
        ImageButton backButton = findViewById(R.id.backButton);

        gameAdapter = new GameAdapter(this, gameList);
        gamesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        gamesRecyclerView.setAdapter(gameAdapter);

        // Load initial games and highlight the first tab
        loadIdentifyGames();
        highlightTab(tabIdentify);

        backButton.setOnClickListener(v -> finish());

        tabIdentify.setOnClickListener(v -> {
            highlightTab(tabIdentify);
            loadIdentifyGames();
        });

        tabMovement.setOnClickListener(v -> {
            highlightTab(tabMovement);
            loadMovementGames();
        });

        tabCognitive.setOnClickListener(v -> {
            highlightTab(tabCognitive);
            loadCognitiveGames();
        });
    }

    private void highlightTab(Button activeTab) {
        // Reset all tabs to inactive state
        tabIdentify.setBackgroundResource(R.drawable.bg_tab_inactive);
        tabIdentify.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));

        tabMovement.setBackgroundResource(R.drawable.bg_tab_inactive);
        tabMovement.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));

        tabCognitive.setBackgroundResource(R.drawable.bg_tab_inactive);
        tabCognitive.setTextColor(ContextCompat.getColor(this, R.color.dark_purple));

        // Set the active tab
        activeTab.setBackgroundResource(R.drawable.bg_tab_active);
        activeTab.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        // Animation temporarily removed to fix crash
        gameAdapter.notifyDataSetChanged();
    }

    private void loadIdentifyGames() {
        gameList.clear();
        gameList.add(new Game("Select the colored balls", "Identify and select balls based on their specific colors.", "Identify", SelectColoredBallsActivity.class));
        gameList.add(new Game("Select the alphabet", "Practice letter recognition by selecting the correct alphabets.", "Identify", SelectAlphabetActivity.class));
        gameList.add(new Game("Select the correct object for the alphabets", "Match objects with their corresponding starting letters.", "Identify", MatchObjectActivity.class));
        gameList.add(new Game("Identify the symbol", "Recognize and select various symbols shown on screen.", "Identify", IdentifySymbolActivity.class));
        gameList.add(new Game("Identify the color of the object", "Name the correct color of different displayed objects.", "Identify", IdentifyColorActivity.class));
        runLayoutAnimation(gamesRecyclerView);
    }

    private void loadMovementGames() {
        gameList.clear();
        gameList.add(new Game("Follow the ball in clockwise direction", "Track a moving ball with your eyes in clockwise pattern.", "Movement", FollowBallClockwiseActivity.class));
        gameList.add(new Game("Follow the ball in anti-clockwise direction", "Track a moving ball in counter-clockwise pattern.", "Movement", FollowBallAntiClockwiseActivity.class));
        gameList.add(new Game("Eyeball movement", "Exercise your eye muscles with guided movement patterns.", "Movement", EyeballMovementActivity.class));
        gameList.add(new Game("Direction of the target", "Identify which direction targets are moving across the screen.", "Movement", DirectionOfTheTargetActivity.class));
        runLayoutAnimation(gamesRecyclerView);
    }

    private void loadCognitiveGames() {
        gameList.clear();
        gameList.add(new Game("Find the characters", "Locate specific characters hidden within a complex display.", "Cognitive", FindTheCharactersActivity.class));
        gameList.add(new Game("Count and choose", "Count the number of objects and select the correct answer.", "Cognitive", CountAndChooseActivity.class));
        gameList.add(new Game("Match the following", "Connect related items by finding their corresponding pairs.", "Cognitive", MatchTheFollowingActivity.class));
        runLayoutAnimation(gamesRecyclerView);
    }
}
