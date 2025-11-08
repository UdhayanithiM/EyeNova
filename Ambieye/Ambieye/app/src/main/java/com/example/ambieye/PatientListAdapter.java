package com.example.ambieye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientListAdapter extends ArrayAdapter<SignUpActivity.User> {

    private List<SignUpActivity.User> userListFull;

    public PatientListAdapter(Context context, List<SignUpActivity.User> users) {
        super(context, 0, users);
        userListFull = new ArrayList<>(users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SignUpActivity.User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_patient, parent, false);
        }

        TextView patientName = convertView.findViewById(R.id.patientName);
        TextView patientEmail = convertView.findViewById(R.id.patientEmail);

        patientName.setText(user.name);
        patientEmail.setText(user.email);

        return convertView;
    }

    @Override
    public android.widget.Filter getFilter() {
        return userFilter;
    }

    private android.widget.Filter userFilter = new android.widget.Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SignUpActivity.User> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(userListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SignUpActivity.User item : userListFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
