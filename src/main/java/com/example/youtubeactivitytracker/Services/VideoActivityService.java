package com.example.youtubeactivitytracker.Services;

import com.example.youtubeactivitytracker.DTO.VideoActivityResponse;
import com.example.youtubeactivitytracker.DTO.VideoActivityRequest;
import com.example.youtubeactivitytracker.Entity.VideoActivityModel;
import com.example.youtubeactivitytracker.Repository.VideoActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class VideoActivityService {
    @Autowired
    private  VideoActivityRepository videoActivityRepository;
    @Autowired
    private YouTubeService youTubeService;

    public void createVideoActivity(Long userId, VideoActivityRequest videoUrlRequest) {
        String videoCategory = youTubeService.getVideoCategory(videoUrlRequest.getVideoUrl());
        if(videoActivityRepository.existsByUserIdAndCategoryAndDate(userId,videoCategory,LocalDate.now(ZoneId.of("Asia/Kolkata")))) {
            VideoActivityModel videoActivityModel = videoActivityRepository.findByUserIdAndCategoryAndDate(userId,videoCategory,LocalDate.now(ZoneId.of("Asia/Kolkata")));
            VideoActivityModel updatedVideoActivityModel = new VideoActivityModel(videoActivityModel.getId(),
                    userId,videoCategory,
                    videoActivityModel.getTimeSpent()+videoUrlRequest.getTimeSpent(),
                    LocalDate.now(ZoneId.of("Asia/Kolkata")));
            videoActivityRepository.save(updatedVideoActivityModel);
        }
        else {
            VideoActivityModel videoActivityModel = new VideoActivityModel(null, userId, videoCategory, videoUrlRequest.getTimeSpent(),LocalDate.now(ZoneId.of("Asia/Kolkata")));
            videoActivityRepository.save(videoActivityModel);
        }
    }
    public List<VideoActivityResponse>  getVideoActivity(Long userId, int offset) {
        return videoActivityRepository.findTop10ByUserIdAndDate(userId,LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(offset)).stream().map(videoActivityModel -> new VideoActivityResponse(
                videoActivityModel.getCategory(),
                videoActivityModel.getTimeSpent(
        ))).collect(toList());
    }
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteOldRecords() {
        LocalDate sevenDaysAgo = LocalDate.now(ZoneId.of("Asia/Kolkata")).minusDays(7);
        videoActivityRepository.deleteByDateBefore(sevenDaysAgo);
    }
}
