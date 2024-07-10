CREATE DATABASE products;

-- \connect products;

CREATE TABLE product_table (
                               productId BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               name VARCHAR(255),
                               reserved BIGINT,
                               available BIGINT
);
