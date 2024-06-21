package com.example.meowtion_capture;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSession extends AppCompatActivity {
    private String userId;
    private String sessionName;
    private Gson gson = new Gson();
    private RecyclerView behaviorRecyclerView;
    private BehaviorAdapter behaviorAdapter;
    private boolean vomitingDetected = false;
    private boolean threatenedDetected = false;
    private boolean vomitingDialogShown = false;
    private boolean threatenedDialogShown = false;
    private ImageView firstAidButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        behaviorRecyclerView = findViewById(R.id.behavior_recycler_view);
        behaviorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView sessionNameTextView = findViewById(R.id.session_name);
        firstAidButton = findViewById(R.id.first_aid_button);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        sessionName = intent.getStringExtra("session_name");

        fetchSessionData();

        sessionNameTextView.setText("Session: " + sessionName);
        TextView dashboardButton = findViewById(R.id.dashboard_button);
        ImageView logoutButton = findViewById(R.id.logout_button);
        ImageView backButton = findViewById(R.id.back_button);
        TextView catInfoTextView = findViewById(R.id.info);

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogIn();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHistory();
            }
        });

        catInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCatInfo();
            }
        });

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

    private void fetchSessionData() {
        NetworkUtils.getBehaviors(userId, sessionName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ViewSession", "Failed to fetch session data", e);
                runOnUiThread(() -> showErrorDialog("Failed to fetch session data"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                    List<Map<String, Object>> sessionData = gson.fromJson(responseBody, listType);

                    // Preprocess the session data
                    List<Map<String, Object>> processedData = preprocessSessionData(sessionData);

                    runOnUiThread(() -> displaySessionData(processedData));
                } else {
                    String responseBody = response.body().string();
                    Log.e("ViewSession", "Failed to fetch session data. Status code: " + response.code() + ", Response body: " + responseBody);
                    runOnUiThread(() -> showErrorDialog("Failed to fetch session data. Status code: " + response.code()));
                }
            }
        });
    }

    private List<Map<String, Object>> preprocessSessionData(List<Map<String, Object>> sessionData) {
        if (sessionData == null || sessionData.isEmpty()) {
            return sessionData;
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

        // Additional logic for warnings based on detected objects
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

    private void displaySessionData(List<Map<String, Object>> sessionData) {
        behaviorAdapter = new BehaviorAdapter(sessionData);
        behaviorRecyclerView.setAdapter(behaviorAdapter);
    }

    private void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSession.this);
        builder.setTitle("Warning")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showFirstAidPopup(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSession.this);
        builder.setTitle("First Aid Information")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSession.this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    private void navigateToHistory() {
        Intent intent = new Intent(this, History.class);
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
