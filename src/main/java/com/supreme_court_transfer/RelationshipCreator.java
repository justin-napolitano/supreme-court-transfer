package com.supreme_court_transfer;

import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RelationshipCreator {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipCreator.class);

    public static void createRelationships() {
        logger.info("Starting relationship creation process...");

        try (Session session = Neo4jConnection.getSession()) {
            // Example relationship creation logic
            createCallNumberToItemRelationships(session);
            createContributorToResourceRelationships(session);
            createItemToSubjectRelationships(session);

            // Add more relationship creation methods as needed
        } catch (Exception e) {
            logger.error("An error occurred during the relationship creation process.", e);
        }

        logger.info("Relationship creation process completed.");
    }

    private static void createCallNumberToItemRelationships(Session session) {
        session.writeTransaction(new TransactionWork<Void>() {
            @Override
            public Void execute(Transaction tx) {
                tx.run("MATCH (c:CallNumber), (i:Item) " +
                       "WHERE c.externalId = i.callNumber " +
                       "CREATE (c)-[:ASSOCIATED_WITH]->(i)");
                logger.info("Created relationships between CallNumbers and Items based on external_id.");
                return null;
            }
        });
    }

    private static void createResourceToItemRelationships(Session session) {
        session.writeTransaction(new TransactionWork<Void>() {
            @Override
            public Void execute(Transaction tx) {
                tx.run("MATCH (r:Resource), (i:item) " +
                       "WHERE r.externalId = i.externalId " +
                       "CREATE (r)-[:RESOURCE_OF]->(i)");
                logger.info("Created relationships between Resources and Items based on external_id.");
                return null;
            }
        });
    }

    private static void createContributorToItemRelationships(Session session) {
        session.writeTransaction(new TransactionWork<Void>() {
            @Override
            public Void execute(Transaction tx) {
                tx.run("MATCH (c:Contributor), (i:item) " +
                       "WHERE c.externalId = i.externalId " +
                       "CREATE (c)-[:CONTRIBUTED_TO]->(I)");
                logger.info("Created relationships between Contributors and Items based on external_id.");
                return null;
            }
        });
    }

    private static void createSubjectToItemRelationships(Session session) {
        session.writeTransaction(new TransactionWork<Void>() {
            @Override
            public Void execute(Transaction tx) {
                tx.run("MATCH (s:Subject), (i:Item) " +
                       "WHERE s.externalId = i.externalId " +
                       "CREATE (s)-[:SUBJECT_OF]->(i)");
                logger.info("Created relationships between Subjects and Items based on external_id.");
                return null;
            }
        });
    }

    public static void main(String[] args) {
        createRelationships();
        Neo4jConnection.close();
    }
}
