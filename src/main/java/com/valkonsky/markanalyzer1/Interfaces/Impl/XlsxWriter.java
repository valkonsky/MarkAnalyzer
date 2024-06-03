package com.valkonsky.markanalyzer1.Interfaces.Impl;

import com.valkonsky.markanalyzer1.entity.SchoolClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import com.valkonsky.markanalyzer1.entity.School;

public class XlsxWriter implements com.valkonsky.markanalyzer1.Interfaces.Writer {
    public OutputStream os;
    public XSSFWorkbook book;

    public XSSFSheet sheet;

    School school;
    public XlsxWriter() throws IOException {
         os = new FileOutputStream("C:\\Users\\valko\\Desktop\\REPORT.xlsx");
         book = new XSSFWorkbook();
         sheet = book.createSheet("report page");
         write();
    }

    public XlsxWriter(School school) throws IOException {
        os = new FileOutputStream("C:\\Users\\valko\\Desktop\\REPORT.xls");
        book = new XSSFWorkbook();
        sheet = book.createSheet("report page");
        this.school = school;
        write();
    }


    @Override
    public void write() throws IOException {
        int rownum = 0;
        Row row = sheet.createRow(rownum);
        Cell cell = row.createCell(1);
        cell.setCellValue("Справка об успеваемости обучающихся ГБОУ");
        ++rownum;
        Row row2 = sheet.createRow(rownum);
        row2.createCell(0).setCellValue("Классы");
        row2.createCell(1).setCellValue("количество");
        row2.createCell(2).setCellValue("отличники");
        row2.createCell(3).setCellValue("с одной \"4\"");
        row2.createCell(4).setCellValue("с одной \"3\"");
        row2.createCell(5).setCellValue("без \"3\"");
        row2.createCell(6).setCellValue("качество знаний");
        row2.createCell(7).setCellValue("\"2\"");
        ++rownum;
        try {
            writeSchool(new School("C:\\Users\\valko\\Desktop\\year.xlsx"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream out = new FileOutputStream(new File("C:\\Users\\valko\\Desktop\\REPORT2.xls"))) {
            book.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSchoolClass(SchoolClass schoolClass,int rowNum){
        Row row  = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(schoolClass.getSchoolClassName());
        row.createCell(1).setCellValue(schoolClass.getStudents().size());
        row.createCell(2).setCellValue(schoolClass.getExcelentStudents().size());
        row.createCell(3).setCellValue(schoolClass.getStudentsWithSingleFour(schoolClass.getHonorStudents()).size());
        row.createCell(4).setCellValue(schoolClass.getStudentsWithSingleThree().size());
        row.createCell(5).setCellValue(schoolClass.getHonorStudents().size());
        row.createCell(6).setCellValue(schoolClass.getAcademicPerfomance());
        row.createCell(7).setCellValue(schoolClass.getStudentsWithNegativeMarks().toString());
    }

    private void writeSchool(School school){
        for(int i =0;i<school.getSchoolClasses().size();i++){
            writeSchoolClass(school.getSchoolClasses().get(i),i+2);
        }
    }
}
