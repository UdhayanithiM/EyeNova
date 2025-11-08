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

        roleSpinner = findViewById(R.id.roleSpinner);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        signupText = findViewById(R.id.signupText);

        // Set up the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        loginBtn.setOnClickListener(v -> {
            String selectedRole = roleSpinner.getSelectedItem().toString();

            if (selectedRole.equals("Patient")) {
                // Navigate to DashboardActivity
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            } else if (selectedRole.equals("Doctor")) {
                // Navigate to DoctorDashboardActivity
                Intent intent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                startActivity(intent);
            } else {
                // TODO: Implement login for other roles
                Toast.makeText(LoginActivity.this, "Login not implemented for this role", Toast.LENGTH_SHORT).show();
            }
        });

        signupText.setOnClickListener(v -> {
            // Navigate to SignUpActivity
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(v -> {
            // Navigate to ForgotPasswordActivity
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}
