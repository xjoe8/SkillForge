package lab7;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> enrolledCourses;
    private Map<String, List<String>> progress;

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "student");
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
    }

    public boolean enrollCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            progress.put(courseId, new ArrayList<>());
            return true;
        }
        return false;
    }

    public boolean isEnrolled(String courseId) {
        return enrolledCourses.contains(courseId);
    }

    public void markLessonCompleted(String courseId, String lessonId) {
        if (progress.containsKey(courseId)) {
            List<String> completedLessons = progress.get(courseId);
            if (!completedLessons.contains(lessonId)) {
                completedLessons.add(lessonId);
            }
        }
    }

    public List<String> getCompletedLessons(String courseId) {
        return progress.getOrDefault(courseId, new ArrayList<>());
    }

    public List<String> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("userId", this.getUserId());
        json.put("username", this.getUsername());
        json.put("email", this.getEmail());
        json.put("passwordHash", this.getPasswordHash());
        json.put("role", this.getRole());

        JSONArray enrolledArray = new JSONArray();
        for (String courseId : enrolledCourses) {
            enrolledArray.put(courseId);
        }
        json.put("enrolledCourses", enrolledArray);

        JSONObject progressJson = new JSONObject();
        for (Map.Entry<String, List<String>> entry : progress.entrySet()) {
            JSONArray lessonsArray = new JSONArray();
            for (String lessonId : entry.getValue()) {
                lessonsArray.put(lessonId);
            }
            progressJson.put(entry.getKey(), lessonsArray);
        }
        json.put("progress", progressJson);

        return json;
    }
}
