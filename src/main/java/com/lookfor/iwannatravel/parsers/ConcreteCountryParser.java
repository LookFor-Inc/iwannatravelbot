package com.lookfor.iwannatravel.parsers;

import com.lookfor.iwannatravel.dto.ConcreteCountryResponse;
import com.lookfor.iwannatravel.dto.CountryRequest;
import com.lookfor.iwannatravel.interfaces.CountryParser;
import com.lookfor.iwannatravel.models.AllowStatus;
import com.lookfor.iwannatravel.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Component
public class ConcreteCountryParser implements CountryParser {
    private static final String URL = "https://travelbans.org/europe/";

    @Override
    public Future<ConcreteCountryResponse> getResult(CountryRequest request) {
        log.info("ConcreteCountryParser parser started...");
        CompletableFuture<ConcreteCountryResponse> future = new CompletableFuture<>();

        try {
            Document doc = Jsoup.connect(getCountryUrl(request.getCountryName())).get();
            ConcreteCountryResponse response = getCountryResponse(doc);
            response.setCountryName(request.getCountryName());
            future.complete(response);
        } catch (IOException e) {
            log.error("ConcreteCountryParser parser: " + e.getMessage());
        } finally {
            log.info("ConcreteCountryParser parser has finished working");
        }

        return future;
    }

    /**
     * Get ConcreteCountryResponse object from document
     *
     * @param doc Jsoup document
     * @return ConcreteCountryResponse object
     */
    private ConcreteCountryResponse getCountryResponse(Document doc) {
        Elements content = doc.select("#content");
        Date lastUpdate = getLastUpdate(doc);
        Elements statuses = content.select(".statuses");
        AllowStatus citizens = getAllowStatus(statuses, ".type-open_for_citizens > span");
        AllowStatus foreigners = getAllowStatus(statuses, ".type-open_for_foreigners > span");
        AllowStatus tourism = getAllowStatus(statuses, ".type-open_for_tourism > span");
        AllowStatus quarantine = getAllowStatus(statuses, ".type-quarantine > span");

        Element table = content.select("table.params").first();
        String crossingRules = getTableValue(table, ".crossing_rules");
        String flightRestrictions = getTableValue(table, ".airlines_updates");
        String quarantineNote = getTableValue(table, ".quarantine");

        return ConcreteCountryResponse.builder()
                .lastUpdate(lastUpdate)
                .citizens(citizens)
                .foreigners(foreigners)
                .tourism(tourism)
                .quarantine(quarantine)
                .crossingRules(crossingRules)
                .flightRestrictions(flightRestrictions)
                .quarantineNote(quarantineNote)
                .build();
    }

    /**
     * Get information about the last data update
     *
     * @param doc Jsoup document
     * @return date of last update
     */
    private Date getLastUpdate(Document doc) {
        String lastUpdate = doc.select("time.date").text();
        return DateUtil.stringToDate(lastUpdate);
    }

    /**
     * Get allow status from labels
     *
     * @param statuses Jsoup elements
     * @param selector items
     * @return AllowStatus object
     */
    private AllowStatus getAllowStatus(Elements statuses, String selector) {
        return AllowStatus.value(statuses.select(selector).text());
    }

    /**
     * Get value from table
     *
     * @param table Jsoup element
     * @param selector items
     * @return table value
     */
    private String getTableValue(Element table, String selector) {
        String result = table.select(selector + " .spoiler-inner").text();

        if (result.isBlank()) {
            result = table.select(selector + " td").get(1).text();
        }

        return result;
    }

    /**
     * Prepare Url for parser
     *
     * @param country name
     * @return full Url
     */
    private String getCountryUrl(String country) {
        return URL + country;
    }
}
