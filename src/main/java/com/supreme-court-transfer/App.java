package com.example;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();

        // Print to verify (optional, for debugging purposes)
        System.out.println("Postgres URL: " + dotenv.get("POSTGRES_URL"));
        System.out.println("Neo4j URL: " + dotenv.get("NEO4J_URL"));

        try {
            DataTransferService.transferCallNumbers();
            DataTransferService.transferContributors();
            DataTransferService.transferResources();
            DataTransferService.transferItems();
            DataTransferService.transferSubjects();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Neo4jConnection.close();
        }
    }
}
