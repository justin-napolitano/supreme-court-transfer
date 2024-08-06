package com.example;

import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataTransferService {

    // CallNumber methods
    public static List<CallNumber> fetchCallNumbersFromPostgres() throws Exception {
        List<CallNumber> callNumbers = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, call_number, external_id FROM callnumbers");

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
    }

    public static void transferCallNumbers() throws Exception {
        List<CallNumber> callNumbers = fetchCallNumbersFromPostgres();
        insertCallNumbersIntoNeo4j(callNumbers);
    }

    // Contributor methods
    public static List<Contributor> fetchContributorsFromPostgres() throws Exception {
        List<Contributor> contributors = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, external_id, contributor FROM contributors");

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
    }

    public static void transferContributors() throws Exception {
        List<Contributor> contributors = fetchContributorsFromPostgres();
        insertContributorsIntoNeo4j(contributors);
    }

    // Resource methods
    public static List<Resource> fetchResourcesFromPostgres() throws Exception {
        List<Resource> resources = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, pdf, image, external_id FROM resources");

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
    }

    public static void transferResources() throws Exception {
        List<Resource> resources = fetchResourcesFromPostgres();
        insertResourcesIntoNeo4j(resources);
    }

    // Item methods
    public static List<Item> fetchItemsFromPostgres() throws Exception {
        List<Item> items = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, notes, call_number, created_published, title, date, source_collection, external_id FROM items");

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
    }

    public static void transferItems() throws Exception {
        List<Item> items = fetchItemsFromPostgres();
        insertItemsIntoNeo4j(items);
    }

    // Subject methods
    public static List<Subject> fetchSubjectsFromPostgres() throws Exception {
        List<Subject> subjects = new ArrayList<>();
        Connection postgresConn = PostgresConnection.getConnection();
        Statement stmt = postgresConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id, external_id, subject FROM subjects");

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
    }

    public static void transferSubjects() throws Exception {
        List<Subject> subjects = fetchSubjectsFromPostgres();
        insertSubjectsIntoNeo4j(subjects);
    }
}
