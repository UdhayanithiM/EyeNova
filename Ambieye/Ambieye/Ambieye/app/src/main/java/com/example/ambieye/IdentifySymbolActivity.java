package com.example.ambieye;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IdentifySymbolActivity extends AppCompatActivity {

    private GridLayout gridSymbols;
    private TextView tvScore, tvRound, tvTask;
    private ImageView targetSymbol;
    private int score = 0, round = 1;
    private final int totalRounds = 10;
    private int targetSymbolRes;
    private int correctSymbolsInGrid = 0;
    private Random random = new Random();
    private int[] symbolIcons = {
            R.drawable.ic_music_note,
            R.drawable.ic_heart,
            R.drawable.ic_star,
            R.drawable.ic_square,
            R.drawable.ic_circle,
            R.drawable.ic_bell,
            R.drawable.ic_plus,
            R.drawable.ic_minus,
            R.drawable.ic_diamond,
            R.drawable.ic_question
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_symbol);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Identify the Symbol");
        }

        gridSymbols = findViewById(R.id.gridSymbols);
        tvScore = findViewById(R.id.tvScore);
        tvRound = findViewById(R.id.tvRound);
        tvTask = findViewById(R.id.tvTask);
        targetSymbol = findViewById(R.id.targetSymbol);

        loadNewRound();
    }

    private void loadNewRound() {
        if (round > totalRounds) {
            showGameOverDialog(true);
            return;
        }

        gridSymbols.removeAllViews();
        correctSymbolsInGrid = 0;

        // Pick random target symbol
        int randomIndex = random.nextInt(symbolIcons.length);
        targetSymbolRes = symbolIcons[randomIndex];

        tvTask.setText("Find all " + getSymbolName(targetSymbolRes) + " symbols");
        tvRound.setText("Round: " + round + "/" + totalRounds);
        targetSymbol.setImageResource(targetSymbolRes);

        List<Integer> gridItems = new ArrayList<>();
        int correctCount = random.nextInt(3) + 3; // 3 to 5 correct symbols
        for (int i = 0; i < correctCount; i++) {
            gridItems.add(targetSymbolRes);
        }
        correctSymbolsInGrid = correctCount;

        List<Integer> distractors = new ArrayList<>();
        for (int icon : symbolIcons) {
            if (icon != targetSymbolRes) {
                distractors.add(icon);
            }
        }

        for (int i = correctCount; i < 25; i++) {
            gridItems.add(distractors.get(random.nextInt(distractors.size())));
        }

        Collections.shuffle(gridItems);

        for (final int res : gridItems) {
            ImageView icon = new ImageView(this);
            icon.setImageResource(res);
            icon.setTag(res); // Use a tag to store the correct resource ID
            icon.setPadding(12, 12, 12, 12);
            icon.setBackgroundResource(R.drawable.circle_background);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.setMargins(8, 8, 8, 8);
            icon.setLayoutParams(params);

            icon.setOnClickListener(this::checkSymbol);
            gridSymbols.addView(icon);
        }
    }

    private void checkSymbol(View v) {
        ImageView icon = (ImageView) v;
        int clickedRes = (int) icon.getTag();

        if (clickedRes == targetSymbolRes) {
            score += 10;
            icon.setAlpha(0.3f);
            icon.setOnClickListener(null); // Disable further clicks on this correct icon
            correctSymbolsInGrid--;

            if (correctSymbolsInGrid == 0) {
                round++;
                Toast.makeText(this, "Round Cleared!", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(this::loadNewRound, 1000); // Wait 1 sec before next round
            }
        } else {
            score -= 5;
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        tvScore.setText("Score: " + score);
    }

    private void showGameOverDialog(boolean allRoundsCompleted) {
        String title = allRoundsCompleted ? "Congratulations!" : "Game Over";
        String message = allRoundsCompleted ? "You completed all rounds!\nFinal score: " + score : "Your final score: " + score;

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Play Again", (d, i) -> {
                    score = 0;
                    round = 1;
                    loadNewRound();
                })
                .setNegativeButton("Exit", (d, i) -> finish())
                .setCancelable(false)
                .show();
    }

    private String getSymbolName(int resId) {
        if (resId == R.drawable.ic_music_note) return "music";
        if (resId == R.drawable.ic_heart) return "heart";
        if (resId == R.drawable.ic_star) return "star";
        if (resId == R.drawable.ic_bell) return "bell";
        if (resId == R.drawable.ic_circle) return "circle";
        if (resId == R.drawable.ic_diamond) return "diamond";
        if (resId == R.drawable.ic_plus) return "plus";
        if (resId == R.drawable.ic_minus) return "minus";
        if (resId == R.drawable.ic_square) return "square";
        if (resId == R.drawable.ic_question) return "question";
        return "random";
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
