package lab7;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

public class Course {
    
    private String courseId, title, description,instructorId;
    private List<Lesson> lessons;
    private List<String> students;
    
    //CONSTRUCTOR:------------------------------------------------------------------------
    public Course(String courseId, String title, String description, String instructorId){
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
    }
    //GETTERS:----------------------------------------------------------------------------
    public String getCourseId() {return courseId;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getInstructorId() {return instructorId;}
    public String getCourseName() {return title;}
    public String getInstructor() {return "Instructor " + instructorId;}
    public String getCategory() {
        if (courseId.startsWith("CS")) return "Programming";
        if (courseId.startsWith("MATH")) return "Math";
        if (courseId.startsWith("PHY")) return "Science";
        if (courseId.startsWith("BUS")) return "Business";
        return "General";
    }
    public String getDifficulty() {
        if (courseId.contains("101") || courseId.contains("102")) return "Beginner";
        if (courseId.contains("201") || courseId.contains("202")) return "Intermediate";
        if (courseId.contains("301") || courseId.contains("302")) return "Advanced";
        return "Beginner";
    }
    public int getTotalLessons() {return lessons.size();}
    public int getDurationHours() {return getTotalLessons() * 2;} // 2 hours per lesson as default
    
    //SETTERS:----------------------------------------------------------------------------
    public void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) 
            this.title = title;
    }
    public void setDescription(String description) {this.description = description;}
    
    //Lessons Management methods:---------------------------------------------------------
    public void addLesson(Lesson lesson){
        if (lesson != null && getLesson((String) lesson.getLessonId())==null){
            lessons.add(lesson);
        }
    }
    public boolean editLesson(String lessonId, String newTitle, String newContent){
        Lesson lesson = getLesson(lessonId);
        if (lesson != null){
            lesson.setTitle(newTitle);
            lesson.setContent(newContent);
            return true;
        }
        return false;
    }
    public boolean removeLesson(String lessonId){
        return lessons.removeIf(lesson -> lesson.getLessonId().equals(lessonId));   //removeIf is method available for Java Collections
    }
    public Lesson getLesson(String lessonId){
        for (Lesson lesson : lessons){
            if (lesson.getLessonId().equals(lessonId))
                return lesson;
        }
        return null;
    }
    
    public List<Lesson> getLessons() {return new ArrayList<>(lessons);}
    public boolean hasLessons() {return !lessons.isEmpty();}
    public int getLessonCount() {return lessons.size();}
    
    //Students Management methods:---------------------------------------------------------

    public boolean enrollStudent(String studentId){
        if (studentId != null && !studentId.trim().isEmpty() && !isStudentEnrolled(studentId)){
            students.add(studentId);
            return true;
        }
        return false;
    }
     public boolean unenrollStudent(String studentId) {
        return students.remove(studentId);
    }

    public boolean isStudentEnrolled(String studentId) {
        return students.contains(studentId);
    }

    public List<String> getStudents() {
        return new ArrayList<>(students);
    }

    public int getStudentCount() {
        return students.size();
    }
    //------------------------------------------------------------------------------------
    
    public boolean isValid() {  //validation method (just making sure it's not null nor empty)
        return courseId != null && !courseId.trim().isEmpty() &&
               title != null && !title.trim().isEmpty() &&
               instructorId != null && !instructorId.trim().isEmpty();
    }
    
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("courseId", this.courseId);
        json.put("title", this.title);
        json.put("description", this.description!=null ? this.description : "");
        json.put("instructorId", this.instructorId);
        
        //Converting List<Lesson> to JSONArray
        JSONArray lessonsArray = new JSONArray();
        for (Lesson lesson : this.lessons){
            lessonsArray.put(lesson.toJSONObject()); //that's the method located in the lesson class
        }
        json.put("lessons", lessonsArray);
        
        //Converting List<String> to JSONArray
        JSONArray studentsArray = new JSONArray();
        for (String studentId : this.students) {
            studentsArray.put(studentId);
        }
        json.put("students", studentsArray);

        return json;
    }
    /*// ===== UTILITY METHODS =====

    @Override
    public String toString() {
        return String.format("Course[ID: %s, Title: %s, Instructor: %s, Lessons: %d, Students: %d]", 
                            courseId, title, instructorId, lessons.size(), enrolledStudents.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId != null && courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() { //Talama 3amalana edit fel equal() yb2a lazm n3ml edit fel hashCode 3ashan my7slsh buggs
        return courseId != null ? courseId.hashCode() : 0;
    }*/

    public int getEnrolledStudentCount() {
        return students.size();
    }
}
