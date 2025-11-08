package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorDashboardActivity extends AppCompatActivity {

    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        settingsBtn = findViewById(R.id.settingsBtn);

        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
