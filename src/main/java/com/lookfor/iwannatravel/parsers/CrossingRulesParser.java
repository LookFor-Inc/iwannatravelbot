package com.lookfor.iwannatravel.parsers;

import com.lookfor.iwannatravel.dto.CountryRequest;
import com.lookfor.iwannatravel.dto.AvailableCrossingResponse;
import com.lookfor.iwannatravel.dto.CountryStatus;
import com.lookfor.iwannatravel.interfaces.AvailableCrossingParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Component
public class CrossingRulesParser implements AvailableCrossingParser {
    private static final String URL = "https://travelbans.org/crossing-rules/?from=";

    @Override
    public Future<AvailableCrossingResponse> getResult(CountryRequest request) {
        log.info("CrossingRulesParser parser started...");
        CompletableFuture<AvailableCrossingResponse> future = new CompletableFuture<>();

        try {
            Document doc = Jsoup.connect(getCountryUrl(request.getCountryName())).get();
            future.complete(new AvailableCrossingResponse(request.getCountryName(), getCountryStatusList(doc)));
        } catch (IOException e) {
            log.error("CrossingRulesParser parser: " + e.getMessage());
        } finally {
            log.info("CrossingRulesParser parser has finished working");
        }

        return future;
    }

    /**
     * Get list of the CountryStatus object from document
     *
     * @param doc Jsoup document
     * @return CountryStatus object
     */
    private List<CountryStatus> getCountryStatusList(Document doc) {
        List<CountryStatus> list = new ArrayList<>();
        Elements countries = doc.select(".item.post_item.crossingrule");

        countries.forEach(c -> {
            String name = c.child(0).text();
            String note = c.child(1).children().select("span").text();
            list.add(new CountryStatus(name, true, note));
        });

        return list;
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
