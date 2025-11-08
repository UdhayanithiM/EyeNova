package com.example.ambieye;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> historyItemList;

    public HistoryAdapter(List<HistoryItem> historyItemList) {
        this.historyItemList = historyItemList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = historyItemList.get(position);
        holder.tvDateNumber.setText(item.getDateNumber());
        holder.tvDateMonth.setText(item.getDateMonth());
        holder.tvGameName.setText(item.getGameName());
        holder.tvGameTime.setText(item.getGameTime());
        holder.tvGameScore.setText("Score: " + item.getGameScore());
        holder.tvAccuracy.setText("Accuracy: " + item.getAccuracy() + "%");
        holder.accuracyProgressBar.setProgress(item.getAccuracy());
    }

    @Override
    public int getItemCount() {
        return historyItemList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateNumber, tvDateMonth, tvGameName, tvGameTime, tvGameScore, tvAccuracy;
        ProgressBar accuracyProgressBar;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateNumber = itemView.findViewById(R.id.tvDateNumber);
            tvDateMonth = itemView.findViewById(R.id.tvDateMonth);
            tvGameName = itemView.findViewById(R.id.tvGameName);
            tvGameTime = itemView.findViewById(R.id.tvGameTime);
            tvGameScore = itemView.findViewById(R.id.tvGameScore);
            tvAccuracy = itemView.findViewById(R.id.tvAccuracy);
            accuracyProgressBar = itemView.findViewById(R.id.accuracyProgressBar);
        }
    }
}
