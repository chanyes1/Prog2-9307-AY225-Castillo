const http = require("http");
const fs = require("fs");
const url = require("url");

http.createServer((req, res) => {

    const query = url.parse(req.url, true).query;

    if (query.path) {

        // Read CSV dataset
        const data = fs.readFileSync(query.path, "utf8");

        const lines = data.split("\n");

        let dataStarted = false;
        let header = "";
        let rows = [];

        // Process dataset
        lines.forEach(line => {

            // Detect header row (skip metadata automatically)
            if (!dataStarted) {

                if (line.startsWith("Candidate")) {
                    dataStarted = true;
                    header = line;  // save header for export
                }

                return; // skip metadata
            }

            // Only add non-empty lines
            if (line.trim() !== "") {
                rows.push(line);
            }

        });

        // Take first 50 dataset rows
        const first50 = rows.slice(0, 50);

        // Write header + first 50 rows to new CSV
        const output = [header, ...first50].join("\n");

        fs.writeFileSync("first50.csv", output);

        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end("First 50 dataset rows exported to first50.csv (metadata skipped).");

    } else {

        // Serve HTML page
        const page = fs.readFileSync("index.html");

        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(page);

    }

}).listen(3000);

console.log("Server running at http://localhost:3000");