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

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(20)  NOT NULL,
    email    VARCHAR(50)  NOT NULL,
    password VARCHAR(120) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UK_users_username (username),
    UNIQUE KEY UK_users_email (email)
);

CREATE TABLE roles
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES ('ROLE_USER');
INSERT INTO roles (name)
VALUES ('ROLE_MANAGER');
INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT FK_user_roles_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT FK_user_roles_roles FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);
