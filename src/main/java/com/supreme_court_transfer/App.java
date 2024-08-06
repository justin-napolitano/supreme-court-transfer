package com.supreme_court_transfer;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Log to verify environment variables (optional, for debugging purposes)
        logger.debug("Postgres URL: {}", dotenv.get("POSTGRES_URL"));
        logger.debug("Neo4j URL: {}", dotenv.get("NEO4J_URL"));

        int limit = Integer.MAX_VALUE; // Default to no limit

        if (args.length > 0) {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.error("Invalid limit argument, using default value (no limit).", e);
            }
        }

        try {
            logger.info("Starting data transfer process with limit {}...", limit);
            DataTransferService.transferCallNumbers(limit);
            DataTransferService.transferContributors(limit);
            DataTransferService.transferResources(limit);
            DataTransferService.transferItems(limit);
            DataTransferService.transferSubjects(limit);
            logger.info("Data transfer process completed.");
        } catch (Exception e) {
            logger.error("An error occurred during the data transfer process.", e);
        } finally {
            Neo4jConnection.close();
        }
    }
}
