package com.lookfor.iwannatravel.interfaces;

import com.lookfor.iwannatravel.dto.AvailableCrossingRequest;
import com.lookfor.iwannatravel.dto.AvailableCrossingResponse;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

@Async
public interface AvailableCrossingParser {
    /**
     * Find all available crossing countries
     *
     * @param request with the name of the country of departure
     * @return list of allowed countries
     */
    Future<AvailableCrossingResponse> getResult(AvailableCrossingRequest request);
}
