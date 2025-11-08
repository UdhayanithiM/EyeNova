package com.example.ambieye;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        TextView patientNameValue = findViewById(R.id.patientNameValue);
        TextView ageValue = findViewById(R.id.ageValue);
        TextView dobValue = findViewById(R.id.dobValue);
        TextView fatherNameValue = findViewById(R.id.fatherNameValue);
        TextView motherNameValue = findViewById(R.id.motherNameValue);

        SignUpActivity.User user = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        if (user != null) {
            patientNameValue.setText(user.name);
            ageValue.setText(user.age);
            dobValue.setText(user.dob);
            fatherNameValue.setText(user.fatherName);
            motherNameValue.setText(user.motherName);
        }
    }
}
