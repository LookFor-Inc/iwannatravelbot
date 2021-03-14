//package com.lookfor.iwannatravel.services;
//
//import com.lookfor.iwannatravel.dto.CountryDto;
//import com.lookfor.iwannatravel.dto.ParserDto;
//import com.lookfor.iwannatravel.parsers.OneToTripParser;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jmx.export.annotation.ManagedOperation;
//import org.springframework.jmx.export.annotation.ManagedResource;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@ManagedResource(objectName = "ParseMBeans:category=MBeans,name=ParseScheduler")
//public class ParseScheduler {
//    private final CountryService countryService;
//    private final OneToTripParser oneToTripParser;
//
//    /**
//     * Parse every 1 hour
//     * @throws ExecutionException exp
//     * @throws InterruptedException exp
//     */
//    @Scheduled(cron = "0 0 0/1 1/1 * ?")
//    @ManagedOperation(description = "Parse web page for traveling restrictions")
//    public void parseWebPage() throws ExecutionException, InterruptedException {
//        ParserDto data = oneToTripParser.getResult().get();
//        log.info(String.valueOf(data));
//        List<CountryDto> countryDtoList = data.getCountryDtoList();
//
//        if (!countryDtoList.isEmpty()) {
//            countryService.saveOrUpdateAll(countryDtoList);
//        }
//    }
//}
