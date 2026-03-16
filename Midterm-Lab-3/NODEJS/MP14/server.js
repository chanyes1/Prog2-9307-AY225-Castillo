// Import required modules
const http = require("http");
const fs = require("fs");
const url = require("url");

// Create HTTP server
http.createServer((req, res) => {

    const query = url.parse(req.url, true).query;

    // If request includes dataset path and keyword
    if (query.path && query.keyword) {

        // Read CSV dataset
        const data = fs.readFileSync(query.path, "utf8");

        const rows = data.split("\n");

        let count = 0; // variable to count occurrences

        // Process dataset
        rows.forEach(row => {

            const columns = row.split(",");

            columns.forEach(value => {

                if (value.toLowerCase().includes(query.keyword.toLowerCase())) {
                    count++;
                }

            });

        });

        // Send formatted output
        res.writeHead(200, {"Content-Type": "text/plain"});
        res.end(`Keyword "${query.keyword}" found ${count} times.`);

    } else {

        // Load HTML page
        const page = fs.readFileSync("index.html");
        res.writeHead(200, {"Content-Type": "text/html"});
        res.end(page);

    }

}).listen(3000);

console.log("Server running at http://localhost:3000");