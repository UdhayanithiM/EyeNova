package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText nameInput, emailInput;
    private SignUpActivity.User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        Button saveBtn = findViewById(R.id.saveBtn);

        currentUser = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        if (currentUser != null) {
            nameInput.setText(currentUser.name);
            emailInput.setText(currentUser.email);
        }

        saveBtn.setOnClickListener(v -> {
            String newName = nameInput.getText().toString().trim();
            String newEmail = emailInput.getText().toString().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // In a real app, you would have a more robust way of finding and updating the user
            for (SignUpActivity.User user : SignUpActivity.users) {
                if (user.email.equals(currentUser.email)) {
                    user.name = newName;
                    user.email = newEmail;
                    break;
                }
            }

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
