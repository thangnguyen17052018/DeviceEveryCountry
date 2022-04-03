package com.tma.deviceeverycountry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tma.deviceeverycountry.Constant.EXCEL_FILE_PATH;

@Configuration
public class AppConfig {

    @Bean
    public ExcelFileReader createExcelFileReaderBean() {
        return new ExcelFileReader(EXCEL_FILE_PATH);
    }

}
