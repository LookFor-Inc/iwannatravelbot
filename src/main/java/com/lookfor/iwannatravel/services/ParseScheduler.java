package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.dto.AvailableCrossingResponse;
import com.lookfor.iwannatravel.dto.CountryRequest;
import com.lookfor.iwannatravel.interfaces.AvailableCrossingParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
@ManagedResource(objectName = "ParseMBeans:category=MBeans,name=ParseScheduler")
public class ParseScheduler {
    private final AvailableCrossingParser parser;
    private final CountryService countryService;
    private final TrajectoryService trajectoryService;

    /**
     * Parse every 1 hour
     * @throws ExecutionException exp
     * @throws InterruptedException exp
     */
    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @ManagedOperation(description = "Parse web page for traveling restrictions")
    public void parseWebPage() throws ExecutionException, InterruptedException {
        Collection<String> countriesName = trajectoryService.getAllArrivalCountriesNames();
//        Collection<String> countriesName = new ArrayList<>() {{
//            add("russia");
//            add("china");
//        }};

        for (String countryName : countriesName) {
            AvailableCrossingResponse response = parser.getResult(new CountryRequest(countryName)).get();
            log.info(response.getCountryStatusList().toString());
        }
    }
}
