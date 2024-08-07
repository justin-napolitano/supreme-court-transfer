package com.supreme_court_transfer;

import org.neo4j.driver.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClearGraph {
    private static final Logger logger = LoggerFactory.getLogger(ClearGraph.class);

    public static void clearGraph() {
        logger.info("Starting graph clearing process...");

        try (Session session = Neo4jConnection.getSession()) {
            session.writeTransaction(tx -> {
                tx.run("MATCH (n) DETACH DELETE n");
                return null;
            });
            logger.info("Graph cleared.");
        } catch (Exception e) {
            logger.error("An error occurred while clearing the graph.", e);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--cleargraph")) {
            clearGraph();
        } else {
            logger.info("No clear graph flag passed. Exiting without clearing the graph.");
        }
        
        Neo4jConnection.close();
    }
}
