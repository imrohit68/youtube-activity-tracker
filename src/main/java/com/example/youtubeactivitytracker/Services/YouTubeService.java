package com.example.youtubeactivitytracker.Services;




import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class YouTubeService {

    private static final String API_KEY = ""; // Replace with your API Key
    private static final String YOUTUBE_VIDEO_API_URL = "https://www.googleapis.com/youtube/v3/videos";
    private static final String YOUTUBE_CATEGORIES_API_URL = "https://www.googleapis.com/youtube/v3/videoCategories";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getVideoCategory(String videoUrl) {
        String videoId = extractVideoId(videoUrl);
        if (videoId == null) {
            return "Invalid YouTube URL";
        }

        // Get Category ID from Video
        String categoryId = getCategoryId(videoId);
        if (categoryId == null) {
            return "Category ID not found";
        }

        // Get Category Name from Category ID
        return getCategoryName(categoryId);
    }

    private String extractVideoId(String url) {
        if (url.contains("youtube.com/watch?v=")) {
            return url.split("v=")[1].split("&")[0];
        } else if (url.contains("youtu.be/")) {
            return url.split("youtu.be/")[1].split("\\?")[0];
        }
        return null;
    }

    private String getCategoryId(String videoId) {
        String url = UriComponentsBuilder.fromHttpUrl(YOUTUBE_VIDEO_API_URL)
                .queryParam("part", "snippet")
                .queryParam("id", videoId)
                .queryParam("key", API_KEY)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            if (rootNode.has("items") && rootNode.get("items").size() > 0) {
                return rootNode.get("items").get(0).get("snippet").get("categoryId").asText();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private String getCategoryName(String categoryId) {
        String url = UriComponentsBuilder.fromHttpUrl(YOUTUBE_CATEGORIES_API_URL)
                .queryParam("part", "snippet")
                .queryParam("regionCode", "US") // Use appropriate country code
                .queryParam("key", API_KEY)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            if (rootNode.has("items")) {
                for (JsonNode item : rootNode.get("items")) {
                    if (item.has("id") && item.get("id").asText().equals(categoryId)) {
                        return item.get("snippet").get("title").asText(); // Returns category name
                    }
                }
            }
        } catch (Exception e) {
            return "Error fetching category name";
        }
        return "Unknown Category";
    }
}

