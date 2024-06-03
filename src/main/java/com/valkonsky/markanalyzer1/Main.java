package com.valkonsky.markanalyzer1;

import com.valkonsky.markanalyzer1.Interfaces.Impl.XlsxReader;
import com.valkonsky.markanalyzer1.Interfaces.Impl.XlsxWriter;
import com.valkonsky.markanalyzer1.entity.School;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            MainApplication.main(args);
            XlsxWriter writer = new XlsxWriter(new School("C:\\Users\\valko\\Desktop\\year.xlsx"));
            writer.write();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
