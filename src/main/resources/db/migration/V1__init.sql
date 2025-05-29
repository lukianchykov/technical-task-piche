USE bankdb;

CREATE TABLE account
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    owner_name     VARCHAR(255) NOT NULL,
    balance        DOUBLE       NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);


CREATE TABLE account_transaction
(
    id                    BIGINT      NOT NULL AUTO_INCREMENT,
    account_id            BIGINT      NOT NULL,
    type                  VARCHAR(50)  NOT NULL,
    amount                DOUBLE      NOT NULL,
    description           VARCHAR(500),
    timestamp             DATETIME    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_account_transaction_account
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);