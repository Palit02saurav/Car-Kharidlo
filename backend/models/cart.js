const db = require("../database/db");

db.query(`
    CREATE TABLE IF NOT EXISTS cart (
        id INT PRIMARY KEY AUTO_INCREMENT,
        user_id INT NOT NULL,
        car_id INT NOT NULL,
        car_name VARCHAR(255) NOT NULL,
        car_price VARCHAR(100) NOT NULL,
        car_image VARCHAR(255) NOT NULL,
        quantity INT DEFAULT 1,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
`, (err) => {
    if (err) {
        console.log("Cart table creation failed:", err);
    } else {
        console.log("Cart table ready");
    }
});

class Cart {
    static addToCart(data, callback) {
        const checkQuery = `
            SELECT * FROM cart
            WHERE user_id = ? AND car_id = ?
        `;

        db.query(
            checkQuery,
            [data.user_id, data.car_id],
            (err, result) => {
                if (err) return callback(err);

                if (result.length > 0) {
                    const updateQuery = `
                        UPDATE cart
                        SET quantity = quantity + 1
                        WHERE user_id = ? AND car_id = ?
                    `;

                    db.query(
                        updateQuery,
                        [data.user_id, data.car_id],
                        callback
                    );
                } else {
                    const insertQuery = `
                        INSERT INTO cart
                        (
                            user_id,
                            car_id,
                            car_name,
                            car_price,
                            car_image,
                            quantity
                        )
                        VALUES (?, ?, ?, ?, ?, ?)
                    `;

                    db.query(
                        insertQuery,
                        [
                            data.user_id,
                            data.car_id,
                            data.car_name,
                            data.car_price,
                            data.car_image,
                            data.quantity
                        ],
                        callback
                    );
                }
            }
        );
    }

    static getCart(userId, callback) {
        const query = `
            SELECT * FROM cart
            WHERE user_id = ?
            ORDER BY created_at DESC
        `;

        db.query(query, [userId], callback);
    }

    static removeCart(cartId, callback) {
        const query = `
            DELETE FROM cart
            WHERE id = ?
        `;

        db.query(query, [cartId], callback);
    }

    static clearCart(userId, callback) {
        const query = `
            DELETE FROM cart
            WHERE user_id = ?
        `;

        db.query(query, [userId], callback);
    }
}

module.exports = Cart;