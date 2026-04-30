-- Create database if not exists
-- CREATE DATABASE pizza_ordering_system;

-- Connect to the database
-- \c pizza_ordering_system;

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    order_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    description VARCHAR(255) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
);