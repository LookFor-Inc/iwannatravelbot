package com.lookfor.iwannatravel.services;

import com.lookfor.iwannatravel.bot.messages.StatusChangeMessages;
import com.lookfor.iwannatravel.dto.AvailableCrossingResponse;
import com.lookfor.iwannatravel.dto.CountryRequest;
import com.lookfor.iwannatravel.dto.CountryStatus;
import com.lookfor.iwannatravel.interfaces.AvailableCrossingParser;
import com.lookfor.iwannatravel.interfaces.MessageSender;
import com.lookfor.iwannatravel.models.Country;
import com.lookfor.iwannatravel.models.Trajectory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
@ManagedResource(objectName = "ParseMBeans:category=MBeans,name=ParseScheduler")
public class ParseScheduler {
    private final AvailableCrossingParser parser;
    private final CountryService countryService;
    private final TrajectoryService trajectoryService;
    private final MessageSender messageSender;

    /**
     * Parse every 1 hour
     *
     * @throws ExecutionException   exp
     * @throws InterruptedException exp
     */
    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @ManagedOperation(description = "Parse web page for traveling restrictions")
    public void parseWebPage() throws ExecutionException, InterruptedException, TelegramApiException {
        Collection<String> countriesName = trajectoryService.getAllDepartureCountriesNames();

        for (String countryName : countriesName) {
            List<Trajectory> trajectoryList = trajectoryService.getTrajectoriesByDepartureCountryName(countryName);
            startParser(countryName, trajectoryList);
        }
    }

    @Async
    public void startParserWithChecks(int userId, String departureCountyName, String arrivalCountryName) throws ExecutionException, InterruptedException, TelegramApiException {
        Optional<Country> arrivalOptional = countryService.findCountryByName(arrivalCountryName);

        if (arrivalOptional.isPresent()) {
            String arrivalName = arrivalOptional.get().getEn();
            log.info("Check trajectory: {} -> {}", departureCountyName, arrivalName);
            Optional<Trajectory> trajectoryOptional = trajectoryService.getTrajectoryByDepartureCountryEnAndArrivalCountryEn(departureCountyName, arrivalName);

            if (trajectoryOptional.isPresent()) {
                Trajectory trajectory = trajectoryOptional.get();

                if (trajectory.getNote() != null) {
                    String message = !trajectory.isRestricted()
                            ? StatusChangeMessages.getTravelAllowMessage(arrivalName, trajectory.getNote())
                            : StatusChangeMessages.getRestrictionsMessage(arrivalName);
                    messageSender.sendToUser(userId, message);
                    return;
                }

                startParser(departureCountyName, Collections.singletonList(trajectory));
            }
        }
    }

    private void startParser(String countryName, List<Trajectory> trajectoryList) throws ExecutionException, InterruptedException, TelegramApiException {
        log.info("Checking update for {}", countryName);
        Future<AvailableCrossingResponse> future = parser.getResult(new CountryRequest(countryName));
        AvailableCrossingResponse response = future.get();

        List<CountryStatus> countryStatusList = response.getCountryStatusList();

        for (Trajectory trajectory : trajectoryList) {
            String arrivalCountryName = trajectory.getArrivalCountry().getEn();
            Collection<Integer> usersIds = trajectoryService.getUsersIdsByTrajectoryId(trajectory.getId());
            Optional<CountryStatus> statusOptional = countryStatusList.stream()
                    .filter(status -> status.getCountryName().equalsIgnoreCase(arrivalCountryName))
                    .findFirst();

            if (statusOptional.isPresent()) {
                CountryStatus status = statusOptional.get();

                if (trajectory.compareTo(status) != 0) {
                    log.info("Update info for {} - {}: {}", countryName, arrivalCountryName, status);
                    trajectory.setRestricted(!status.isTravel());
                    trajectory.setNote(status.getNote());
                    trajectoryService.save(trajectory);

                    String message = status.isTravel()
                            ? StatusChangeMessages.getTravelAllowMessage(arrivalCountryName, status.getNote())
                            : StatusChangeMessages.getRestrictionsMessage(arrivalCountryName);
                    messageSender.sendToUsers(usersIds, message);
                }
            } else { // restricted
                if (!trajectory.isRestricted()) {
                    log.info("Insert restriction info for {} -> {}", countryName, arrivalCountryName);
                    trajectory.setRestricted(true);
                    trajectory.setNote("The country is closed for tourism");
                    trajectoryService.save(trajectory);

                    messageSender.sendToUsers(usersIds, StatusChangeMessages.getRestrictionsMessage(arrivalCountryName));
                }
            }
        }
    }
}
