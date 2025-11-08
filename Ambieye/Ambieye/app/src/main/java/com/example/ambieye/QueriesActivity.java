package com.example.ambieye;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QueriesActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private EditText messageInput;
    private String patientName;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        patientName = getIntent().getStringExtra("USERNAME");
        currentUserEmail = getIntent().getStringExtra("EMAIL");

        TextView recipientName = findViewById(R.id.recipientName);
        recipientName.setText(patientName);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        ImageButton sendBtn = findViewById(R.id.sendBtn);

        if (!DoctorDashboardActivity.conversations.containsKey(patientName)) {
            DoctorDashboardActivity.conversations.put(patientName, new ArrayList<>());
        }
        messageList = DoctorDashboardActivity.conversations.get(patientName);

        chatAdapter = new ChatAdapter(this, messageList, currentUserEmail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        sendBtn.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            ChatMessage newMessage = new ChatMessage(messageText, currentUserEmail);
            messageList.add(newMessage);
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            chatRecyclerView.scrollToPosition(messageList.size() - 1);
            messageInput.setText("");
        }
    }
}
