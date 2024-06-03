package com.valkonsky.markanalyzer1.entity;

import java.util.HashMap;
import java.util.Map;

public class Student {
    
    private String name;
    private Map<String,String> marks;

    public double getAverageScore()  {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    private double averageScore;
    public Student(){
        marks = new HashMap<>();
    }

    public Student(String name){
        this.name = name;
        marks = new HashMap<>();
    }

    public String getName() {
        return name;
    }
    public Map<String, String> getMarks() {
        return marks;
    }

    public void setMarks(Map<String, String> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name;
    }
}
