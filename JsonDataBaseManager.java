package lab7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonDataBaseManager {
    
    private final String usersPath;
    private final String coursesPath;
    
    public JsonDataBaseManager(String usersPath, String coursesPath){
        this.usersPath = usersPath;
        this.coursesPath = coursesPath;
    }
    
    public JSONArray loadUsers() {
        try {
            String loadedContent = new String(Files.readAllBytes(Paths.get(this.usersPath))); // reads the entire users.json file
            return new JSONArray(loadedContent); // convert he text ( string ) to JSONArray

        } catch (IOException e) {
            System.out.println("Error reading users.json: " + e.getMessage());
            return new JSONArray(); // return empty array if file is missing
        }
    }

    public JSONArray loadCourses() {
        try {
            String loadedContent = new String(Files.readAllBytes(Paths.get(this.coursesPath))); // reads the entire courses.json file
            return new JSONArray(loadedContent); // convert he text ( string ) to JSONArray

        } catch (IOException e) {
            System.out.println("Error reading courses.json: " + e.getMessage());
            return new JSONArray(); // return empty array if file is missing
        }
    }
    
    public void saveUsers(JSONArray users) {
        try {
            Files.write( // overwrites the file with the new content givven to it
                    Paths.get(this.usersPath), // converts a file path ( string ) into a path object
                    users.toString(4).getBytes()); // converts it into a formatted text , with 4 spaces indentation
        }
        catch (IOException e) {
            System.out.println("Error saving users.json: " + e.getMessage());
        }
    }
    public void saveCourses(JSONArray courses) {
        try {
            Files.write( // overwrites the file with the new content given to it
                    Paths.get(this.coursesPath), // converts a file path ( string ) into a path object
                    courses.toString(4).getBytes()); // converts it into a formatted text , with 4 spaces indentation
        }
        catch (IOException e) {
            System.out.println("Error saving courses.json: " + e.getMessage());
        }
    }
    
    public User parseUser(JSONObject J) {
        String role = J.getString("role");

        if (role.equals("student")) {
            return parseStudent(J);
        } 
        else if (role.equals("instructor")) {
            return parseInstructor(J);
        }
        return null;
    }
        
    public Student parseStudent(JSONObject J) {

        Student s = new Student(
            J.getString("userId"),
            J.getString("username"),
            J.getString("email"),
            J.getString("passwordHash"));
        
        JSONArray enrolled = J.optJSONArray("enrolledCourses");
        if (enrolled != null) {
            for (int i = 0; i < enrolled.length(); i++) {
                s.enrollCourse(enrolled.getString(i));
            }
        }

        JSONObject progress = J.optJSONObject("progress");
        if (progress != null) {
            for (String courseId : progress.keySet()) {
                JSONArray lessons = progress.getJSONArray(courseId);
                for (int i = 0; i < lessons.length(); i++) {
                    s.markLessonCompleted(courseId, lessons.getString(i));
                }
            }
        }
        return s;
    }

    public Instructor parseInstructor(JSONObject J) {

        Instructor inst = new Instructor(
            J.getString("userId"),
            J.getString("username"),
            J.getString("email"),
            J.getString("passwordHash"));

        JSONArray created = J.optJSONArray("createdCourses");
        if (created != null) {
            for (int i = 0; i < created.length(); i++) {
                inst.addCreatedCourse(created.getString(i));
            }
        }

        return inst;
    }

    public Course parseCourse(JSONObject J) {
        
        Course course = new Course(
            J.getString("courseId"),
            J.getString("title"),
            J.getString("description"),
            J.getString("instructorId"));
        
        JSONArray lessons = J.optJSONArray("lessons");
        if (lessons != null) {
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject lessonJson = lessons.getJSONObject(i);
                Lesson lesson = parseLesson(lessonJson);
                course.addLesson(lesson);
            }
        }
        
        JSONArray students = J.optJSONArray("students");
        if (students != null) {
            for (int i = 0; i < students.length(); i++) {
                course.enrollStudent(students.getString(i));
            }
        }

        return course;
    }
    
    public Lesson parseLesson(JSONObject J) {
        Lesson lesson = new Lesson(
                J.getString("lessonId"),
                J.getString("title"),
                J.getString("content"));
        
        JSONArray resArr = J.optJSONArray("resources");
        if (resArr != null) {
            for (int i = 0; i < resArr.length(); i++) {
                lesson.addResource(resArr.getString(i));
            }
        }
        return lesson;
    }
    
/*    public JSONObject toJson(User user) {
        return user.toJSONObject();
    }

    public JSONObject toJson(Student student) {
        return student.toJSONObject(); // Just call the object's own method
    }

    public JSONObject toJson(Instructor instructor) {
        return instructor.toJSONObject(); // Just call the object's own method
    }*/
    
    public String generateId() {
        return UUID.randomUUID().toString();
    }

}
