package com.example.youtubeactivitytracker.Repository;

import com.example.youtubeactivitytracker.Entity.VideoActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoActivityRepository extends JpaRepository<VideoActivityModel,Long> {
    @Query(value = "SELECT * FROM video_activity WHERE user_id = :userId AND date = :localDate ORDER BY time_spent DESC LIMIT 10",
            nativeQuery = true)
    List<VideoActivityModel> findTop10ByUserIdAndDate(@Param("userId") Long userId,
                                                      @Param("localDate") LocalDate localDate);

    boolean existsByUserIdAndCategoryAndDate(Long userId, String category, LocalDate date);

    void deleteByDateBefore(LocalDate date);
    VideoActivityModel findByUserIdAndCategoryAndDate(Long userId, String category, LocalDate date);
}
