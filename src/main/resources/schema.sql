CREATE TABLE IF NOT EXISTS users
(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    password varchar NOT NULL,
    first_name varchar(50) NOT NULL,
    surname varchar(50),
    last_name varchar(50),
    email varchar(70) NOT NULL unique,
    birthday TIMESTAMP WITHOUT TIME ZONE,
    phone varchar(12),
    social_media_url varchar(255),
    portfolio_url varchar(255),
    country varchar(40),
    city varchar(40),
    role varchar(15)
);