package com.example.youtubeactivitytracker.DTO;

public class VideoActivityResponse {
    private final String category;
    private final double timeSpent;


    public VideoActivityResponse(String category, double timeSpent) {
        this.category = category;
        this.timeSpent = timeSpent;
    }

    public String getCategory() {
        return category;
    }

    public double getTimeSpent() {
        return timeSpent;
    }
}
