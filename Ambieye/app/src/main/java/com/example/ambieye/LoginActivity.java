package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Spinner roleSpinner;
    private EditText emailInput, passwordInput;
    private Button loginBtn;
    private TextView forgotPassword, signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Add test users if the list is empty
        if (SignUpActivity.users.isEmpty()) {
            // Patient test user
            SignUpActivity.User patientUser = new SignUpActivity.User("Patient User", "patient@test.com", "admin", "Patient");
            SignUpActivity.users.add(patientUser);

            // Doctor test user
            SignUpActivity.User doctorUser = new SignUpActivity.User("Doctor User", "doctor@test.com", "admin", "Doctor");
            doctorUser.doctorId = "12345";
            SignUpActivity.users.add(doctorUser);
        }

        roleSpinner = findViewById(R.id.roleSpinner);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        signupText = findViewById(R.id.signupText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setPopupBackgroundResource(R.drawable.bg_spinner_popup);

        loginBtn.setOnClickListener(v -> loginUser());

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        for (SignUpActivity.User user : SignUpActivity.users) {
            if (user.email.equalsIgnoreCase(email) && user.password.equals(password) && user.role.equals(role)) {
                if (role.equals("Patient")) {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                    finish();
                    return;
                } else if (role.equals("Doctor")) {
                    Intent intent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        }

        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
    }
}
