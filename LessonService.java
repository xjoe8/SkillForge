package lab7;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LessonService {

    private JsonDataBaseManager db;

    public LessonService(JsonDataBaseManager db) {
        this.db = db;
    }

   
    public Lesson addLesson(String courseId, Lesson lesson) {
        JSONArray coursesArray = db.loadCourses();

        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {

                // Parse course object
                Course course = db.parseCourse(courseJson);
                course.addLesson(lesson);

                // Update JSON (won't persist correctly because toJson is empty)
                JSONObject updatedCourseJson = db.toJson(course);
                coursesArray.put(i, updatedCourseJson);

                db.saveCourses(coursesArray);
                return lesson;
            }
        }

        // Course not found
        return null;
    }

   
    public boolean editLesson(String courseId, String lessonId, String newTitle, String newContent) {
        JSONArray coursesArray = db.loadCourses();

        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {

                Course course = db.parseCourse(courseJson);
                List<Lesson> lessons = course.getLessons();

                for (Lesson lesson : lessons) {
                    if (lesson.getLessonId().equals(lessonId)) {
                        lesson.setTitle(newTitle);
                        lesson.setContent(newContent);

                        JSONObject updatedCourseJson = db.toJson(course);
                        coursesArray.put(i, updatedCourseJson);

                        db.saveCourses(coursesArray);
                        return true;
                    }
                }
            }
        }

        return false; // Lesson not found
    }

    public boolean deleteLesson(String courseId, String lessonId) {
        JSONArray coursesArray = db.loadCourses();

        for (int i = 0; i < coursesArray.length(); i++) {
            
            JSONObject courseJson = coursesArray.getJSONObject(i);
            
            if (courseJson.getString("courseId").equals(courseId)) {

                Course course = db.parseCourse(courseJson);
                List<Lesson> lessons = course.getLessons();

                for (int j = 0; j < lessons.size(); j++) {
                    if (lessons.get(j).getLessonId().equals(lessonId)) {
                        lessons.remove(j);

                        JSONObject updatedCourseJson = db.toJson(course);
                        coursesArray.put(i, updatedCourseJson);

                        db.saveCourses(coursesArray);
                        return true;
                    }
                }
            }
        }

        return false; // Lesson not found
    }

   
    public List<Lesson> getlessonsByCourse(String courseId) {
        List<Lesson> list = new ArrayList<>();
        JSONArray coursesArray = db.loadCourses();

        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {

                Course course = db.parseCourse(courseJson);
                list.addAll(course.getLessons());
                break;
            }
        }

        return list;
    }
}

