package com.supreme_court_transfer;

import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DataTransferService {
    private static final Logger logger = LoggerFactory.getLogger(DataTransferService.class);

    // Fetch unique items from PostgreSQL
    public static Set<Item> fetchUniqueItemsFromPostgres(int limit) throws Exception {
        Set<Item> items = new HashSet<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT external_id, title, call_number, date FROM items LIMIT " + limit);

        while (rs.next()) {
            items.add(new Item(
                    rs.getString("external_id"),
                    rs.getString("title"),
                    rs.getString("call_number"),
                    rs.getString("date")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} unique items from PostgreSQL.", items.size());
        return items;
    }

    // Fetch unique call numbers from PostgreSQL
    public static Set<CallNumber> fetchUniqueCallNumbersFromPostgres(int limit) throws Exception {
        Set<CallNumber> callNumbers = new HashSet<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT external_id, call_number FROM callnumbers LIMIT " + limit);

        while (rs.next()) {
            callNumbers.add(new CallNumber(
                    rs.getString("external_id"),
                    rs.getString("call_number")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} unique call numbers from PostgreSQL.", callNumbers.size());
        return callNumbers;
    }

    // Fetch unique contributors from PostgreSQL
    public static Set<Contributor> fetchUniqueContributorsFromPostgres(int limit) throws Exception {
        Set<Contributor> contributors = new HashSet<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT contributor FROM contributors LIMIT " + limit);

        while (rs.next()) {
            contributors.add(new Contributor(
                    rs.getString("contributor")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} unique contributors from PostgreSQL.", contributors.size());
        return contributors;
    }

    // Fetch unique resources from PostgreSQL
    public static Set<Resource> fetchUniqueResourcesFromPostgres(int limit) throws Exception {
        Set<Resource> resources = new HashSet<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT external_id, pdf, image FROM resources LIMIT " + limit);

        while (rs.next()) {
            resources.add(new Resource(
                    rs.getString("external_id"),
                    rs.getString("pdf"),
                    rs.getString("image")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} unique resources from PostgreSQL.", resources.size());
        return resources;
    }

    // Fetch unique subjects from PostgreSQL
    public static Set<Subject> fetchUniqueSubjectsFromPostgres(int limit) throws Exception {
        Set<Subject> subjects = new HashSet<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT subject FROM subjects LIMIT " + limit);

        while (rs.next()) {
            subjects.add(new Subject(
                    rs.getString("subject")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} unique subjects from PostgreSQL.", subjects.size());
        return subjects;
    }

    // Insert items into Neo4j
    public static void insertItemsIntoNeo4j(Set<Item> items) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Item item : items) {
            neo4jSession.run("MERGE (i:Item {externalId: $externalId}) " +
                            "ON CREATE SET i.title = $title, i.callNumber = $callNumber, i.date = $date",
                    Values.parameters(
                            "externalId", item.getExternalId(),
                            "title", item.getTitle() != null ? item.getTitle() : "",
                            "callNumber", item.getCallNumber() != null ? item.getCallNumber() : "",
                            "date", item.getDate() != null ? item.getDate() : ""
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} unique items into Neo4j.", items.size());
    }

    // Insert call numbers into Neo4j
    public static void insertCallNumbersIntoNeo4j(Set<CallNumber> callNumbers) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (CallNumber callNumber : callNumbers) {
            neo4jSession.run("MERGE (c:CallNumber {externalId: $externalId}) " +
                            "ON CREATE SET c.callNumber = $callNumber",
                    Values.parameters(
                            "externalId", callNumber.getExternalId(),
                            "callNumber", callNumber.getCallNumber() != null ? callNumber.getCallNumber() : ""
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} unique call numbers into Neo4j.", callNumbers.size());
    }

    // Insert contributors into Neo4j
    public static void insertContributorsIntoNeo4j(Set<Contributor> contributors) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Contributor contributor : contributors) {
            neo4jSession.run("MERGE (c:Contributor {contributor: $contributor})",
                    Values.parameters(
                            "contributor", contributor.getContributor() != null ? contributor.getContributor() : ""
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} unique contributors into Neo4j.", contributors.size());
    }

    // Insert resources into Neo4j
    public static void insertResourcesIntoNeo4j(Set<Resource> resources) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Resource resource : resources) {
            neo4jSession.run("MERGE (r:Resource {externalId: $externalId}) " +
                            "ON CREATE SET r.pdf = $pdf, r.image = $image",
                    Values.parameters(
                            "externalId", resource.getExternalId(),
                            "pdf", resource.getPdf() != null ? resource.getPdf() : "",
                            "image", resource.getImage() != null ? resource.getImage() : ""
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} unique resources into Neo4j.", resources.size());
    }

    // Insert subjects into Neo4j
    public static void insertSubjectsIntoNeo4j(Set<Subject> subjects) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Subject subject : subjects) {
            neo4jSession.run("MERGE (s:Subject {subject: $subject})",
                    Values.parameters(
                            "subject", subject.getSubject() != null ? subject.getSubject() : ""
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} unique subjects into Neo4j.", subjects.size());
    }

    // Transfer items
    public static void transferItems(int limit) throws Exception {
        logger.debug("Starting transfer of unique items.");
        Set<Item> uniqueItems = fetchUniqueItemsFromPostgres(limit);
        insertItemsIntoNeo4j(uniqueItems);
        logger.debug("Completed transfer of unique items.");
    }

    // Transfer call numbers
    public static void transferCallNumbers(int limit) throws Exception {
        logger.debug("Starting transfer of unique call numbers.");
        Set<CallNumber> uniqueCallNumbers = fetchUniqueCallNumbersFromPostgres(limit);
        insertCallNumbersIntoNeo4j(uniqueCallNumbers);
        logger.debug("Completed transfer of unique call numbers.");
    }

    // Transfer contributors
    public static void transferContributors(int limit) throws Exception {
        logger.debug("Starting transfer of unique contributors.");
        Set<Contributor> uniqueContributors = fetchUniqueContributorsFromPostgres(limit*5);
        insertContributorsIntoNeo4j(uniqueContributors);
        logger.debug("Completed transfer of unique contributors.");
    }

    // Transfer resources
    public static void transferResources(int limit) throws Exception {
        logger.debug("Starting transfer of unique resources.");
        Set<Resource> uniqueResources = fetchUniqueResourcesFromPostgres(limit);
        insertResourcesIntoNeo4j(uniqueResources);
        logger.debug("Completed transfer of unique resources.");
    }

    // Transfer subjects
    public static void transferSubjects(int limit) throws Exception {
        logger.debug("Starting transfer of unique subjects.");
        Set<Subject> uniqueSubjects = fetchUniqueSubjectsFromPostgres(limit*5);
        insertSubjectsIntoNeo4j(uniqueSubjects);
        logger.debug("Completed transfer of unique subjects.");
    }
}
