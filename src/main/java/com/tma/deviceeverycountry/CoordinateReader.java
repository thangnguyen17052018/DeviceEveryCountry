package com.tma.deviceeverycountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CoordinateReader {
    public static final int LATITUDE_COLUMN = 8;
    public static final int LONGITUDE_COLUMN = 9;
    public static final int SHEET_DATA_INDEX = 1;
    public static final int REPORT_DATA_INDEX = 1;

    private final ExcelFileReader reader;

    @Autowired
    public CoordinateReader(ExcelFileReader reader) {
        this.reader = reader;
    }

    public String createRequestBodyFromExcelFile() {
        int rows = reader.getRowCount(SHEET_DATA_INDEX);
        StringBuilder bodyString = new StringBuilder("recId|prox\n");

        for (int i = 1; i < rows; i++) {
            double latitude = reader.getData(REPORT_DATA_INDEX, i, LATITUDE_COLUMN);
            double longitude = reader.getData(REPORT_DATA_INDEX, i, LONGITUDE_COLUMN);
            Coordinate coordinate = new Coordinate(latitude, longitude);
            if (!coordinate.isNullIsLand()) {
                bodyString.append(String.format("%d|%s,%s,0\n", i + 1, latitude, longitude));
            }
        }

        return bodyString.toString();
    }

    public int getNumberOfDevices() {
        return reader.getRowCount(SHEET_DATA_INDEX) - 1;
    }

}
