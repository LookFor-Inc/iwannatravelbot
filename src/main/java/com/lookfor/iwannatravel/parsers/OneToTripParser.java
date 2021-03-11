package com.lookfor.iwannatravel.parsers;

import com.lookfor.iwannatravel.dto.ParserDto;
import com.lookfor.iwannatravel.interfaces.Parser;
import com.lookfor.iwannatravel.dto.CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OneToTripParser implements Parser {
    private static final String URL = "https://www.onetwotrip.com/ru/blog/journeys/countries-that-are-open-to-tourists-from-russia/";
    private static final String TOURISM = "Туристические поездки:";
    private static final String DOCUMENTS = "Дополнительные документы:";
    private static final String QUARANTINE = "Карантин:";

    @Override
    public Future<ParserDto> getResult() {
        CompletableFuture<ParserDto> completableFuture = new CompletableFuture<>();

        try {
            Document doc = Jsoup.connect(URL).get();
            ParserDto dto = new ParserDto();
            dto.setLastUpdate(getDate(doc));
            dto.setCountryDtoList(parseCountries(doc));

            completableFuture.complete(dto);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return completableFuture;
    }

    private Date getDate(Document doc) {
        String lastUpdate = doc.select("article header time").text();
        return stringToDate(lastUpdate);
    }

    private List<CountryDto> parseCountries(Document doc) {
        List<CountryDto> list = new ArrayList<>();
        Elements section = doc.select("section");
        Elements countries = section.select("h3");
        // Delete "Не знаете, куда поехать?" value
        countries.remove(countries.size() - 1);

        countries.forEach(c -> {
            Element current = c.nextElementSibling();
            CountryDto dto = new CountryDto();
            StringJoiner note = new StringJoiner(" ");

            while (!current.tagName().equals("h3")) {
                if (!current.tagName().equals("p")) {
                    current = current.nextElementSibling();
                    continue;
                }

                Elements properties = current.children().select("strong");
                properties = properties.stream().filter(p -> p.children().size() == 0).collect(Collectors.toCollection(Elements::new));

                // note property
                if (properties.size() == 0) {
                    note.add(current.text());
                } else { // other properties
                    properties.forEach(p -> {
                        String propName = p.text();
                        Node nextSibling = p.parent().nextSibling();
                        String propValue = nextSibling.toString();

                        switch (propName) {
                            case TOURISM -> {
                                boolean status = !propValue.startsWith("не");
                                dto.setTourism(status);
                            }
                            case DOCUMENTS -> dto.setDocuments(propValue.trim());
                            case QUARANTINE -> {
                                dto.setQuarantine(true);
                                System.out.println(c.text());

                                if (propValue.isBlank()) {
                                    propValue = p.nextSibling().toString();
                                }

                                if (propValue.matches("(.*)\\d+ дней(.*)")) {
                                    int days = Integer.parseInt(propValue.replaceAll("\\D+", ""));
                                    dto.setQuarantineDays(days);
                                } else {
                                    dto.setQuarantineNote(propValue.trim());
                                }
                            }
                        }
                    });
                }

                System.out.println(c.text() + ": " + current);
                current = current.nextElementSibling();
            }

            dto.setName(c.text());
            dto.setNote(note.toString());
            list.add(dto);
        });

        return list;
    }

    private Date stringToDate(String str) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;

        try {
            date = inputFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
