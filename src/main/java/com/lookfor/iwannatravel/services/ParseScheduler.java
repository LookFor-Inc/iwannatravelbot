package com.lookfor.iwannatravel.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
@ManagedResource(objectName = "ParseMBeans:category=MBeans,name=ParseScheduler")
public class ParseScheduler {
    private final CountryService countryService;

    /**
     * Parse every 1 hour
     * @throws ExecutionException exp
     * @throws InterruptedException exp
     */
    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @ManagedOperation(description = "Parse web page for traveling restrictions")
    public void parseWebPage() throws ExecutionException, InterruptedException {

    }
}
