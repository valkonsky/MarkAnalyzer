package com.valkonsky.markanalyzer1.Interfaces.Impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class XlsxReader implements com.valkonsky.markanalyzer1.Interfaces.Reader {

    private InputStream is;

    public XSSFWorkbook getBook() {

        return book;
    }

    private XSSFWorkbook book;
    private XSSFSheet sheet;

    public XlsxReader(String path) throws IOException {
        is = new FileInputStream(new File(path));
        book = new XSSFWorkbook(is);
        sheet = book.getSheetAt(0);
    }
    @Override
    public void read() {
        Iterator<Row> rowIterator = sheet.iterator();
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
            Iterator <Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                System.out.println(cell);
            }
        }

    }
}
