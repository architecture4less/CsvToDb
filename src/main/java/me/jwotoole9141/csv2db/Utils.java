package me.jwotoole9141.csv2db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class Utils {

    /**
     * Gets a buffered reader for a file on the classpath of {@link Utils}.
     *
     * @param path the relative path of the resource
     * @return an open buffered reader for the resource
     *
     * @throws FileNotFoundException the stream returned by the class loader was null
     */
    public static BufferedReader getResourceReader(Path path) throws FileNotFoundException {

        InputStream stream = IterOne.class.getClassLoader().getResourceAsStream(path.toString());
        if (stream == null) throw new FileNotFoundException(path.toString());
        return new BufferedReader(new InputStreamReader(stream));
    }
}
