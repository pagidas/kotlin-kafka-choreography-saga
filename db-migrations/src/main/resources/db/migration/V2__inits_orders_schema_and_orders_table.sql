CREATE SCHEMA orders_app;

CREATE TABLE orders_app.orders(
      id uuid NOT NULL,
      status varchar(45),
      pantry_item_id uuid NOT NULL,
      PRIMARY KEY (id),
      FOREIGN KEY (pantry_item_id) REFERENCES pantry_app.items(id)
);
