package com.supreme_court_transfer;

import org.neo4j.driver.Session;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RelationshipCreator {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipCreator.class);

    public static void createRelationships(int limit) {
        logger.info("Starting relationship creation process with limit {}...", limit);

        try (Session session = Neo4jConnection.getSession()) {
            relateItemsToSubjects(session, limit);
            relateItemsToCallNumbers(session, limit);
            relateItemsToContributors(session, limit);
            relateItemsToResources(session, limit);
        } catch (Exception e) {
            logger.error("An error occurred during the relationship creation process.", e);
        }

        logger.info("Relationship creation process completed.");
    }

    private static void relateItemsToSubjects(Session session, int limit) throws Exception {
        logger.info("Starting to relate items to subjects...");

        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT i.external_id, s.subject FROM items i LEFT JOIN subjects s ON i.external_id = s.external_id LIMIT " + limit);

        int count = 0;
        while (rs.next()) {
            String externalId = rs.getString("external_id");
            String subject = rs.getString("subject");

            if (subject != null) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MATCH (i:Item {externalId: $externalId}), (s:Subject {subject: $subject}) " +
                            "CREATE (i)-[:HAS_SUBJECT]->(s)",
                            Values.parameters(
                                    "externalId", externalId,
                                    "subject", subject
                            ));
                    return null;
                });
                count++;
                logger.debug("Created relationship between Item (externalId: {}) and Subject (subject: {}).", externalId, subject);
            }
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Related {} items to their subjects.", count);
    }

    private static void relateItemsToCallNumbers(Session session, int limit) throws Exception {
        logger.info("Starting to relate items to call numbers...");

        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT i.external_id, c.call_number FROM items i LEFT JOIN callnumbers c ON i.external_id = c.external_id LIMIT " + limit);

        int count = 0;
        while (rs.next()) {
            String externalId = rs.getString("external_id");
            String callNumber = rs.getString("call_number");

            if (callNumber != null) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MATCH (i:Item {externalId: $externalId}), (c:CallNumber {callNumber: $callNumber}) " +
                            "CREATE (i)-[:HAS_CALL_NUMBER]->(c)",
                            Values.parameters(
                                    "externalId", externalId,
                                    "callNumber", callNumber
                            ));
                    return null;
                });
                count++;
                logger.debug("Created relationship between Item (externalId: {}) and CallNumber (callNumber: {}).", externalId, callNumber);
            }
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Related {} items to their call numbers.", count);
    }

    private static void relateItemsToContributors(Session session, int limit) throws Exception {
        logger.info("Starting to relate items to contributors...");

        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT i.external_id, c.contributor FROM items i LEFT JOIN contributors c ON i.external_id = c.external_id LIMIT " + limit);

        int count = 0;
        while (rs.next()) {
            String externalId = rs.getString("external_id");
            String contributor = rs.getString("contributor");

            if (contributor != null) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MATCH (i:Item {externalId: $externalId}), (c:Contributor {contributor: $contributor}) " +
                            "CREATE (i)-[:HAS_CONTRIBUTOR]->(c)",
                            Values.parameters(
                                    "externalId", externalId,
                                    "contributor", contributor
                            ));
                    return null;
                });
                count++;
                logger.debug("Created relationship between Item (externalId: {}) and Contributor (contributor: {}).", externalId, contributor);
            }
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Related {} items to their contributors.", count);
    }

    private static void relateItemsToResources(Session session, int limit) throws Exception {
        logger.info("Starting to relate items to resources...");

        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT i.external_id, r.pdf, r.image FROM items i LEFT JOIN resources r ON i.external_id = r.external_id LIMIT " + limit);

        int count = 0;
        while (rs.next()) {
            String externalId = rs.getString("external_id");
            String pdf = rs.getString("pdf");
            String image = rs.getString("image");

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run("MATCH (i:Item {externalId: $externalId}), (r:Resource {externalId: $externalId}) " +
                        "CREATE (i)-[:HAS_RESOURCE]->(r)",
                        Values.parameters(
                                "externalId", externalId
                        ));
                return null;
            });
            count++;
            logger.debug("Created relationship between Item (externalId: {}) and Resource (externalId: {}).", externalId, externalId);

            if (pdf != null) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MATCH (i:Item {externalId: $externalId}), (r:Resource {pdf: $pdf}) " +
                            "CREATE (i)-[:HAS_PDF]->(r)",
                            Values.parameters(
                                    "externalId", externalId,
                                    "pdf", pdf
                            ));
                    return null;
                });
                logger.debug("Created relationship between Item (externalId: {}) and Resource (pdf: {}).", externalId, pdf);
            }

            if (image != null) {
                session.writeTransaction((TransactionWork<Void>) tx -> {
                    tx.run("MATCH (i:Item {externalId: $externalId}), (r:Resource {image: $image}) " +
                            "CREATE (i)-[:HAS_IMAGE]->(r)",
                            Values.parameters(
                                    "externalId", externalId,
                                    "image", image
                            ));
                    return null;
                });
                logger.debug("Created relationship between Item (externalId: {}) and Resource (image: {}).", externalId, image);
            }
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Related {} items to their resources.", count);
    }

    public static void main(String[] args) {
        int limit = Integer.MAX_VALUE; // Default to no limit
        if (args.length > 0) {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.error("Invalid limit argument, using default value (no limit).", e);
            }
        }

        createRelationships(limit);
        Neo4jConnection.close();
    }
}
