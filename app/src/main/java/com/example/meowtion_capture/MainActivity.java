package com.example.meowtion_capture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashMap;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout emailField;
    private TextInputLayout passwordField;
    private String userId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getEditText().getText().toString();
                String password = passwordField.getEditText().getText().toString();
                login(email, password);
            }
        });
    }

    private void login(String email, String password) {
        NetworkUtils.login(email, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainActivity", "Login request failed", e);
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    userId = gson.fromJson(response.body().string(), HashMap.class).get("user_id").toString();
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        intent.putExtra("user_id", userId);
                        startActivity(intent);
                    });
                } else {
                    String responseBody = response.body().string();
                    Log.e("MainActivity", "Login failed. Status code: " + response.code() + ", Response body: " + responseBody);
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Login failed. Status code: " + response.code(), Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
