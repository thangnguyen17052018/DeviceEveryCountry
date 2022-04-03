package com.tma.deviceeverycountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.tma.deviceeverycountry.Constant.COUNTRY_NAME_INDEX;
import static com.tma.deviceeverycountry.Constant.SEQ_NUMBER_INDEX;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final CountryExaminer countryExaminer;
    private final CoordinateReader reader;
    private final XMLParser xmlParser;

    @Autowired
    public ApplicationRunner(CountryExaminer countryExaminer, CoordinateReader reader, XMLParser xmlParser) {
        this.countryExaminer = countryExaminer;
        this.reader = reader;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {

        String xmlText = countryExaminer.getBulkPostResponse(reader.createRequestBodyFromExcelFile());

        String requestID = xmlParser.parseXML(xmlText, Constant.REQUEST_ID_TAG);

        String xmlStatusText = countryExaminer.getJobStatus(requestID);
        String status = xmlParser.parseXML(xmlStatusText, Constant.JOB_STATUS_TAG);

        boolean statusIsCompleted = true;

        while (!Constant.COMPLETED_STATUS.equals(status)) {
            xmlStatusText = countryExaminer.getJobStatus(requestID);
            status = xmlParser.parseXML(xmlStatusText, Constant.JOB_STATUS_TAG);

            if (Constant.DELETED_STATUS.equals(status) || Constant.CANCELLED_STATUS.equals(status)) {
                statusIsCompleted = false;
                break;
            }
        }

        if (statusIsCompleted) {
            String result = countryExaminer.getResult(requestID);
            String[] recordLines = result.split(Constant.BREAK_LINE_REGEX);
            Map<String, Integer> devicesEveryCountryMap = new HashMap<>();
            long countDevicesInUsed = 0L;

            for (String recordLine : recordLines) {
                String[] record = recordLine.split(Constant.OUTPUT_FIELD_DELIMITER);
                String seqNumber = record[SEQ_NUMBER_INDEX];

                Function<String, Boolean> getOneResultAmongAmbiguousResults = "1"::equals;
                if (getOneResultAmongAmbiguousResults.apply(seqNumber)) {
                    String countryName = record[COUNTRY_NAME_INDEX];
                    int count = devicesEveryCountryMap.getOrDefault(countryName, 0);
                    devicesEveryCountryMap.put(countryName, ++count);
                    countDevicesInUsed++;
                }
            }

            System.out.println("All " + reader.getNumberOfDevices() + " devices which " + countDevicesInUsed + " devices in used");

            devicesEveryCountryMap.forEach((countryName, numberOfDevices) -> System.out.println(countryName + ": " + numberOfDevices + ((numberOfDevices > 1) ? " devices" : " device")));
        } else {
            System.out.println("Failed to running batch jobs");
        }
    }


}
