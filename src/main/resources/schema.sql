CREATE TABLE IF NOT EXISTS account(
    id         CHAR(36)     NOT NULL,
    number     VARCHAR(225) NOT NULL,
    balance    DECIMAL      NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT uk_number UNIQUE (number)
);

CREATE TABLE IF NOT EXISTS transaction(
    id             CHAR(36)     NOT NULL,
    type           VARCHAR(50)  NOT NULL,
    create_date    TIMESTAMP    NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    sum            DECIMAL      NOT NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);
