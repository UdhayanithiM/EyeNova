package com.example.ambieye;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }

        TextView patientNameValue = findViewById(R.id.patientNameValue);
        TextView patientEmailValue = findViewById(R.id.patientEmailValue);
        TextView editProfileButton = findViewById(R.id.editProfileButton);
        LinearLayout addDoctorButton = findViewById(R.id.addDoctorButton);

        SignUpActivity.User user = (SignUpActivity.User) getIntent().getSerializableExtra("USER");

        if (user != null) {
            patientNameValue.setText(user.name);
            patientEmailValue.setText(user.email);
        }

        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientDetailsActivity.this, EditProfileActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        });

        addDoctorButton.setOnClickListener(v -> showAddDoctorDialog());
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
                // TODO: Implement doctor ID verification logic
                Toast.makeText(this, "Doctor ID Submitted: " + doctorId, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
