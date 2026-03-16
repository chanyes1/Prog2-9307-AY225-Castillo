import java.io.*;
import java.util.*;

public class MP14 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Ask user for CSV file path (variable)
        System.out.print("Enter CSV file path: ");
        String path = scanner.nextLine();

        // Ask user for keyword (variable)
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();

        BufferedReader reader = null;

        try {

            // Attempt to open dataset file (dataset handling)
            reader = new BufferedReader(new FileReader(path));

            String line;                 // variable to store each line
            boolean dataStarted = false; // flag for detecting header row
            int count = 0;               // variable counting keyword occurrences

            // Read dataset line by line (processing logic)
            while ((line = reader.readLine()) != null) {

                // Skip metadata rows until header is detected (dataset handling)
                if (!dataStarted) {
                    if (line.startsWith("Candidate")) {
                        dataStarted = true;
                    }
                    continue;
                }

                // Split CSV row into columns (dataset handling)
                String[] columns = line.split(",");

                // Search keyword in each column (processing logic)
                for (String value : columns) {
                    if (value.toLowerCase().contains(keyword.toLowerCase())) {
                        count++;
                    }
                }
            }

            // Output result
            System.out.println("\nKeyword '" + keyword + "' found " + count + " times.");

        } catch (FileNotFoundException e) {

            // Error message when CSV file cannot be found
            System.out.println("Error: CSV file not found. Please check the file path.");

        } catch (IOException e) {

            // Error message when file cannot be read
            System.out.println("Error: Unable to read the CSV file.");

        } finally {

            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }

        }
    }
}