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
        boolean clearGraph = false;    // Default to not clearing the graph
        boolean createRelationships = false; // Default to not creating relationships

        // Process command line arguments
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--cleargraph")) {
                clearGraph = true;
            } else if (arg.equalsIgnoreCase("--createrelationships")) {
                createRelationships = true;
            } else {
                try {
                    limit = Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    logger.error("Invalid limit argument, using default value (no limit).", e);
                }
            }
        }

        try {
            if (clearGraph) {
                logger.info("Clearing the graph...");
                ClearGraph.clearGraph();
                logger.info("Graph cleared.");
            }

            logger.info("Starting data transfer process with limit {}...", limit);
            // DataTransferService.transferCallNumbers(limit);
            DataTransferService.transferContributors(limit);
            // DataTransferService.transferResources(limit);
            DataTransferService.transferItems(limit);
            DataTransferService.transferSubjects(limit);
            logger.info("Data transfer process completed.");

            if (createRelationships) {
                logger.info("Creating relationships...");
                RelationshipCreator.createRelationships(limit);
                logger.info("Relationships created.");
            }
        } catch (Exception e) {
            logger.error("An error occurred during the process.", e);
        } finally {
            Neo4jConnection.close();
        }
    }
}
