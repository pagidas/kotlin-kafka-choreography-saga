CREATE SCHEMA orders_app;

CREATE TABLE orders_app.orders(
      id uuid NOT NULL,
      status varchar(45),
      pantry_item_id uuid NOT NULL,
      pantry_item_quantity int NOT NULL,
      PRIMARY KEY (id)
);
