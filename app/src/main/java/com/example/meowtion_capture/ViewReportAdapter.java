package com.example.meowtion_capture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ViewReportAdapter extends RecyclerView.Adapter<ViewReportAdapter.BehaviorViewHolder> {

    private List<Map<String, Object>> behaviors;

    public ViewReportAdapter(List<Map<String, Object>> behaviors) {
        this.behaviors = behaviors;
    }

    @NonNull
    @Override
    public BehaviorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_report, parent, false);
        return new BehaviorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BehaviorViewHolder holder, int position) {
        Map<String, Object> behavior = behaviors.get(position);
        String startTimestamp = (String) behavior.get("start_timestamp");
        String endTimestamp = (String) behavior.get("end_timestamp");
        String detectedObjects = (String) behavior.get("detected_objects");

        // Parse and format start timestamp
        Date parsedStartDate = parseTimestamp(startTimestamp);
        if (parsedStartDate != null) {
            String formattedStartTime = formatTimestamp(parsedStartDate);
            holder.startTimestamp.setText(formattedStartTime);
        } else {
            holder.startTimestamp.setText("Invalid timestamp");
        }

        // Parse and format end timestamp
        Date parsedEndDate = parseTimestamp(endTimestamp);
        if (parsedEndDate != null) {
            String formattedEndTime = formatTimestamp(parsedEndDate);
            holder.endTimestamp.setText(formattedEndTime);
        } else {
            holder.endTimestamp.setText("Invalid timestamp");
        }

        // Set detected objects and apply color logic
        holder.detectedObjects.setText(detectedObjects);
        if (detectedObjects != null) {
            String lowerCaseObjects = detectedObjects.toLowerCase();
            if (lowerCaseObjects.contains("vomiting") || lowerCaseObjects.contains("threatened")) {
                holder.detectedObjects.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red)); // Change to appropriate color resource
            } else {
                holder.detectedObjects.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black)); // Default color
            }
        }
    }

    @Override
    public int getItemCount() {
        return behaviors != null ? behaviors.size() : 0;
    }

    private Date parseTimestamp(String timestamp) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
        try {
            return inputFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatTimestamp(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd (EEE), hh:mm:ss a", Locale.US);
        return outputFormat.format(date);
    }

    public static class BehaviorViewHolder extends RecyclerView.ViewHolder {

        TextView detectedObjects;
        TextView startTimestamp;
        TextView endTimestamp;

        public BehaviorViewHolder(@NonNull View itemView) {
            super(itemView);
            detectedObjects = itemView.findViewById(R.id.detected_objects);
            startTimestamp = itemView.findViewById(R.id.start_timestamp);
            endTimestamp = itemView.findViewById(R.id.end_timestamp);
        }
    }
}
