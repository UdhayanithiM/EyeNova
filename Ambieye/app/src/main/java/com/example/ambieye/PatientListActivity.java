package com.example.ambieye;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends AppCompatActivity {

    private PatientListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        TextView doctorNameLabel = findViewById(R.id.doctorNameLabel);
        String username = getIntent().getStringExtra("USERNAME");
        doctorNameLabel.setText("Dr. " + username);

        RecyclerView patientRecyclerView = findViewById(R.id.patientRecyclerView);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchView patientSearchView = findViewById(R.id.patientSearchView);

        List<SignUpActivity.User> patientList = new ArrayList<>();
        for (SignUpActivity.User user : SignUpActivity.users) {
            if (user.role.equals("Patient")) {
                patientList.add(user);
            }
        }

        adapter = new PatientListAdapter(this, patientList);
        patientRecyclerView.setAdapter(adapter);

        patientSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
