DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    record_code     VARCHAR(3) NOT NULL,
    expiration_date DATE       NOT NULL

);