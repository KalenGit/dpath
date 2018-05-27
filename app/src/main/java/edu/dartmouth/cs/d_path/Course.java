package edu.dartmouth.cs.d_path;

/**
 * Created by jameslee on 5/25/18.
 */

public class Course {
    String courseNumber;
    String title;
    String description;

    public Course(String courseNumber, String title) {
        this.courseNumber = courseNumber;
        this.title = title;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
