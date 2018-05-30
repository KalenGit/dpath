package edu.dartmouth.cs.d_path.Model;

/**
 * Created by jameslee on 5/25/18.
 */


public class Course {
    public String courseNumber;
    public String title;
    public String description;
    public String instructors;
    public String distributives;
    public String offered;

    public Course(){

    }

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

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public String getDistributives() {
        return distributives;
    }

    public void setDistributives(String distributives) {
        this.distributives = distributives;
    }

    public String getOffered() {
        return offered;
    }

    public void setOffered(String offered) {
        this.offered = offered;
    }
}
