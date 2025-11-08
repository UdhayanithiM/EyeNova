package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

// Import the game activity classes
import com.example.ambieye.SelectColoredBallsActivity;
import com.example.ambieye.SelectAlphabetActivity;
import com.example.ambieye.MatchObjectActivity;
import com.example.ambieye.IdentifySymbolActivity;
import com.example.ambieye.IdentifyColorActivity;
import com.example.ambieye.FollowBallClockwiseActivity;
import com.example.ambieye.FollowBallAntiClockwiseActivity;
import com.example.ambieye.EyeballMovementActivity;
import com.example.ambieye.DirectionOfTheTargetActivity;
import com.example.ambieye.FindTheCharactersActivity;
import com.example.ambieye.CountAndChooseActivity;
import com.example.ambieye.MatchTheFollowingActivity;

public class GamesDashboardActivity extends AppCompatActivity {

    private LinearLayout listContainer;
    private Button tabIdentify, tabMovement, tabCognitive;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_dashboard);

        listContainer = findViewById(R.id.listContainer);
        tabIdentify = findViewById(R.id.tabIdentify);
        tabMovement = findViewById(R.id.tabMovement);
        tabCognitive = findViewById(R.id.tabCognitive);
        backButton = findViewById(R.id.backButton);

        loadIdentifyGames();

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
        tabIdentify.setBackgroundColor(getColor(R.color.light_gray));
        tabIdentify.setTextColor(getColor(R.color.dark_purple));

        tabMovement.setBackgroundColor(getColor(R.color.light_gray));
        tabMovement.setTextColor(getColor(R.color.dark_purple));

        tabCognitive.setBackgroundColor(getColor(R.color.light_gray));
        tabCognitive.setTextColor(getColor(R.color.dark_purple));

        activeTab.setBackgroundColor(getColor(R.color.dark_purple));
        activeTab.setTextColor(getColor(android.R.color.white));
    }

    private void addGameCard(String title, String desc, String gameType, final Class<?> activityClass) {
        CardView card = new CardView(this);
        card.setCardElevation(4);
        card.setRadius(16);
        card.setUseCompatPadding(true);
        card.setCardBackgroundColor(getColor(android.R.color.white));
        card.setContentPadding(20, 20, 20, 20);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView titleView = new TextView(this);
        titleView.setText(title);
        titleView.setTextSize(16);
        titleView.setTextColor(getColor(R.color.dark_purple));
        titleView.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView descView = new TextView(this);
        descView.setText(desc);
        descView.setTextSize(14);
        descView.setTextColor(getColor(R.color.gray));

        layout.addView(titleView);
        layout.addView(descView);

        card.addView(layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 10);
        card.setLayoutParams(params);
        
        card.setOnClickListener(v -> {
            if (activityClass != null) {
                Intent intent = new Intent(GamesDashboardActivity.this, GameIntroActivity.class);
                intent.putExtra("GAME_TITLE", title);
                intent.putExtra("GAME_DESC", desc);
                intent.putExtra("GAME_POINTS", "10 Points");
                intent.putExtra("GAME_TIME", "30 Sec");
                intent.putExtra("GAME_TYPE", gameType);
                intent.putExtra("GAME_ACTIVITY", activityClass.getName());
                startActivity(intent);
            }
        });

        listContainer.addView(card);
    }

    private void loadIdentifyGames() {
        listContainer.removeAllViews();
        addGameCard("Select the colored balls", "Identify and select balls based on their specific colors.", "Identify", SelectColoredBallsActivity.class);
        addGameCard("Select the alphabet", "Practice letter recognition by selecting the correct alphabets.", "Identify", SelectAlphabetActivity.class);
        addGameCard("Select the correct object for the alphabets", "Match objects with their corresponding starting letters.", "Identify", MatchObjectActivity.class);
        addGameCard("Identify the symbol", "Recognize and select various symbols shown on screen.", "Identify", IdentifySymbolActivity.class);
        addGameCard("Identify the color of the object", "Name the correct color of different displayed objects.", "Identify", IdentifyColorActivity.class);
    }

    private void loadMovementGames() {
        listContainer.removeAllViews();
        addGameCard("Follow the ball in clockwise direction", "Track a moving ball with your eyes in clockwise pattern.", "Movement", FollowBallClockwiseActivity.class);
        addGameCard("Follow the ball in anti-clockwise direction", "Track a moving ball in counter-clockwise pattern.", "Movement", FollowBallAntiClockwiseActivity.class);
        addGameCard("Eyeball movement", "Exercise your eye muscles with guided movement patterns.", "Movement", EyeballMovementActivity.class);
        addGameCard("Direction of the target", "Identify which direction targets are moving across the screen.", "Movement", DirectionOfTheTargetActivity.class);
    }

    private void loadCognitiveGames() {
        listContainer.removeAllViews();
        addGameCard("Find the characters", "Locate specific characters hidden within a complex display.", "Cognitive", FindTheCharactersActivity.class);
        addGameCard("Count and choose", "Count the number of objects and select the correct answer.", "Cognitive", CountAndChooseActivity.class);
        addGameCard("Match the following", "Connect related items by finding their corresponding pairs.", "Cognitive", MatchTheFollowingActivity.class);
    }
}
