package com.example.ambieye;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private ListView remindersListView;
    private FloatingActionButton fabAddReminder;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> remindersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        remindersListView = findViewById(R.id.remindersListView);
        fabAddReminder = findViewById(R.id.fabAddReminder);

        remindersList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, remindersList);
        remindersListView.setAdapter(adapter);

        fabAddReminder.setOnClickListener(v -> showAddReminderDialog());
    }

    private void showAddReminderDialog() {
        // Inflate custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_reminder, null);

        EditText etTitle = dialogView.findViewById(R.id.etReminderTitle);
        TextView tvDate = dialogView.findViewById(R.id.tvReminderDate);
        TextView tvTime = dialogView.findViewById(R.id.tvReminderTime);

        tvDate.setOnClickListener(v -> pickDate(tvDate));
        tvTime.setOnClickListener(v -> pickTime(tvTime));

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String date = tvDate.getText().toString().trim();
            String time = tvTime.getText().toString().trim();

            if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                String reminder = "🕒 " + title + "\n📅 " + date + " | ⏰ " + time;
                remindersList.add(reminder);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void pickDate(TextView tvDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) ->
                        tvDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                year, month, day);
        datePickerDialog.show();
    }

    private void pickTime(TextView tvTime) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) ->
                        tvTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute)),
                hour, minute, true);
        timePickerDialog.show();
    }
}
