CREATE TABLE IF NOT EXISTS Persons
(
    id         INTEGER  GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    surname    VARCHAR(100) NOT NULL,
    birth_date date
);