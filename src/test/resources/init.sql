DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS transaction;

CREATE TABLE account(
    id         CHAR(36)     NOT NULL,
    number     VARCHAR(225) NOT NULL,
    balance    DECIMAL      NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT uk_number UNIQUE (number)
);

CREATE TABLE transaction(
    id             CHAR(36)     NOT NULL,
    type           VARCHAR(50)  NOT NULL,
    create_date    TIMESTAMP    NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    sum            DECIMAL      NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

INSERT INTO account (id, number, balance)
VALUES ('8b8753ad-a11a-4d71-b04d-fe82c8059b3a', '123456789123456789', 1000),
       ('c443bb81-49bf-44a1-ac7d-47b0c7845a37', '987654321987654321', 0);
