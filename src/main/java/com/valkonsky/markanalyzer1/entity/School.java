package com.valkonsky.markanalyzer1.entity;

import com.valkonsky.markanalyzer1.Interfaces.Impl.XlsxReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class School {
    private List <SchoolClass> schoolClasses;
    private XlsxReader reader;
    private double academicPerfomance;
    private Map schoolClassesInMap;
    public School(String s) throws IOException {
        reader = new XlsxReader(s);
        schoolClasses = new ArrayList<>();
        schoolClassesInMap = new LinkedHashMap();
        initializeSchoolClasses();
        initializeSchoolSubjects();
        initializeStudents();
        initializeMarks();
        initLinkedHashMap(getSchoolClassNames(),getSchoolClasses());
        initializeAcademicPerfomance();
    }

    public Map getSchoolClassesInMap() {
        return schoolClassesInMap;
    }
    private void initializeSchoolClasses(){
        Iterator<Sheet> sheetIterator = reader.getBook().sheetIterator();
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            schoolClasses.add(new SchoolClass(sheet.getSheetName()));
        }
    }

    private List<String> getSchoolClassNames(){
        List<String> schoolClassNames = new ArrayList<>();
        Iterator<Sheet> sheetIterator = reader.getBook().sheetIterator();
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            schoolClassNames.add(sheet.getSheetName());
        }
        return schoolClassNames;
    }
    private void initializeStudents(){
        Iterator<Sheet> sheetIterator = reader.getBook().sheetIterator();
        int i =0;

        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            Iterator <Row> iterator = sheet.iterator();
            List<Cell> list = new ArrayList<>();
            while (iterator.hasNext()){
                List<Cell> marks = new ArrayList<>();//marks
                Row row = iterator.next();
                list.add((row.getCell(0)));
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    marks.add(cell);
                }
            }
            List<Cell> finishlist = list.subList(4,list.size()-7);
            List <Student> students = new ArrayList<>();
            for(int j =0; j<finishlist.size();j++){
                students.add(new Student(finishlist.get(j).getStringCellValue()));
            }
            schoolClasses.get(i).setStudents(students);
            i++;
        }
    }
    private void initializeSchoolSubjects(){
        Iterator<Sheet> sheetIterator = reader.getBook().sheetIterator();
        int i =0;

        while (sheetIterator.hasNext()){
            List<String> subjects = new ArrayList<>();
            Sheet sheet = sheetIterator.next();
            Row row = sheet.getRow(5);
            Iterator <Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                subjects.add(cell.getStringCellValue());
            }
            List<String> finishlist = subjects.subList(1,subjects.size()-7);
            schoolClasses.get(i).setSchoolSubjects(finishlist);
            i++;
        }
    }
    private void initializeMarks(){
        Iterator<Sheet> sheetIterator = reader.getBook().sheetIterator();
        int i = 0;
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            for(int j=0;j<=schoolClasses.get(i).getStudents().size()-1;j++){
                Row row = sheet.getRow(6+j);
                List<String> marks = new ArrayList<>();
                for (int k = 0;k<schoolClasses.get(i).getSchoolSubjects().size();k++){
                    Cell cell = row.getCell(1+k);
                    marks.add(cell.getStringCellValue());
                }
                schoolClasses.get(i).getStudents().get(j).setMarks(listToMap(schoolClasses.get(i).getSchoolSubjects(),marks));
                initializeAverageScoreForStudent(schoolClasses.get(i).getStudents().get(j));
            }
        i++;
        }
    }

    private void initializeAverageScoreForStudent(Student student){
        double averageMark=0;
        double allSum=0;
        ArrayList<String> marks = new ArrayList<>(student.getMarks().values());
        for(String mark:marks){
            if (mark.equals("ЗТ")){
                allSum+=5.0;
            } else if (mark.equals("НАН")||mark.equals("НАУ")||mark.equals("")||mark.equals(" ")) {
                allSum+=0;
            } else {
                allSum += Double.parseDouble(mark);
            }
        }
        student.setAverageScore(allSum/student.getMarks().size());
    }
    public List<Student> getUnmarkedStudents(){
        List<Student> students = new ArrayList<>();
        for(int i=0;i<schoolClasses.size();i++){
            for(int j=0;j<schoolClasses.get(i).getStudents().size();j++){
                if (schoolClasses.get(i).getStudents().get(j).getMarks().containsValue("")){
                    students.add(schoolClasses.get(i).getStudents().get(j));
                    break;//?
                }
            }
        }
        return students;
    }
    public List<Student> getUnAttestedUnrespectedStudents(){
        List<Student> students = new ArrayList<>();
        for(int i=0;i<schoolClasses.size();i++){
            for(int j=0;j<schoolClasses.get(i).getStudents().size();j++){
                if (schoolClasses.get(i).getStudents().get(j).getMarks().containsValue("НАН")){
                    students.add(schoolClasses.get(i).getStudents().get(j));
                }
            }
        }
        return students;
    }
    public List<Student> getUnAttestedRespectedStudents(){
        List<Student> students = new ArrayList<>();
        for(int i=0;i<schoolClasses.size();i++){
            for(int j=0;j<schoolClasses.get(i).getStudents().size();j++){
                if (schoolClasses.get(i).getStudents().get(j).getMarks().containsValue("НАУ")){
                    students.add(schoolClasses.get(i).getStudents().get(j));
                }
            }
        }
        return students;
    }
    private Map<String, String> listToMap(List keys, List values){
        Map<String,String> result = new HashMap<>();
        for (int i =0;i<keys.size();i++){
            result.put((String) keys.get(i), (String) values.get(i));
        }
        return result;
    }
    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public int getCountOfStudents(){
        int allStudents = 0;
        for(int i =0;i<schoolClasses.size();i++){
            allStudents += schoolClasses.get(i).getStudents().size();
        }
        return allStudents;
    }

    public void initializeAcademicPerfomance() {
        for (SchoolClass schoolClass:schoolClasses){
            double excelent = schoolClass.getExcelentStudents().size();
            double honor = schoolClass.getHonorStudents().size();
            double allInClass = schoolClass.getStudents().size();
            academicPerfomance = ((((excelent+honor)/allInClass))*100.0);
            academicPerfomance = Math.round(academicPerfomance*100.0)/100.0;
            schoolClass.setAcademicPerfomance((float) academicPerfomance);
        }
    }

    public void initLinkedHashMap(List<String> keys, List<SchoolClass> values){
        schoolClassesInMap = IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }
}
