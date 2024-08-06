package com.supreme_court_transfer;

import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataTransferService {
    private static final Logger logger = LoggerFactory.getLogger(DataTransferService.class);

    // CallNumber methods
    public static List<CallNumber> fetchCallNumbersFromPostgres(int limit) throws Exception {
        List<CallNumber> callNumbers = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, call_number, external_id FROM callnumbers LIMIT " + limit);

        while (rs.next()) {
            callNumbers.add(new CallNumber(
                    rs.getInt("id"),
                    rs.getString("call_number"),
                    rs.getString("external_id")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} call numbers from PostgreSQL.", callNumbers.size());
        return callNumbers;
    }

    public static void insertCallNumbersIntoNeo4j(List<CallNumber> callNumbers) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (CallNumber callNumber : callNumbers) {
            neo4jSession.run("CREATE (:CallNumber {id: $id, callNumber: $callNumber, externalId: $externalId})",
                    Values.parameters(
                            "id", callNumber.getId(),
                            "callNumber", callNumber.getCallNumber(),
                            "externalId", callNumber.getExternalId()
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} call numbers into Neo4j.", callNumbers.size());
    }

    public static void transferCallNumbers(int limit) throws Exception {
        logger.debug("Starting transfer of call numbers.");
        List<CallNumber> callNumbers = fetchCallNumbersFromPostgres(limit);
        insertCallNumbersIntoNeo4j(callNumbers);
        logger.debug("Completed transfer of call numbers.");
    }

    // Contributor methods
    public static List<Contributor> fetchContributorsFromPostgres(int limit) throws Exception {
        List<Contributor> contributors = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, external_id, contributor FROM contributors LIMIT " + limit);

        while (rs.next()) {
            contributors.add(new Contributor(
                    rs.getInt("id"),
                    rs.getString("external_id"),
                    rs.getString("contributor")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} contributors from PostgreSQL.", contributors.size());
        return contributors;
    }

    public static void insertContributorsIntoNeo4j(List<Contributor> contributors) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Contributor contributor : contributors) {
            neo4jSession.run("CREATE (:Contributor {id: $id, externalId: $externalId, contributor: $contributor})",
                    Values.parameters(
                            "id", contributor.getId(),
                            "externalId", contributor.getExternalId(),
                            "contributor", contributor.getContributor()
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} contributors into Neo4j.", contributors.size());
    }

    public static void transferContributors(int limit) throws Exception {
        logger.debug("Starting transfer of contributors.");
        List<Contributor> contributors = fetchContributorsFromPostgres(limit);
        insertContributorsIntoNeo4j(contributors);
        logger.debug("Completed transfer of contributors.");
    }

    // Resource methods
    public static List<Resource> fetchResourcesFromPostgres(int limit) throws Exception {
        List<Resource> resources = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, pdf, image, external_id FROM resources LIMIT " + limit);

        while (rs.next()) {
            resources.add(new Resource(
                    rs.getInt("id"),
                    rs.getString("pdf"),
                    rs.getString("image"),
                    rs.getString("external_id")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} resources from PostgreSQL.", resources.size());
        return resources;
    }

    public static void insertResourcesIntoNeo4j(List<Resource> resources) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Resource resource : resources) {
            neo4jSession.run("CREATE (:Resource {id: $id, pdf: $pdf, image: $image, externalId: $externalId})",
                    Values.parameters(
                            "id", resource.getId(),
                            "pdf", resource.getPdf(),
                            "image", resource.getImage(),
                            "externalId", resource.getExternalId()
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} resources into Neo4j.", resources.size());
    }

    public static void transferResources(int limit) throws Exception {
        logger.debug("Starting transfer of resources.");
        List<Resource> resources = fetchResourcesFromPostgres(limit);
        insertResourcesIntoNeo4j(resources);
        logger.debug("Completed transfer of resources.");
    }

    // Item methods
    public static List<Item> fetchItemsFromPostgres(int limit) throws Exception {
        List<Item> items = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, notes, call_number, created_published, title, date, source_collection, external_id FROM items LIMIT " + limit);

        while (rs.next()) {
            items.add(new Item(
                    rs.getInt("id"),
                    rs.getString("notes"),
                    rs.getString("call_number"),
                    rs.getString("created_published"),
                    rs.getString("title"),
                    rs.getString("date"),
                    rs.getString("source_collection"),
                    rs.getString("external_id")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} items from PostgreSQL.", items.size());
        return items;
    }

    public static void insertItemsIntoNeo4j(List<Item> items) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Item item : items) {
            neo4jSession.run("CREATE (:Item {id: $id, notes: $notes, callNumber: $callNumber, createdPublished: $createdPublished, title: $title, date: $date, sourceCollection: $sourceCollection, externalId: $externalId})",
                    Values.parameters(
                            "id", item.getId(),
                            "notes", item.getNotes(),
                            "callNumber", item.getCallNumber(),
                            "createdPublished", item.getCreatedPublished(),
                            "title", item.getTitle(),
                            "date", item.getDate(),
                            "sourceCollection", item.getSourceCollection(),
                            "externalId", item.getExternalId()
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} items into Neo4j.", items.size());
    }

    public static void transferItems(int limit) throws Exception {
        logger.debug("Starting transfer of items.");
        List<Item> items = fetchItemsFromPostgres(limit);
        insertItemsIntoNeo4j(items);
        logger.debug("Completed transfer of items.");
    }

    // Subject methods
    public static List<Subject> fetchSubjectsFromPostgres(int limit) throws Exception {
        List<Subject> subjects = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, external_id, subject FROM subjects LIMIT " + limit);

        while (rs.next()) {
            subjects.add(new Subject(
                    rs.getInt("id"),
                    rs.getString("external_id"),
                    rs.getString("subject")
            ));
        }

        rs.close();
        stmt.close();
        postgresConn.close();
        logger.info("Fetched {} subjects from PostgreSQL.", subjects.size());
        return subjects;
    }

    public static void insertSubjectsIntoNeo4j(List<Subject> subjects) {
        Session neo4jSession = Neo4jConnection.getSession();
        for (Subject subject : subjects) {
            neo4jSession.run("CREATE (:Subject {id: $id, externalId: $externalId, subject: $subject})",
                    Values.parameters(
                            "id", subject.getId(),
                            "externalId", subject.getExternalId(),
                            "subject", subject.getSubject()
                    ));
        }
        neo4jSession.close();
        logger.info("Inserted {} subjects into Neo4j.", subjects.size());
    }

    public static void transferSubjects(int limit) throws Exception {
        logger.debug("Starting transfer of subjects.");
        List<Subject> subjects = fetchSubjectsFromPostgres(limit);
        insertSubjectsIntoNeo4j(subjects);
        logger.debug("Completed transfer of subjects.");
    }
}
