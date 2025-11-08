package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    public static List<User> users = new ArrayList<>();

    private EditText nameInput, emailInput, passwordInput;
    private Button nextBtn, backBtn;
    private TextView loginText;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginText = findViewById(R.id.loginText);
        roleSpinner = findViewById(R.id.roleSpinner);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setPopupBackgroundResource(R.drawable.bg_spinner_popup);

        nextBtn.setOnClickListener(v -> {
            if (validateInput()) {
                Intent intent = new Intent(SignUpActivity.this, PersonalDetailsActivity.class);
                User user = new User(nameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), roleSpinner.getSelectedItem().toString());
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(v -> finish());

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInput() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (User user : users) {
            if (user.email.equalsIgnoreCase(email)) {
                Toast.makeText(this, "User with this email already exists", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    public static class User implements Serializable {
        String name, email, password, role, doctorId;
        String age, dob, gender, fatherName, motherName, address;
        String securityQuestion1, securityAnswer1, securityQuestion2, securityAnswer2;

        public User(String name, String email, String password, String role) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public User(String name, String email, String password, String role, String securityQuestion1, String securityAnswer1, String securityQuestion2, String securityAnswer2, String doctorId) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
            this.securityQuestion1 = securityQuestion1;
            this.securityAnswer1 = securityAnswer1;
            this.securityQuestion2 = securityQuestion2;
            this.securityAnswer2 = securityAnswer2;
            this.doctorId = doctorId;
        }
    }
}
