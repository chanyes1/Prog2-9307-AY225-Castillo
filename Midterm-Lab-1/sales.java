import java.io.*;
import java.util.*;

class DataRecord {
    double totalSales;

    public DataRecord(double totalSales) {
        this.totalSales = totalSales;
    }
}

public class sales {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        File file;

        while (true) {
            System.out.print("Enter dataset file path: ");
            String path = input.nextLine();
            file = new File(path);

            if (!file.exists() || !file.isFile()) {
                System.out.println("Invalid file path. Please try again.");
                continue;
            }

            if (!path.toLowerCase().endsWith(".csv")) {
                System.out.println("Invalid file format. Must be CSV.");
                continue;
            }

            if (!file.canRead()) {
                System.out.println("File is not readable.");
                continue;
            }

            break;
        }

        processFile(file);
    }

    public static void processFile(File file) {
        List<DataRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] cols = line.split(",");

                try {
                    double sale = Double.parseDouble(cols[6]);
                    records.add(new DataRecord(sale));
                } catch (Exception e) {
                    // skip invalid/missing values
                }
            }

            int totalRecords = records.size();
            double totalSales = 0;
            double highest = Double.MIN_VALUE;
            double lowest = Double.MAX_VALUE;

            for (DataRecord r : records) {
                totalSales += r.totalSales;

                if (r.totalSales > highest) highest = r.totalSales;
                if (r.totalSales < lowest) lowest = r.totalSales;
            }

            double average = totalRecords > 0 ? totalSales / totalRecords : 0;

            // Output report
            System.out.println("\n===== EXECUTIVE SALES SUMMARY =====");
            System.out.println("Total Records Processed : " + totalRecords);
            System.out.printf("Total Sales Revenue     : %.2f\n", totalSales);
            System.out.printf("Average Sales           : %.2f\n", average);
            System.out.printf("Highest Transaction     : %.2f\n", highest);
            System.out.printf("Lowest Transaction      : %.2f\n", lowest);
            System.out.println("===================================");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}