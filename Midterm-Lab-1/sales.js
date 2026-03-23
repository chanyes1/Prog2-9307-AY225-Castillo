const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Validate CSV format (basic check)
function isCSV(path) {
    return path.toLowerCase().endsWith('.csv');
}

// Process dataset
function processFile(path) {
    try {
        const data = fs.readFileSync(path, 'utf-8');
        const lines = data.trim().split('\n');

        // Remove header
        const header = lines.shift();

        let totalRecords = 0;
        let totalSales = 0;
        let highest = Number.MIN_VALUE;
        let lowest = Number.MAX_VALUE;

        lines.forEach(line => {
            const cols = line.split(',');

            // "total sales" column index = 6
            let sale = parseFloat(cols[6]);

            if (!isNaN(sale)) {
                totalRecords++;
                totalSales += sale;

                if (sale > highest) highest = sale;
                if (sale < lowest) lowest = sale;
            }
        });

        let average = totalRecords > 0 ? totalSales / totalRecords : 0;

        // Display formatted report
        console.log("\n===== EXECUTIVE SALES SUMMARY =====");
        console.log(`Total Records Processed : ${totalRecords}`);
        console.log(`Total Sales Revenue     : ${totalSales.toFixed(2)}`);
        console.log(`Average Sales           : ${average.toFixed(2)}`);
        console.log(`Highest Transaction     : ${highest.toFixed(2)}`);
        console.log(`Lowest Transaction      : ${lowest.toFixed(2)}`);
        console.log("===================================\n");

    } catch (err) {
        console.log("Error reading file:", err.message);
        askFilePath();
    }
}

// Ask user for file path
function askFilePath() {
    rl.question("Enter dataset file path: ", function(path) {

        if (!fs.existsSync(path)) {
            console.log("File does not exist. Try again.");
            return askFilePath();
        }

        if (!isCSV(path)) {
            console.log("Invalid file format. Please provide a CSV file.");
            return askFilePath();
        }

        try {
            fs.accessSync(path, fs.constants.R_OK);
        } catch {
            console.log("File is not readable.");
            return askFilePath();
        }

        console.log("File found. Processing...");
        processFile(path);
        rl.close();
    });
}

// Start program
askFilePath();