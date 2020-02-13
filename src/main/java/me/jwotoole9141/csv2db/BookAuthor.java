package me.jwotoole9141.csv2db;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A representation of a book's author for the bookstore.
 */
public class BookAuthor {

    /* fields match naming convention of json-serialized
     * authors in order to be parsed with gson */

    private String author_name;
    private String author_email;
    private String author_url;

    public String getName() {
        return author_name;
    }

    public String getEmail() {
        return author_email;
    }

    public String getUrl() {
        return author_url;
    }

    public void setName(String newName) {
        this.author_name = newName;
    }

    public void setEmail(String newEmail) {
        this.author_email = newEmail;
    }

    public void setUrl(String newUrl) {
        this.author_url = newUrl;
    }

    /**
     * Inserts this author into the 'author' table.
     *
     * @param conn a connection to the bookstore database
     * @throws SQLException the operation was not successful
     */
    public void insertToDb(Connection conn) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO author (author_name, author_email, author_url) VALUES (?, ?, ?)");

        stmt.setString(1, getName());
        stmt.setString(2, getEmail());
        stmt.setString(3, getUrl());

        stmt.execute();
        stmt.close();
    }

    /**
     * Parses authors from a JSON file.
     *
     * @param jsonFile the path of the json file
     * @return an array of authors
     *
     * @throws FileNotFoundException the given path doesn't exist
     * @throws JsonIOException the file could not be read
     * @throws JsonSyntaxException the file contained invalid JSON
     */
    public static BookAuthor[] parseAuthors(Path jsonFile)
            throws FileNotFoundException, JsonIOException, JsonSyntaxException {

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(Utils.getResourceReader(jsonFile));
        return gson.fromJson(jsonReader, BookAuthor[].class);
    }
}
