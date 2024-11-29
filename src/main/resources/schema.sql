CREATE TABLE IF NOT EXISTS tbl_items (
                                         id bigint not null auto_increment PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(7,2) NOT NULL DEFAULT 0,
    quantity INTEGER NOT NULL,
    category ENUM('GROCERIES', 'AUTOMOTIVE', 'CLOTHES', 'SHOES', 'ELECTRONICS') NOT NULL,
    create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS tbl_order (
                                         id bigint not null auto_increment PRIMARY KEY,
                                         status ENUM('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL, -- ENUM in Java, stored as string in SQL
    original_currency VARCHAR(3) NOT NULL, -- ISO currency code (e.g., "USD")
    target_currency VARCHAR(3) NOT NULL, -- ISO currency code (e.g., "EUR")
    org_curr_amount DECIMAL(7, 2) NOT NULL, -- BigDecimal equivalent
    discount DECIMAL(7, 2) NOT NULL, -- Discount in percentage (e.g., 10.00%)
    target_payable_amount DECIMAL(7, 2) NOT NULL,
    create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS tbl_order_item (
                                              id bigint not null auto_increment PRIMARY KEY,
                                              order_id BIGINT NOT NULL,
                                              item_id BIGINT NOT NULL,
                                              quantity INT NOT NULL,
                                              create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              update_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tbl_store_user (
                                              id bigint not null auto_increment PRIMARY KEY,
                                              name TEXT NOT NULL,
                                              email TEXT NOT NULL,
                                              password TEXT NOT NULL,
                                              role ENUM('EMPLOYEE', 'AFFILIATED', 'CUSTOMER') NOT NULL,
    create_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

