package lab7;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> resources;

    // Constructor WITH resources
    public Lesson(String lessonId, String title, String content, List<String> resources) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>(resources); // FIXED
    }

    // Constructor WITHOUT resources
    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>(); // FIXED
    }

    // Getters & Setters
    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) { // FIXED
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        if (content == null || content.trim().isEmpty()) {
            return "No content available for this lesson.";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getResources() {
        return new ArrayList<>(resources); // return copy
    }


    // Add / Remove resources
    public void addResource(String url) {
        if (isValidUrl(url) && !resources.contains(url)) {
            resources.add(url);
        }
    }

    public boolean removeResource(String url) {
        return resources.remove(url);
    }

    // URL validation
    private boolean isValidUrl(String url) {
        return url != null && !url.trim().isEmpty() &&
               (url.startsWith("http://") || url.startsWith("https://"));
    }

    // json method 
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("lessonId", lessonId);
        json.put("title", title);
        json.put("content", content != null ? content : JSONObject.NULL);

        JSONArray resourceArray = new JSONArray();
        for (String r : resources) {
            resourceArray.put(r);
        }
        json.put("resources", resourceArray);

        return json;
    }
}
