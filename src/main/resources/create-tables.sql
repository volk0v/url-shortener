CREATE TABLE IF NOT EXISTS short_url
(
    id            INT PRIMARY KEY auto_increment,
    reference_url VARCHAR(300) NOT NULL,
    shortened_url VARCHAR(300) NOT NULL UNIQUE
);