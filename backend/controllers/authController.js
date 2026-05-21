const db = require("../database/db");
const bcrypt = require("bcryptjs");

const signupUser = async (req, res) => {
    try {
        const { name, email, password } = req.body;

        if (!name || !email || !password) {
            return res.status(400).json({
                message: "All fields are required"
            });
        }

        const checkUserQuery = "SELECT * FROM users WHERE email = ?";

        db.query(checkUserQuery, [email], async (err, result) => {
            if (err) {
                return res.status(500).json({
                    message: err.message
                });
            }

            if (result.length > 0) {
                return res.status(400).json({
                    message: "Email already exists"
                });
            }

            const hashedPassword = await bcrypt.hash(password, 10);

            const insertQuery =
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

            db.query(
                insertQuery,
                [name, email, hashedPassword],
                (err, result) => {
                    if (err) {
                        return res.status(500).json({
                            message: err.message
                        });
                    }

                    res.status(201).json({
                        message: "User Registered Successfully"
                    });
                }
            );
        });

    } catch (error) {
        res.status(500).json({
            message: error.message
        });
    }
};

const loginUser = (req, res) => {

    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({
            message: "Email and Password required"
        });
    }

    const query = "SELECT * FROM users WHERE email = ?";

    db.query(query, [email], async (err, result) => {

        if (err) {
            return res.status(500).json({
                message: err.message
            });
        }

        if (result.length === 0) {
            return res.status(401).json({
                message: "Invalid credentials"
            });
        }

        const user = result[0];

        const isMatch = await bcrypt.compare(password, user.password);

        if (!isMatch) {
            return res.status(401).json({
                message: "Invalid credentials"
            });
        }

        res.status(200).json({
            message: "Login Successful",
            userId: user.id,
            name: user.name,
            email: user.email
        });

    });
};

module.exports = {
    signupUser,
    loginUser
};