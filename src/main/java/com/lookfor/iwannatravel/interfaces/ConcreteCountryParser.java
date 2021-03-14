package com.lookfor.iwannatravel.interfaces;

import com.lookfor.iwannatravel.dto.ParserDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

@Async
public interface ConcreteCountryParser {
    /**
     * Find all information about countries with or without restriction
     * for concrete country
     *
     * @return ParserDto object
     */
    Future<ParserDto> getResult();
}
