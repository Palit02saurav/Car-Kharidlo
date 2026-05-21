const Cart = require("../models/cart");

const addToCart = (req, res) => {
    const {
        user_id,
        car_id,
        car_name,
        car_price,
        car_image
    } = req.body;

    if (!user_id || !car_id || !car_name || !car_price || !car_image) {
        return res.status(400).json({
            success: false,
            message: "Missing required fields"
        });
    }

    const cartData = {
        user_id,
        car_id,
        car_name,
        car_price,
        car_image,
        quantity: 1
    };

    Cart.addToCart(cartData, (err) => {
        if (err) {
            return res.status(500).json({
                success: false,
                message: "Failed to add to cart"
            });
        }

        res.status(200).json({
            success: true,
            message: "Car added to cart"
        });
    });
};

const getCart = (req, res) => {
    const userId = req.params.userId;

    Cart.getCart(userId, (err, result) => {
        if (err) {
            return res.status(500).json({
                success: false,
                message: "Failed to fetch cart"
            });
        }

        res.status(200).json({
            success: true,
            cart: result
        });
    });
};

const removeCart = (req, res) => {
    const cartId = req.params.id;

    Cart.removeCart(cartId, (err) => {
        if (err) {
            return res.status(500).json({
                success: false,
                message: "Failed to remove cart item"
            });
        }

        res.status(200).json({
            success: true,
            message: "Cart item removed"
        });
    });
};

const clearCart = (req, res) => {
    const userId = req.params.userId;

    Cart.clearCart(userId, (err) => {
        if (err) {
            return res.status(500).json({
                success: false,
                message: "Failed to clear cart"
            });
        }

        res.status(200).json({
            success: true,
            message: "Cart cleared"
        });
    });
};

module.exports = {
    addToCart,
    getCart,
    removeCart,
    clearCart
};