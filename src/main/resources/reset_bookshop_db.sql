/*
 * Destroys and re-creates the database
 */

DROP TABLE IF EXISTS author;
CREATE TABLE author (
    author_name  VARCHAR PRIMARY KEY,
    author_email VARCHAR /*UNIQUE*/,
    author_url   VARCHAR /*UNIQUE*/
);


DROP TABLE IF EXISTS book;
CREATE TABLE book (
    isbn           VARCHAR (13)    PRIMARY KEY,
    publisher_name VARCHAR         REFERENCES publisher (pub_name),
    author_name    VARCHAR         REFERENCES author (author_name),
    book_year      INTEGER,
    book_title     VARCHAR         NOT NULL,
    book_price     NUMERIC (20, 2) DEFAULT (9.99)
);

DROP TABLE IF EXISTS publisher;
CREATE TABLE publisher (
    pub_name  VARCHAR      PRIMARY KEY,
    address   VARCHAR      DEFAULT ('No Physical Address'),
    pub_email VARCHAR      UNIQUE,
    pub_phone VARCHAR (10) UNIQUE
);

DROP TABLE IF EXISTS store;
CREATE TABLE store (
    store_id      INTEGER      PRIMARY KEY AUTOINCREMENT,
    store_name    VARCHAR      NOT NULL
                               UNIQUE,
    store_address VARCHAR      UNIQUE,
    store_email   VARCHAR,
    store_phone   VARCHAR (30) UNIQUE
);

DROP TABLE IF EXISTS store_book;
CREATE TABLE store_book (
    store_id INTEGER REFERENCES store (store_id),
    isbn     VARCHAR REFERENCES book (isbn),
    quantity INTEGER DEFAULT (10)
);
