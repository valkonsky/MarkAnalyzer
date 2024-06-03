package com.valkonsky.markanalyzer1.entity;

import java.util.ArrayList;
import java.util.List;

public class SchoolClass {
    private String schoolClassName;

    private List <Student> students;

    private float academicPerfomance;

    public List<String> getSchoolSubjects() {
        return schoolSubjects;
    }

    public void setSchoolSubjects(List<String> schoolSubjects) {
        this.schoolSubjects = schoolSubjects;
    }

    private List <String> schoolSubjects;
    public String getSchoolClassName() {
        return schoolClassName;
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public SchoolClass(){
        students = new ArrayList<>();
        schoolSubjects = new ArrayList<>();
    }

    public SchoolClass(String name){
        students = new ArrayList<>();
        schoolSubjects = new ArrayList<>();
        schoolClassName = name;
    }

    public List<Student> getUnAttestedStudentsInStudentClass(){
        List<Student> unAttestedStudents = new ArrayList<>();
        for(Student student:students){
           if (student.getMarks().containsValue("")||(student.getMarks().containsValue(" "))){
               unAttestedStudents.add(student);
           }
        }
        return unAttestedStudents;
    }

    public List<Student> getUnAttestedStudentsInStudentClassForRespectReason(){
        List<Student> unAttestedStudents = new ArrayList<>();
        for(Student student:students){
            if (student.getMarks().containsValue("НАУ")){
                unAttestedStudents.add(student);
            }
        }
        return unAttestedStudents;
    }

    public List<Student> getUnAttestedStudentsInStudentClassForUnRespectReason(){
        List<Student> unAttestedStudents = new ArrayList<>();
        for(Student student:students){
            if (student.getMarks().containsValue("НАН")){
                unAttestedStudents.add(student);
            }
        }
        return unAttestedStudents;
    }

    public List<Student> getExcelentStudents(){
        List<Student> excelentStudents = new ArrayList<>();
        for(Student student:students){
            int counter = 0;
            for (String subject:schoolSubjects){
                if (student.getMarks().get(subject).equals("5")||(student.getMarks().get(subject).equals("ЗТ"))){
                    counter++;
                }
            }
            if (counter==schoolSubjects.size()){
                excelentStudents.add(student);
            }
        }
        return excelentStudents;
    }

    public List<Student> getHonorStudents(){
        List<Student> honorStudents = new ArrayList<>();
        for(Student student:students){
            int counter = 0;
            for (String subject:schoolSubjects){
                if (student.getMarks().get(subject).equals("5")||(student.getMarks().get(subject).equals("ЗТ"))||(student.getMarks().get(subject).equals("4"))){
                    counter++;
                }
            }
            if (counter==schoolSubjects.size()&&student.getAverageScore()<5.0){
                honorStudents.add(student);
            }
        }
        return honorStudents;
    }

    public List<Student> getStudentsWithSingleFour(List<Student> students){
        List<Student> studentsWithSingleFour = new ArrayList<>();
        for(Student student:students){
            int counter = 0;
            for (String subject:schoolSubjects){
                if (student.getMarks().get(subject).equals("4")){
                    counter++;
                }
            }
            if (counter==1){
                studentsWithSingleFour.add(student);
            }
        }
        return studentsWithSingleFour;
    }

    public List<Student> getStudentsWithSingleThree(){
        List<Student> studentsWithSingleThree = new ArrayList<>();
        for(Student student:students){
            int counter = 0;
            int subjectCounter=0;
            for (String subject:schoolSubjects){
                if (student.getMarks().get(subject).equals("3")){
                    counter++;
                } else if (student.getMarks().get(subject).equals("2")||student.getMarks().get(subject).equals("")||
                        student.getMarks().get(subject).equals(" ")||student.getMarks().get(subject).equals("НАН")||
                        student.getMarks().get(subject).equals("НАУ")) {
                    break;
                }
                subjectCounter++;
            }//??
            if (counter==1&&(subjectCounter==schoolSubjects.size())){
                studentsWithSingleThree.add(student);
            }
        }
        return studentsWithSingleThree;
    }

    public List<Student> getStudentsWithNegativeMarks(){
        List<Student> studentsWithNegativeMarks = new ArrayList<>();
        for(Student student:students){
            for (String subject:schoolSubjects){
                if ((student.getMarks().get(subject).equals("2")) || ((student.getMarks().get(subject).equals("НАН")))){
                    if(studentsWithNegativeMarks.contains(student)){
                        break;
                    }else {
                        studentsWithNegativeMarks.add(student);
                    }
                }
            }
        }
        return studentsWithNegativeMarks;
    }


    public float getAcademicPerfomance() {
        return academicPerfomance;
    }
    public void setAcademicPerfomance(float academicPerfomance) {
        this.academicPerfomance = academicPerfomance;
    }
}
