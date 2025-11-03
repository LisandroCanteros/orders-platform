-- Drop existing tables in dependency order
DROP TABLE IF EXISTS outbox_events;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS orders;

-- Orders table (owned by api-orders)
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    product VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

-- Payments table (owned by api-payments)
CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Outbox table (for reliable event publishing)
CREATE TABLE outbox_events (
    id SERIAL PRIMARY KEY,
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id BIGINT NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
