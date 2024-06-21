package com.example.meowtion_capture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {
    private List<SessionInfo> sessionNames;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String sessionName);
    }

    public SessionAdapter(List<SessionInfo> sessionNames, OnItemClickListener listener) {
        this.sessionNames = sessionNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        SessionInfo sessionInfo = sessionNames.get(position);
        holder.sessionNameTextView.setText(sessionInfo.getSessionName());
        holder.creationDateTextView.setText(sessionInfo.getCreationDate());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(sessionInfo.getSessionName()));
    }

    @Override
    public int getItemCount() {
        return sessionNames.size();
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView sessionNameTextView;
        TextView creationDateTextView;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionNameTextView = itemView.findViewById(android.R.id.text1);
            creationDateTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}