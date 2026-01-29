package com.revconnect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RevConnectApplication {

    private static final Logger logger =
            LogManager.getLogger(RevConnectApplication.class);

    public static void main(String[] args) {

        logger.info("RevConnect application starting");
        SpringApplication.run(RevConnectApplication.class, args);
        logger.info("RevConnect application started");
    }
}
