package com.example.meowtion_capture;

public class SessionInfo {
    private String sessionName;
    private String creationDate;

    public SessionInfo(String sessionName, String creationDate) {
        this.sessionName = sessionName;
        this.creationDate = creationDate;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
