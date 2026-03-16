import java.io.*;
import java.util.*;

public class MP16 {

    // Function to parse CSV line respecting quotes
    public static List<String> parseCSV(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes; // toggle quotes
                continue; // remove the quotes themselves
            }

            if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        // Add the last column
        result.add(sb.toString().trim());
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CSV file path: ");
        String path = scanner.nextLine();

        List<List<String>> rows = new ArrayList<>();
        List<String> header = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean dataStarted = false;

            while ((line = reader.readLine()) != null) {
                // Skip metadata until header row
                if (!dataStarted) {
                    if (line.startsWith("Candidate")) {
                        header = parseCSV(line);
                        dataStarted = true;
                    }
                    continue;
                }

                // Parse row respecting quotes
                List<String> row = parseCSV(line);

                // Skip empty or malformed rows
                if (row.size() >= header.size()) {
                    rows.add(row);
                }
            }

            // Pick 10 random rows
            Random rand = new Random();
            Set<Integer> used = new HashSet<>();
            int rowsToShow = Math.min(10, rows.size());

            System.out.println("\nRandom 10 Rows from Dataset:\n");

            for (int i = 0; i < rowsToShow; i++) {
                int index;
                do {
                    index = rand.nextInt(rows.size());
                } while (used.contains(index));
                used.add(index);

                List<String> row = rows.get(index);
                for (int j = 0; j < header.size(); j++) {
                    String colName = header.get(j);
                    String value = j < row.size() ? row.get(j) : "";
                    System.out.println(colName + ": " + value);
                }
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found. Please check the file path.");
        } catch (IOException e) {
            System.out.println("Error: Unable to read the CSV file.");
        }
    }
}