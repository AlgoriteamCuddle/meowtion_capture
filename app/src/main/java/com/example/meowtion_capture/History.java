package com.example.meowtion_capture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class History extends AppCompatActivity {
    private static final String SERVER_URL = "https://algoriteam.pythonanywhere.com";
    private String userId;
    private Gson gson = new Gson();
    private RecyclerView sessionRecyclerView;
    private SessionAdapter sessionAdapter;
    private List<SessionInfo> sessionNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        sessionRecyclerView = findViewById(R.id.behavior_data_recycler_view);
        sessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionAdapter = new SessionAdapter(sessionNames, this::onSessionClick);
        sessionRecyclerView.setAdapter(sessionAdapter);

        Button submitButton = findViewById(R.id.submit_button);
        ImageView logoutButton = findViewById(R.id.logout_button);
        TextView dashboardButton = findViewById(R.id.dashboard_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchSessions();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogIn();
            }
        });

        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });
    }

    private void fetchSessions() {
        NetworkUtils.getSessions(userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("History", "Session data request failed", e);
                runOnUiThread(() -> Toast.makeText(History.this, "Failed to retrieve session data.", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Map<String, String> sessions = gson.fromJson(response.body().string(), new TypeToken<Map<String, String>>(){}.getType());
                    runOnUiThread(() -> displaySessions(sessions));
                } else {
                    String responseBody = response.body().string();
                    Log.e("History", "Failed to retrieve session data. Status code: " + response.code() + ", Response body: " + responseBody);
                    runOnUiThread(() -> Toast.makeText(History.this, "Failed to retrieve session data. Status code: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void displaySessions(Map<String, String> sessions) {
        sessionNames.clear();
        for (Map.Entry<String, String> entry : sessions.entrySet()) {
            sessionNames.add(new SessionInfo(entry.getKey(), entry.getValue()));
        }
        sessionAdapter.notifyDataSetChanged();
    }

    private void onSessionClick(String sessionName) {
        Intent intent = new Intent(this, ViewSession.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("session_name", sessionName);
        startActivity(intent);
    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }
}