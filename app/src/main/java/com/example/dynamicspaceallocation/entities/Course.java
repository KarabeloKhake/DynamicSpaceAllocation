package com.example.dynamicspaceallocation.entities;

public class Course {
    //Data Members
    private String courseCode;
    private String courseDescription;
    private String courseName;

    //Constructors
    public Course() {} //end default constructor
//    public Course(String courseCode, String courseDescription, String courseName) {
//        this.courseCode = courseCode;
//        this.courseDescription = courseDescription;
//        this.courseName = courseName;
//    } //end overloaded constructor

    //Methods
    public String getCourseCode() {
        return courseCode;
    } //end getCourseCode()
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    } //end setCourseCode()

    public String getCourseDescription() {
        return courseDescription;
    } //end getCourseDescription
    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    } //end setCourseDescription()

    public String getCourseName() {
        return courseName;
    } //end getCourseName()
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    } //end setCourseName()
} //end class Course
