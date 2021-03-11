package com.lookfor.iwannatravel.interfaces;

import com.lookfor.iwannatravel.dto.ParserDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface Parser {
    @Async
    Future<ParserDto> getResult();
}
