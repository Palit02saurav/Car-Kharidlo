const express = require("express");
const cors = require("cors");
require("dotenv").config();

const db = require("./database/db");

const createUsersTable = require("./models/User");

const authRoutes = require("./routes/authRoutes");
const carRoutes = require("./routes/carRoutes");
const cartRoutes = require("./routes/cartRoutes");
const userRoutes = require("./routes/userRoutes");

const app = express();

app.use(cors({
    origin: "*",
    methods: ["GET", "POST", "PUT", "DELETE"],
    allowedHeaders: ["Content-Type", "Authorization"]
}));

app.use(express.json());

createUsersTable();

app.get("/", (req, res) => {
    res.send("Backend Running Successfully");
});

app.use("/api/auth", authRoutes);
app.use("/api/cars", carRoutes);
app.use("/api/cart", cartRoutes);
app.use("/api/user", userRoutes);

const PORT = process.env.PORT || 5000;

app.listen(PORT, "0.0.0.0", () => {
    console.log(`Server running on port ${PORT}`);
});