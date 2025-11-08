package com.example.ambieye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentQueriesAdapter extends RecyclerView.Adapter<RecentQueriesAdapter.QueryViewHolder> {

    private List<Query> queryList;
    private Context context;

    public RecentQueriesAdapter(Context context, List<Query> queryList, String currentUserEmail) {
        this.context = context;
        this.queryList = queryList;
    }

    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_query, parent, false);
        return new QueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        Query query = queryList.get(position);
        holder.patientName.setText(query.patientName);
        holder.message.setText(query.message);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PatientDetailsActivity.class);
            intent.putExtra("PATIENT_NAME", query.patientName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return queryList.size();
    }

    static class QueryViewHolder extends RecyclerView.ViewHolder {
        TextView patientName, message;

        public QueryViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patientNameQuery);
            message = itemView.findViewById(R.id.messageQuery);
        }
    }
}
