CREATE SCHEMA orders_app;

CREATE TABLE orders_app.orders (
    id uuid NOT NULL,
    status varchar(45),
    PRIMARY KEY (id)
);