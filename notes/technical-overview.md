---
slug: github-supreme-court-transfer-note-technical-overview
id: github-supreme-court-transfer-note-technical-overview
title: Supreme Court Transfer Repo Overview
repo: justin-napolitano/supreme-court-transfer
githubUrl: https://github.com/justin-napolitano/supreme-court-transfer
generatedAt: '2025-11-24T18:47:48.769Z'
source: github-auto
summary: >-
  This repo streamlines moving data from PostgreSQL to Neo4j using Java. It's
  designed for handling large datasets and consists of several key components:
tags: []
seoPrimaryKeyword: ''
seoSecondaryKeywords: []
seoOptimized: false
topicFamily: null
topicFamilyConfidence: null
kind: note
entryLayout: note
showInProjects: false
showInNotes: true
showInWriting: false
showInLogs: false
---

This repo streamlines moving data from PostgreSQL to Neo4j using Java. It's designed for handling large datasets and consists of several key components:

- **Java JDK 8+**
- **Maven** for project management
- Running instances of **Neo4j** and **PostgreSQL**
- A `.env` file for environment variables

## Quick Start

1. **Set up Maven Project**:
   ```sh
   mvn archetype:generate -DgroupId=com.supreme_court_transfer -DartifactId=supreme-court-transfer -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```

2. **Prepare Project Structure**: Ensure it matches the outlined directory structure.

3. **Add dependencies** in `pom.xml` for PostgreSQL, Neo4j, and logging.

4. **Set Environment Variables** in a `.env` file:
   ```plaintext
   POSTGRES_URL=jdbc:postgresql://localhost:5432/your_db
   ```

## Gotchas

Make sure your database instances are configured correctly. Check the connection URLs in your `.env` file.
