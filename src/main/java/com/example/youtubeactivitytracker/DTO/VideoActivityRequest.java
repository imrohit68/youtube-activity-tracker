package com.example.youtubeactivitytracker.DTO;

import java.time.LocalDate;

public class VideoActivityRequest {
    private final String videoUrl;
    private final double timeSpent;
    public VideoActivityRequest(String videoUrl, Double timeSpent) {
        this.videoUrl = videoUrl;
        this.timeSpent = timeSpent;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
    public double getTimeSpent() {
        return timeSpent;
    }
}
