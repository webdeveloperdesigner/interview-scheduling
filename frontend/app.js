const express = require("express");
const axios = require("axios");

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.set("view engine", "ejs");
app.set("views", __dirname + "/views");

// ===== HOME PAGE =====
app.get("/", (req, res) => {
    res.redirect("/home");
});

app.get("/home", (req, res) => {
    res.render("home", { userName: "Guest" });
});


// ===== AVAILABILITY PAGE =====
app.get("/availability", (req, res) => {
    res.render("availability");
});

// ===== SLOTS PAGE =====
app.get("/slots", async (req, res) => {
    try {
        const availabilityId = req.query.availabilityId;
        console.log("Availability ID from query:", availabilityId);

        const url = availabilityId
            ? `http://localhost:8080/api/slots/by-availability/${availabilityId}`
            : `http://localhost:8080/api/slots`;

        console.log("Calling backend URL:", url);

        const response = await axios.get(url);

        // Pass slots to EJS template
        res.render("slots", { slots: response.data });
    } catch (err) {
        console.error(err);
        res.status(500).send("Error loading slots");
    }
});

// ===== CATCH-ALL 404 =====
app.use((req, res) => {
    res.status(404).send("Page not found");
});

// ===== START SERVER =====
app.listen(3000, () => {
    console.log("Frontend running at http://localhost:3000");
});
