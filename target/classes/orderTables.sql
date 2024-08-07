-- Create temporary tables ordered by external_id
CREATE TABLE items_temp AS
SELECT *
FROM items
ORDER BY external_id;

CREATE TABLE callnumbers_temp AS
SELECT *
FROM callnumbers
ORDER BY external_id;

CREATE TABLE contributors_temp AS
SELECT *
FROM contributors
ORDER BY external_id;

CREATE TABLE resources_temp AS
SELECT *
FROM resources
ORDER BY external_id;

CREATE TABLE subjects_temp AS
SELECT *
FROM subjects
ORDER BY external_id;

-- Rename original tables to temporary names
ALTER TABLE items RENAME TO items_old;
ALTER TABLE items_temp RENAME TO items;

ALTER TABLE callnumbers RENAME TO callnumbers_old;
ALTER TABLE callnumbers_temp RENAME TO callnumbers;

ALTER TABLE contributors RENAME TO contributors_old;
ALTER TABLE contributors_temp RENAME TO contributors;

ALTER TABLE resources RENAME TO resources_old;
ALTER TABLE resources_temp RENAME TO resources;

ALTER TABLE subjects RENAME TO subjects_old;
ALTER TABLE subjects_temp RENAME TO subjects;

-- Drop old tables
DROP TABLE items_old;
DROP TABLE callnumbers_old;
DROP TABLE contributors_old;
DROP TABLE resources_old;
DROP TABLE subjects_old;
