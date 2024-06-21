package com.example.meowtion_capture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        ImageView viewReportButton = findViewById(R.id.view_report_button);
        ImageView historyButton = findViewById(R.id.history_button);
        ImageView logoutButton = findViewById(R.id.logout_button);

        viewReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToViewReport();
            }
        });
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHistory();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogIn();
            }
        });

    }

    private void navigateToViewReport() {
        Intent intent = new Intent(this, ViewReport.class);
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
}
