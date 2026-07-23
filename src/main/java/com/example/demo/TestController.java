package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/working")
    public String working() {
        logger.info("Executing /working endpoint successfully");
        return "Service working normally!";
    }

    @GetMapping("/breaking")
    public String breaking() {
        logger.error("Executing /breaking endpoint - throwing error log");
        throw new RuntimeException("Service broke intentional failure!");
    }
}
