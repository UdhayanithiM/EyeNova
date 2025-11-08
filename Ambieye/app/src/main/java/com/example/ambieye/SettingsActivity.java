package com.example.ambieye;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SettingsActivity extends AppCompatActivity {

    private SignUpActivity.User currentUser;
    private TextView userName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        Button editProfileBtn = findViewById(R.id.editProfileBtn);
        CardView addDoctorCard = findViewById(R.id.addDoctorCard);
        Button logoutBtn = findViewById(R.id.logoutBtn);
        Button deleteAccountBtn = findViewById(R.id.deleteAccountBtn);

        currentUser = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        updateUI();

        editProfileBtn.setOnClickListener(v -> {
            if (currentUser != null) {
                Intent intent = new Intent(SettingsActivity.this, EditProfileActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
            } else {
                Toast.makeText(this, "User data not available.", Toast.LENGTH_SHORT).show();
            }
        });

        addDoctorCard.setOnClickListener(v -> showAddDoctorDialog());

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        deleteAccountBtn.setOnClickListener(v -> {
            // TODO: Implement account deletion logic
            Toast.makeText(this, "Delete Account Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void showAddDoctorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_doctor, null);
        builder.setView(dialogView);

        final EditText etDoctorId = dialogView.findViewById(R.id.etDoctorId);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        final AlertDialog dialog = builder.create();

        btnSubmit.setOnClickListener(v -> {
            String doctorId = etDoctorId.getText().toString();
            if (!doctorId.isEmpty()) {
                // TODO: Implement doctor ID verification logic here
                Toast.makeText(SettingsActivity.this, "Doctor ID " + doctorId + " submitted.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(SettingsActivity.this, "Please enter a Doctor ID.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateUI() {
        if (currentUser != null) {
            userName.setText(currentUser.name != null ? currentUser.name : "N/A");
            userEmail.setText(currentUser.email != null ? currentUser.email : "N/A");
        } else {
            userName.setText("Guest User");
            userEmail.setText("Not logged in");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh user data in case it was changed in another activity
        currentUser = (SignUpActivity.User) getIntent().getSerializableExtra("USER");
        updateUI();
    }
}
