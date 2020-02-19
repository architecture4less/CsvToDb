package me.jwotoole9141.csv2db;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A driver for iteration two
 *
 * @author Jared O'Toole
 */
public class IterTwo {

    /**
     * Reads a CSV file and a JSON file and inserts the data into the project's database.
     *
     * @param args unused command line args
     */
    public static void main(String[] args) {

        Path bookstoreDb = Paths.get("BookStore.db");
        Path csvReport = Paths.get("bookstore_report2.csv");
        Path jsonAuthors = Paths.get("authors.json");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + bookstoreDb.toString())) {

            // read books from the csv file and insert them to the database

            try {
                for (Book book : Book.parseBooksCsv(csvReport)) {
                    try {
                        book.insertToDb(conn);
                        System.out.printf("Inserted %s from CSV to the database.%n", book);
                    }
                    catch (SQLException ex) {
                        System.out.printf("Couldn't add %s to the database: %s%n", book, ex.getMessage());
                    }
                }
            }
            catch (FileNotFoundException ex) {
                System.out.printf("The file '%s' could not found%n", csvReport);
            }
            catch (IOException ex) {
                System.out.printf("The file '%s' could not be read: %s%n", csvReport, ex.getMessage());
            }
            catch (CsvException ex) {
                System.out.printf("The file '%s' contained invalid CSV: %s%n", csvReport, ex.getMessage());
            }

            // read authors from the json file and insert them to the database

            try {
                for (Author author : Author.parseAuthors(jsonAuthors)) {
                    try {
                        author.insertToDb(conn);
                        System.out.printf("Inserted %s from JSON to the database.%n", author);
                    }
                    catch (SQLException ex) {
                        System.out.printf("Couldn't add %s to the database: %s%n", author, ex.getMessage());
                    }
                }
            }
            catch (FileNotFoundException ex) {
                System.out.printf("The file '%s' could not found%n", jsonAuthors);
            }
            catch (JsonIOException ex) {
                System.out.printf("The file '%s' could not be read: %s%n", jsonAuthors, ex.getMessage());
            }
            catch (JsonSyntaxException ex) {
                System.out.printf("The file '%s' contained invalid JSON: %s%n", jsonAuthors, ex.getMessage());
            }
        }
        catch (SQLException ex) {
            System.out.printf("Couldn't connect to the '%s' database: %s%n", bookstoreDb, ex.getMessage());
        }
    }
}
