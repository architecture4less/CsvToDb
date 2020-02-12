package me.jwotoole9141.csv2db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A driver for iteration one.
 *
 * @author Jared O'Toole
 */
public class IterOne {

    private static BufferedReader getResourceReader(Path path) throws FileNotFoundException {

        InputStream stream = IterOne.class.getClassLoader().getResourceAsStream(path.toString());
        if (stream == null) throw new FileNotFoundException(path.toString());
        return new BufferedReader(new InputStreamReader(stream));
    }

    /**
     * Parses a CSV file and prints its contents to the console.
     *
     * @param args ignored command line args
     */
    public static void main(String[] args) {

        /* 1. check that file exists
         * 2. read in csv data from file
         * 3. print out the csv data
         * 4. close everything
         */

        Path csvFile = Paths.get("SEOExample.csv");
        System.out.printf("Displaying the CSV contents of '%s'...\n", csvFile);

        try (CSVReader reader = new CSVReader(getResourceReader(csvFile))) {

            // parse the raw data...
            List<String[]> rawData = reader.readAll();

            if (!rawData.isEmpty()) {

                // determine how wide each column should be...
                List<Integer> colWidths = new ArrayList<>();
                for (String[] rawRow : rawData) {
                    for (int i = 0; i < rawRow.length; i++) {

                        int fieldWidth = rawRow[i].length();

                        if (i >= colWidths.size()) {
                            colWidths.add(fieldWidth);
                        }
                        else if (colWidths.get(i) < fieldWidth) {
                            colWidths.set(i, fieldWidth);
                        }
                    }
                }
                // print the csv table...
                System.out.println(" " + rawData.stream()
                        .map(rawRow ->
                                IntStream.range(0, rawRow.length).boxed()
                                        .map(i -> String.format("%" + colWidths.get(i) + "s", rawRow[i]))
                                        .collect(Collectors.joining(" , ")))
                        .collect(Collectors.joining("\n")));
            }
            else {
                System.out.printf("The file '%s' was empty\n", csvFile);
            }
        }
        catch (FileNotFoundException ex) {
            System.out.printf("The file '%s' does not exist\n", csvFile);
        }
        catch (IOException ex) {
            System.out.printf("Could not read file '%s': %s\n", csvFile, ex.getMessage());
        }
        catch (CsvException ex) {
            System.out.printf("Could not parse valid CSV from file '%s': %s\n", csvFile, ex.getMessage());
        }
    }
}
