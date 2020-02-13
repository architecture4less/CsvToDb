package me.jwotoole9141.csv2db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A representation of a book for the bookstore.
 */
public class Book {

    private String isbn;
    private String book_title;
    private String author_name;

    public Book(String isbn, String bookTitle, String authorName) {
        this.isbn = isbn;
        this.book_title = bookTitle;
        this.author_name = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return book_title;
    }

    public String getAuthor() {
        return author_name;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String book_title) {
        this.book_title = book_title;
    }

    public void setAuthor(String author_name) {
        this.author_name = author_name;
    }

    /**
     * Inserts this book into the 'book' table.
     *
     * @param conn a connection to the bookstore database
     * @throws SQLException the operation was not successful
     */
    public void insertToDb(Connection conn) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO book (isbn, book_title, author_name) VALUES (?, ?, ?)");

        stmt.setString(1, getIsbn());
        stmt.setString(2, getTitle());
        stmt.setString(3, getAuthor());

        stmt.execute();
        stmt.close();
    }

    /**
     * Parses books from a CSV file.
     *
     * @param csvFile the path of the CSV file
     * @return an array of books
     *
     * @throws IOException  the file could not be found or read
     * @throws CsvException the file contained invalid CSV
     */
    public static Book[] parseBooksCsv(Path csvFile) throws IOException, CsvException {

        CSVReader reader = new CSVReader(Utils.getResourceReader(csvFile));
        return reader.readAll().stream()
                .map(csvRow -> new Book(csvRow[0], csvRow[1], csvRow[2]))
                .toArray(Book[]::new);
    }
}
