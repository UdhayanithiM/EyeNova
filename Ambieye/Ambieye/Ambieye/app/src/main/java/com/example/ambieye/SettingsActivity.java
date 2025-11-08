package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button changePasswordButton = findViewById(R.id.change_password_button);
        Button logoutButton = findViewById(R.id.logout_button);

        changePasswordButton.setOnClickListener(v -> showChangePasswordDialog());

        logoutButton.setOnClickListener(v -> {
            // For now, just navigate back to the LoginActivity
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");

        // Set up the input
        final EditText oldPassword = new EditText(this);
        final EditText newPassword = new EditText(this);
        final EditText confirmPassword = new EditText(this);

        oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        oldPassword.setHint(R.string.old_password);
        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newPassword.setHint(R.string.new_password);
        confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPassword.setHint(R.string.confirm_new_password);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(oldPassword);
        layout.addView(newPassword);
        layout.addView(confirmPassword);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Change", (dialog, which) -> {
            String newPass = newPassword.getText().toString();
            String confirmPass = confirmPassword.getText().toString();

            // TODO: Add your password change logic here
            if (newPass.equals(confirmPass)) {
                Toast.makeText(SettingsActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
