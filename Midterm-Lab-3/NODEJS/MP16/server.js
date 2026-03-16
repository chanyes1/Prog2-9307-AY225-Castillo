const http = require("http");
const fs = require("fs");
const url = require("url");

// Regex function to split CSV respecting quotes
function parseCSVLine(line) {
    const result = [];
    let current = "";
    let inQuotes = false;

    for (let i = 0; i < line.length; i++) {
        const char = line[i];

        if (char === '"') {
            inQuotes = !inQuotes; // toggle quote flag
        } else if (char === ',' && !inQuotes) {
            result.push(current.trim());
            current = "";
        } else {
            current += char;
        }
    }
    result.push(current.trim()); // push last field
    return result;
}

// HTTP server
http.createServer((req, res) => {

    const query = url.parse(req.url, true).query;

    if (query.path) {

        const data = fs.readFileSync(query.path, "utf8");
        const lines = data.split("\n");

        let dataStarted = false;
        let header = [];
        let rows = [];

        lines.forEach(line => {

            if (!dataStarted) {
                if (line.startsWith("Candidate")) {
                    dataStarted = true;
                    header = parseCSVLine(line); // parse header
                }
                return; // skip metadata
            }

            const values = parseCSVLine(line); // parse row with quotes handling

            if (values.length >= header.length) {
                let obj = {};
                header.forEach((col, i) => {
                    obj[col] = values[i];
                });
                rows.push(obj);
            }

        });

        // Pick 10 random rows without duplicates
        let randomRows = [];
        const usedIndexes = new Set();
        while (randomRows.length < 10 && randomRows.length < rows.length) {
            const index = Math.floor(Math.random() * rows.length);
            if (!usedIndexes.has(index)) {
                usedIndexes.add(index);
                randomRows.push(rows[index]);
            }
        }

        // Build HTML table
        let tableHTML = "<table border='1' style='border-collapse: collapse; text-align: left;'>";
        tableHTML += "<tr>";
        header.forEach(col => {
            tableHTML += `<th style='padding:4px;'>${col}</th>`;
        });
        tableHTML += "</tr>";

        randomRows.forEach(row => {
            tableHTML += "<tr>";
            header.forEach(col => {
                tableHTML += `<td style='padding:4px;'>${row[col]}</td>`;
            });
            tableHTML += "</tr>";
        });

        tableHTML += "</table>";

        // Serve HTML page with table
        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(`
            <html>
            <head><title>MP16 - Random 10 Rows</title></head>
            <body>
            <h2>Random 10 Rows from Dataset</h2>
            ${tableHTML}
            <br><a href="/">Back</a>
            </body>
            </html>
        `);

    } else {

        const page = fs.readFileSync("index.html");
        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(page);

    }

}).listen(3000);

console.log("Server running at http://localhost:3000");