package com.example.youtubeactivitytracker.Controller;

import com.example.youtubeactivitytracker.DTO.VideoActivityResponse;
import com.example.youtubeactivitytracker.DTO.VideoActivityRequest;
import com.example.youtubeactivitytracker.Services.VideoActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class VideoActivityController {
    @Autowired
    private VideoActivityService videoActivityService;

    @GetMapping(value = "/video-activity/{userId}/{offset}")
    public ResponseEntity<List<VideoActivityResponse>> getVideoActivity(@PathVariable Long userId,@PathVariable int offset) {
        return ResponseEntity.ok(videoActivityService.getVideoActivity(userId,offset));
    }
    @PostMapping(value = "/video-activity/{userId}")
    public ResponseEntity<Void> createVideoActivity(@PathVariable Long userId,@RequestBody VideoActivityRequest videoUrlRequest) {
        videoActivityService.createVideoActivity(userId,videoUrlRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }
}
