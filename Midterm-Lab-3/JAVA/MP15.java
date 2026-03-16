import java.io.*;
import java.util.*;

public class MP15 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Ask user for dataset path
        System.out.print("Enter CSV file path: ");
        String path = scanner.nextLine();

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {

            // Open dataset file (dataset handling)
            reader = new BufferedReader(new FileReader(path));

            // Create output CSV file
            writer = new BufferedWriter(new FileWriter("first50.csv"));

            String line;
            boolean dataStarted = false;
            int count = 0;

            // Read dataset line by line (processing logic)
            while ((line = reader.readLine()) != null) {

                // Detect header row and skip metadata
                if (!dataStarted) {

                    if (line.startsWith("Candidate")) {
                        dataStarted = true;

                        // Write header to new CSV
                        writer.write(line);
                        writer.newLine();
                    }

                    continue;
                }

                // Write first 50 dataset rows
                if (count < 50) {
                    writer.write(line);
                    writer.newLine();
                    count++;
                } else {
                    break;
                }
            }

            System.out.println("\nFirst 50 dataset rows exported to first50.csv");

        } catch (FileNotFoundException e) {

            // Error message when dataset file cannot be found
            System.out.println("Error: CSV file not found. Please check the file path.");

        } catch (IOException e) {

            // Error message for read/write problems
            System.out.println("Error: Problem reading or writing the CSV file.");

        } finally {

            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("Error closing file streams.");
            }

        }
    }
}