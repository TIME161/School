package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService {
    Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private Integer port;

    public Integer getPort() {
        logger.debug("Method getPort is Called");
        return port;
    }

    public String comparison() {
        logger.debug("Method comparison is Called");

        long startTimeOriginal = System.currentTimeMillis();
        int sumOriginal = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long endTimeOriginal = System.currentTimeMillis();

        long startTimeModifed = System.currentTimeMillis();
        int sumModifed = IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
        long endTimeModifed = System.currentTimeMillis();

        return "Оригинальный метод получил ответ " + sumOriginal
                + " и выполнился за: " + (endTimeOriginal - startTimeOriginal)
                + " , модифицированный метод получил ответ " + sumModifed
                + " и выполнился за: " + (endTimeModifed - startTimeModifed);

    }

}
