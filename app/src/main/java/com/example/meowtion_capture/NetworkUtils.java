package com.example.meowtion_capture;

import okhttp3.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {
    private static final String SERVER_URL = "https://algoriteam.pythonanywhere.com";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static void login(String email, String password, Callback callback) {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", email);
        loginDetails.put("password", password);

        String json = gson.toJson(loginDetails);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );

        Request request = new Request.Builder()
                .url(SERVER_URL + "/login")
                .post(body)  // Ensure this is a POST request
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void getBehaviors(String userId, String sessionName, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVER_URL + "/behaviors").newBuilder();
        urlBuilder.addQueryParameter("user_id", userId);
        if (sessionName != null && !sessionName.isEmpty()) {
            urlBuilder.addQueryParameter("session_name", sessionName);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        client.newCall(request).enqueue(callback);
    }
    public static void getRecentBehaviors(String userId, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVER_URL + "/recent_behaviors").newBuilder();
        urlBuilder.addQueryParameter("user_id", userId);

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        client.newCall(request).enqueue(callback);
    }
    public static void getSessions(String userId, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(SERVER_URL + "/sessions").newBuilder();
        urlBuilder.addQueryParameter("user_id", userId);

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        client.newCall(request).enqueue(callback);
    }
}