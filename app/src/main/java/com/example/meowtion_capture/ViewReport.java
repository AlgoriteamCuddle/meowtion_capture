package com.example.meowtion_capture;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ViewReport extends AppCompatActivity {
    private String userId;
    private Gson gson = new Gson();
    private RecyclerView behaviorRecyclerView;
    private TextView sessionNameTextView;
    private FloatingActionButton firstAidButton;

    private boolean vomitingDetected = false;
    private boolean threatenedDetected = false;

    private boolean vomitingDialogShown = false;
    private boolean threatenedDialogShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        sessionNameTextView = findViewById(R.id.session_name);
        behaviorRecyclerView = findViewById(R.id.behavior_recycler_view);
        behaviorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        firstAidButton = findViewById(R.id.first_aid_button);

        TextView dashboardButton = findViewById(R.id.dashboard_button);
        ImageView logoutButton = findViewById(R.id.logout_button);
        TextView catInfoTextView = findViewById(R.id.info);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        fetchRecentBehaviors();

        dashboardButton.setOnClickListener(v -> navigateToDashboard());

        logoutButton.setOnClickListener(v -> navigateToLogIn());

        catInfoTextView.setOnClickListener(v -> navigateToCatInfo());

        firstAidButton.setOnClickListener(v -> {
            StringBuilder messageBuilder = new StringBuilder();

            if (vomitingDetected) {
                messageBuilder.append("\n➤ What to do when the cat is vomiting?\n\n")
                        .append("Withhold food for a short period (4-6 hours) to allow the stomach to settle, then offer BLAND FOOD like boiled chicken.\n\n")
                        .append("Ensure the cat stays HYDRATED.\n\n")
                        .append("If the condition does not improve, visit the vet.\n\n\n");
            }

            if (threatenedDetected) {
                messageBuilder.append("➤ What to do if the report detects threatened behavior in my cat?")
                        .append("\n\nRecommendations:" +
                                "\n\n• Identify any potential sources of stress or fear in the environment, such as other animals, loud noises, or unfamiliar people.\n\n" +
                                "• Provide a safe and quiet space for the cat to retreat to and feel secure.\n\n" +
                                "• Avoid handling the cat too much when it shows signs of fear or aggression to prevent bites or scratches.\n\n" +
                                "If the behavior persists, consider consulting with a veterinarian or a pet behaviorist for advice on managing anxiety or stress.\n");
            }

            showFirstAidPopup(messageBuilder.toString());
        });
    }

    private void fetchRecentBehaviors() {
        NetworkUtils.getRecentBehaviors(userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ViewReport", "Recent behavior data request failed", e);
                runOnUiThread(() -> {
                    Toast.makeText(ViewReport.this, "Failed to retrieve recent behavior data.", Toast.LENGTH_SHORT).show();
                    // Optionally handle the case where data retrieval failed
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, Object> responseData = gson.fromJson(response.body().string(), new TypeToken<Map<String, Object>>() {}.getType());
                    String sessionName = (String) responseData.get("session_name");
                    List<Map<String, Object>> behaviors = (List<Map<String, Object>>) responseData.get("behaviors");
                    List<Map<String, Object>> processedBehaviors = preprocessSessionData(behaviors);
                    runOnUiThread(() -> {
                        if (!processedBehaviors.isEmpty()) {
                            displayBehaviorData(sessionName, processedBehaviors);
                        } else {
                            Toast.makeText(ViewReport.this, "No recent behaviors found.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String responseBody = response.body().string();
                    Log.e("ViewReport", "Failed to retrieve recent behavior data. Status code: " + response.code() + ", Response body: " + responseBody);
                    runOnUiThread(() -> {
                        Toast.makeText(ViewReport.this, "Failed to retrieve recent behavior data. Status code: " + response.code(), Toast.LENGTH_SHORT).show();
                        // Optionally handle the case where data retrieval failed
                    });
                }
            }
        });
    }

    private List<Map<String, Object>> preprocessSessionData(List<Map<String, Object>> sessionData) {
        if (sessionData == null || sessionData.isEmpty()) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> processedData = new ArrayList<>();
        Map<String, Object> lastBehavior = null;

        for (Map<String, Object> entry : sessionData) {
            if (lastBehavior != null && lastBehavior.get("detected_objects").equals(entry.get("detected_objects"))) {
                lastBehavior.put("end_timestamp", entry.get("end_timestamp"));
            } else {
                if (lastBehavior != null) {
                    processedData.add(new HashMap<>(lastBehavior));
                }
                lastBehavior = new HashMap<>(entry);
            }
        }

        if (lastBehavior != null) {
            processedData.add(new HashMap<>(lastBehavior)); // Add the last behavior
        }

        // Detect vomiting and threatened behaviors
        for (Map<String, Object> behavior : processedData) {
            String detectedObjects = (String) behavior.get("detected_objects");
            if (detectedObjects != null) {
                String lowerCaseObjects = detectedObjects.toLowerCase();
                if (lowerCaseObjects.contains("vomiting")) {
                    vomitingDetected = true;
                }
                if (lowerCaseObjects.contains("threatened")) {
                    threatenedDetected = true;
                }
            }
        }

        // Update UI based on detected behaviors
        runOnUiThread(() -> {
            if (vomitingDetected && !vomitingDialogShown) {
                showWarningDialog("Cat detected vomiting. First aid needed.");
                vomitingDialogShown = true;
            }
            if (threatenedDetected && !threatenedDialogShown) {
                showWarningDialog("Cat feels threatened. Actions are recommended.");
                threatenedDialogShown = true;
            }
            if (vomitingDetected || threatenedDetected) {
                firstAidButton.setVisibility(View.VISIBLE);
            } else {
                firstAidButton.setVisibility(View.GONE);
            }
        });

        return processedData;
    }

    private void displayBehaviorData(String sessionName, List<Map<String, Object>> behaviors) {
        sessionNameTextView.setText("Session: " + sessionName);
        List<Map<String, Object>> processedBehaviors = preprocessSessionData(behaviors);
        ViewReportAdapter adapter = new ViewReportAdapter(processedBehaviors);
        behaviorRecyclerView.setAdapter(adapter);
    }

    private void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewReport.this);
        builder.setTitle("Warning")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();

        // Access the dialog's TextView for the title and apply bold style
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            TextView titleView = alertDialog.findViewById(android.R.id.message); // Find the TextView ID for the message
            if (titleView != null) {
                titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD); // Apply bold style to message
            }
        });
    }

    private void showFirstAidPopup(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewReport.this);
        builder.setTitle("First Aid Information")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToCatInfo() {
        Intent intent = new Intent(this, FirstAid.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }
}
