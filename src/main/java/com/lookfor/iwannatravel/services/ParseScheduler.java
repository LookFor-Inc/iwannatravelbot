package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.dto.AvailableCrossingResponse;
import com.lookfor.iwannatravel.dto.CountryRequest;
import com.lookfor.iwannatravel.dto.CountryStatus;
import com.lookfor.iwannatravel.interfaces.AvailableCrossingParser;
import com.lookfor.iwannatravel.models.Trajectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
@ManagedResource(objectName = "ParseMBeans:category=MBeans,name=ParseScheduler")
public class ParseScheduler {
    private final AvailableCrossingParser parser;
    private final TrajectoryService trajectoryService;

    /**
     * Parse every 1 hour
     * @throws ExecutionException exp
     * @throws InterruptedException exp
     */
    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @ManagedOperation(description = "Parse web page for traveling restrictions")
    public void parseWebPage() throws ExecutionException, InterruptedException {
        Collection<String> countriesName = trajectoryService.getAllDepartureCountriesNames();

        for (String countryName : countriesName) {
            log.info("Checking update for {}", countryName);
            Future<AvailableCrossingResponse> future = parser.getResult(new CountryRequest(countryName));
            AvailableCrossingResponse response = future.get();

            List<Trajectory> trajectoryList = trajectoryService.getTrajectoriesByDepartureCountryName(countryName);
            List<CountryStatus> countryStatusList = response.getCountryStatusList();

            for (Trajectory trajectory : trajectoryList) {
                Optional<CountryStatus> statusOptional = countryStatusList.stream()
                        .filter(status -> status.getCountryName().equalsIgnoreCase(trajectory.getArrivalCountry().getEn()))
                        .findFirst();

                if (statusOptional.isPresent()) {
                    CountryStatus status = statusOptional.get();

                    if (trajectory.compareTo(status) != 0) {
                        log.info("Update info for {}: {}", countryName, status);
                        trajectory.setRestricted(!status.isTravel());
                        trajectory.setNote(status.getNote());
                        trajectoryService.save(trajectory);
                    }
                } else { // restricted
                    trajectory.setRestricted(true);
                    trajectory.setNote("The country is closed for tourism");
                    trajectoryService.save(trajectory);
                }
            }
        }
    }
}
