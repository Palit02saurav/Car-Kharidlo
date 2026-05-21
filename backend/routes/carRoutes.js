const express = require("express");

const router = express.Router();

router.get("/", (req, res) => {
    res.send("Car Route Working");
});

module.exports = router;