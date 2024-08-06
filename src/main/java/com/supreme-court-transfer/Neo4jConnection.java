package com.example;

import java.util.Properties;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import io.github.cdimascio.dotenv.Dotenv;

public class Neo4jConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String NEO4J_URL = dotenv.get("NEO4J_URL");
    private static final String NEO4J_USER = dotenv.get("NEO4J_USER");
    private static final String NEO4J_PASSWORD = dotenv.get("NEO4J_PASSWORD");

    private static Driver driver = GraphDatabase.driver(NEO4J_URL, AuthTokens.basic(NEO4J_USER, NEO4J_PASSWORD));

    public static Session getSession() {
        return driver.session();
    }

    public static void close() {
        driver.close();
    }
}
