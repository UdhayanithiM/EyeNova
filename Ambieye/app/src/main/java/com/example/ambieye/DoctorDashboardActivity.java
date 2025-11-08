package com.example.ambieye;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorDashboardActivity extends AppCompatActivity {

    public static Map<String, List<ChatMessage>> conversations = new HashMap<>();
    private RecentQueriesAdapter recentQueriesAdapter;
    private List<Query> queryList = new ArrayList<>();
    private SignUpActivity.User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        currentUser = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        TextView doctorName = findViewById(R.id.doctorName);
        doctorName.setText("Dr. " + currentUser.name);

        int patientCount = 0;
        List<SignUpActivity.User> patientList = new ArrayList<>();
        for (SignUpActivity.User user : SignUpActivity.users) {
            if (user.role.equals("Patient")) {
                patientList.add(user);
                patientCount++;
            }
        }
        TextView patientsCount = findViewById(R.id.patientsCount);
        patientsCount.setText(String.valueOf(patientCount));

        RecyclerView recentPatientsRecyclerView = findViewById(R.id.recentPatientsRecyclerView);
        LinearLayout noPatientsLayout = findViewById(R.id.noPatientsLayout);

        if (patientList.isEmpty()) {
            recentPatientsRecyclerView.setVisibility(View.GONE);
            noPatientsLayout.setVisibility(View.VISIBLE);
        } else {
            recentPatientsRecyclerView.setVisibility(View.VISIBLE);
            noPatientsLayout.setVisibility(View.GONE);
            RecentPatientAdapter recentPatientAdapter = new RecentPatientAdapter(this, patientList, currentUser.email);
            recentPatientsRecyclerView.setAdapter(recentPatientAdapter);
        }

        RecyclerView recentQueriesRecyclerView = findViewById(R.id.recentQueriesRecyclerView);
        recentQueriesAdapter = new RecentQueriesAdapter(this, queryList, currentUser.email);
        recentQueriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentQueriesRecyclerView.setAdapter(recentQueriesAdapter);

        CardView patientsCard = findViewById(R.id.patientsCard);
        CardView queriesCard = findViewById(R.id.queriesCard);

        if (patientsCard != null) animateCard(patientsCard, 0);
        if (queriesCard != null) animateCard(queriesCard, 100);

        patientsCard.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, PatientListActivity.class);
            intent.putExtra("USER", currentUser);
            startActivity(intent);
        });

        TextView seeAllPatients = findViewById(R.id.seeAllPatients);
        seeAllPatients.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, PatientListActivity.class);
            intent.putExtra("USER", currentUser);
            startActivity(intent);
        });

        TextView seeAllQueries = findViewById(R.id.seeAllQueries);
        seeAllQueries.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, QueriesActivity.class);
            intent.putExtra("USER", currentUser);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                return true;
            } else if (itemId == R.id.navigation_patients) {
                Intent intent = new Intent(DoctorDashboardActivity.this, PatientListActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.navigation_queries) {
                Intent intent = new Intent(DoctorDashboardActivity.this, QueriesActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.navigation_settings) {
                Intent intent = new Intent(DoctorDashboardActivity.this, SettingsActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecentQueries();
    }

    private void updateRecentQueries(){
        queryList.clear();
        for (Map.Entry<String, List<ChatMessage>> entry : conversations.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                queryList.add(new Query(entry.getKey(), entry.getValue().get(entry.getValue().size() - 1).message));
            }
        }
        TextView queriesCount = findViewById(R.id.queriesCount);
        if (queriesCount != null) queriesCount.setText(String.valueOf(queryList.size()));
        if(recentQueriesAdapter != null) recentQueriesAdapter.notifyDataSetChanged();
    }

    private void animateCard(View view, long startDelay) {
        view.setAlpha(0f);
        view.setTranslationY(50);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alphaAnimator.setDuration(500);

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(view, "translationY", 50, 0);
        translationAnimator.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
    }
}
