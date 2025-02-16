package com.example.youtubeactivitytracker.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "video_activity", indexes = {
        @Index(name = "idx_user_id", columnList = "userId"),
        @Index(name = "idx_date", columnList = "date"),
        @Index(name = "idx_category", columnList = "category")
}
)
public class VideoActivityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String category;
    private double timeSpent;
    private LocalDate date;

    public VideoActivityModel(Long id, Long userId,String category, double timeSpent, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.timeSpent = timeSpent;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public VideoActivityModel() {

    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }


    public String getCategory() {
        return category;
    }

    public double getTimeSpent() {
        return timeSpent;
    }

}

