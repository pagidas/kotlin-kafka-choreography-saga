CREATE SCHEMA pantry_app;

CREATE TABLE pantry_app.items(
     id uuid NOT NULL,
     name varchar(45),
     quantity_limit int NOT NULL,
     PRIMARY KEY (id)
);
