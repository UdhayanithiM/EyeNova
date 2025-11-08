package com.example.ambieye;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFullName, etUsername, etEmail, etAge, etGender, etPhoneNumber, etAddress, etFatherName, etMotherName;
    private Button btnCancel, btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Profile");
        }

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etFatherName = findViewById(R.id.etFatherName);
        etMotherName = findViewById(R.id.etMotherName);
        btnCancel = findViewById(R.id.btnCancel);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        SignUpActivity.User user = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        if (user != null) {
            etFullName.setText(user.name);
            etUsername.setText(user.name);
            etEmail.setText(user.email);
            etAge.setText(user.age);
            etGender.setText(user.gender);
            // TODO: Add phone number, address, father/mother name to User class
        }

        btnCancel.setOnClickListener(v -> finish());
        btnSaveChanges.setOnClickListener(v -> {
            // TODO: Implement save changes logic
            finish();
        });
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
