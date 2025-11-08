package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PersonalDetailsActivity extends AppCompatActivity {

    private EditText ageInput, dobInput, genderInput, fatherNameInput, motherNameInput, addressInput, securityAnswer1Input, securityAnswer2Input;
    private Spinner securityQuestion1Spinner, securityQuestion2Spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        ageInput = findViewById(R.id.ageInput);
        dobInput = findViewById(R.id.dobInput);
        genderInput = findViewById(R.id.genderInput);
        fatherNameInput = findViewById(R.id.fatherNameInput);
        motherNameInput = findViewById(R.id.motherNameInput);
        addressInput = findViewById(R.id.addressInput);
        securityQuestion1Spinner = findViewById(R.id.securityQuestion1Spinner);
        securityAnswer1Input = findViewById(R.id.securityAnswer1Input);
        securityQuestion2Spinner = findViewById(R.id.securityQuestion2Spinner);
        securityAnswer2Input = findViewById(R.id.securityAnswer2Input);

        Button backBtn = findViewById(R.id.backBtn);
        Button submitBtn = findViewById(R.id.submitBtn);

        SignUpActivity.User user = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.security_questions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        securityQuestion1Spinner.setAdapter(adapter);
        securityQuestion2Spinner.setAdapter(adapter);

        backBtn.setOnClickListener(v -> finish());

        submitBtn.setOnClickListener(v -> {
            user.age = ageInput.getText().toString();
            user.dob = dobInput.getText().toString();
            user.gender = genderInput.getText().toString();
            user.fatherName = fatherNameInput.getText().toString();
            user.motherName = motherNameInput.getText().toString();
            user.address = addressInput.getText().toString();
            user.securityQuestion1 = securityQuestion1Spinner.getSelectedItem().toString();
            user.securityAnswer1 = securityAnswer1Input.getText().toString().trim();
            user.securityQuestion2 = securityQuestion2Spinner.getSelectedItem().toString();
            user.securityAnswer2 = securityAnswer2Input.getText().toString().trim();

            if (user.securityAnswer1.isEmpty() || user.securityAnswer2.isEmpty()) {
                Toast.makeText(this, "Please answer both security questions", Toast.LENGTH_SHORT).show();
                return;
            }
            
            if (user.securityQuestion1.equals(user.securityQuestion2)) {
                Toast.makeText(this, "Please select two different security questions", Toast.LENGTH_SHORT).show();
                return;
            }

            if ("Doctor".equals(user.role)) {
                user.doctorId = String.valueOf(new Random().nextInt(90000) + 10000);
            }

            SignUpActivity.users.add(user);

            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
            Intent loginIntent = new Intent(PersonalDetailsActivity.this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        });
    }
}
