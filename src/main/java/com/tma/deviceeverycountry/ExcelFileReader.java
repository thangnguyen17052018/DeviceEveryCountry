package com.tma.deviceeverycountry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelFileReader {

    private Workbook workbook = null;

    public ExcelFileReader(String filePath) {
        try {
            File source = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(source);
            workbook = WorkbookFactory.create(fileInputStream);
        } catch (IOException | InvalidFormatException exception) {
            exception.printStackTrace();
        }
    }

    public double getData(int sheetIndex, int row, int column) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.getRow(row).getCell(column).getNumericCellValue();
    }

    public int getRowCount(int sheetIndex) {
        int row = workbook.getSheetAt(sheetIndex).getLastRowNum();
        return ++row;
    }

}
