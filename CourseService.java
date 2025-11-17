package lab7;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private JsonDataBaseManager db;

    public CourseService(JsonDataBaseManager db) {
        this.db = db;
    }

    public Course createCourse(String instructorId, String title, String description) {
        String courseId = db.generateId();
        Course course = new Course(courseId, title, description, instructorId);
        
        JSONArray coursesArray = db.loadCourses();
        JSONObject courseJson = db.toJson(course);
        coursesArray.put(courseJson);
        
        db.saveCourses(coursesArray);
        return course;
    }

    public boolean editCourse(String courseId, String newTitle, String newDescription) {
        JSONArray coursesArray = db.loadCourses();
        
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {
                // Parse the course to preserve existing data
                Course existingCourse = db.parseCourse(courseJson);
                
                // Create updated course with existing data but new title/description
                Course updatedCourse = new Course(
                    courseId, 
                    newTitle, 
                    newDescription, 
                    existingCourse.getInstructorId()
                );
                
                // Copy existing lessons and students
                for (Lesson lesson : existingCourse.getLessons()) {
                    updatedCourse.addLesson(lesson);
                }
                for (String studentId : existingCourse.getStudents()) {
                    updatedCourse.enrollStudent(studentId);
                }
                
                // Replace with updated course
                JSONObject updatedCourseJson = db.toJson(updatedCourse);
                coursesArray.put(i, updatedCourseJson);
                
                db.saveCourses(coursesArray);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourse(String courseId) {
        JSONArray coursesArray = db.loadCourses();
        JSONArray updatedArray = new JSONArray();
        boolean found = false;
        
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (!courseJson.getString("courseId").equals(courseId)) {
                updatedArray.put(courseJson);
            } else {
                found = true;
            }
        }
        
        if (found) {
            db.saveCourses(updatedArray);
        }
        return found;
    }

    public List<Course> browseCourses() {
        List<Course> courses = new ArrayList<>();
        JSONArray coursesArray = db.loadCourses();
        
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            Course course = db.parseCourse(courseJson);
            courses.add(course);
        }
        return courses;
    }

    public boolean enrollStudent(String courseId, String studentId) {
        JSONArray coursesArray = db.loadCourses();
        boolean enrolledInCourse = false;

        // 1. Update the course with the new student
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {
                Course course = db.parseCourse(courseJson);
                enrolledInCourse = course.enrollStudent(studentId);

                if (enrolledInCourse) {
                    JSONObject updatedCourseJson = db.toJson(course);
                    coursesArray.put(i, updatedCourseJson);
                    db.saveCourses(coursesArray);
                }
                break;
            }
        }

        // 2. Update the student's enrolled courses list
        if (enrolledInCourse) {
            JSONArray usersArray = db.loadUsers();
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                if (userJson.getString("userId").equals(studentId) && 
                    userJson.getString("role").equals("student")) {

                    Student student = db.parseStudent(userJson);
                    student.enrollCourse(courseId);

                    JSONObject updatedStudentJson = db.toJson(student);
                    usersArray.put(i, updatedStudentJson);
                    db.saveUsers(usersArray);
                    break;
                }
            }
        }

        return enrolledInCourse;
    }

    public Course getCourseById(String courseId) {
        JSONArray coursesArray = db.loadCourses();
        
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("courseId").equals(courseId)) {
                return db.parseCourse(courseJson);
            }
        }
        return null;
    }

    public List<Course> getCoursesByInstructor(String instructorId) {
        List<Course> instructorCourses = new ArrayList<>();
        JSONArray coursesArray = db.loadCourses();
        
        for (int i = 0; i < coursesArray.length(); i++) {
            JSONObject courseJson = coursesArray.getJSONObject(i);
            if (courseJson.getString("instructorId").equals(instructorId)) {
                Course course = db.parseCourse(courseJson);
                instructorCourses.add(course);
            }
        }
        return instructorCourses;
    }

    public boolean isStudentEnrolled(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        return course != null && course.isStudentEnrolled(studentId);
    }

    public int getEnrolledStudentCount(String courseId) {
        Course course = getCourseById(courseId);
        return course != null ? course.getEnrolledStudentCount() : 0;
    }
}