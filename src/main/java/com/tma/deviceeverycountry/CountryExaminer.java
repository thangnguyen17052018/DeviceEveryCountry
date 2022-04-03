package com.tma.deviceeverycountry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CountryExaminer {

    @Autowired
    private ReverseGeocoder reverseGeocoder;

    public String getBulkPostResponse(String requestBody) throws IOException, InterruptedException {
        return reverseGeocoder.sendPostBatchRequestReverseGeocoding(requestBody).body();
    }

    public String getJobStatus(String requestId) throws IOException, InterruptedException {
        return reverseGeocoder.sendGetRequestJobStatus(requestId).body();
    }

    public String getResult(String requestId) throws IOException, InterruptedException {
        return reverseGeocoder.getResponseResult(requestId).body();
    }
}
