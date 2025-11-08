package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput, securityAnswer1Input, securityAnswer2Input;
    private Spinner securityQuestion1Spinner, securityQuestion2Spinner;
    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.emailInput);
        securityQuestion1Spinner = findViewById(R.id.securityQuestion1Spinner);
        securityAnswer1Input = findViewById(R.id.securityAnswer1Input);
        securityQuestion2Spinner = findViewById(R.id.securityQuestion2Spinner);
        securityAnswer2Input = findViewById(R.id.securityAnswer2Input);
        verifyBtn = findViewById(R.id.verifyBtn);

        verifyBtn.setOnClickListener(v -> verifySecurityQuestions());
    }

    private void verifySecurityQuestions() {
        String email = emailInput.getText().toString().trim();
        String question1 = securityQuestion1Spinner.getSelectedItem().toString();
        String answer1 = securityAnswer1Input.getText().toString().trim();
        String question2 = securityQuestion2Spinner.getSelectedItem().toString();
        String answer2 = securityAnswer2Input.getText().toString().trim();

        if (email.isEmpty() || answer1.isEmpty() || answer2.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        for (SignUpActivity.User user : SignUpActivity.users) {
            if (user.email.equalsIgnoreCase(email) &&
                user.securityQuestion1.equals(question1) && user.securityAnswer1.equals(answer1) &&
                user.securityQuestion2.equals(question2) && user.securityAnswer2.equals(answer2)) {

                Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
                return;
            }
        }

        Toast.makeText(this, "Invalid security questions or answers", Toast.LENGTH_SHORT).show();
    }
}
