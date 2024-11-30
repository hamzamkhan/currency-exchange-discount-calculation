INSERT INTO tbl_store_user(name, email, password, role, create_timestamp, update_timestamp) VALUES ('Hamza Mustafa Khan', 'hamza@khan.com', '$2a$10$GnumULeYv/Z0Pqa7A9X1nOxKQtXMUH6UOzthxAW/pEhuixi9e751C', 'CUSTOMER', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO tbl_store_user(name, email, password, role, create_timestamp, update_timestamp) VALUES ('Hamza Khan', 'hamza1@khan.com', '$2a$10$GnumULeYv/Z0Pqa7A9X1nOxKQtXMUH6UOzthxAW/pEhuixi9e751C', 'EMPLOYEE', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO tbl_store_user(name, email, password, role, create_timestamp, update_timestamp) VALUES ('Hamza M Khan', 'hamza2@khan.com', '$2a$10$GnumULeYv/Z0Pqa7A9X1nOxKQtXMUH6UOzthxAW/pEhuixi9e751C', 'AFFILIATE', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO tbl_store_user(name, email, password, role, create_timestamp, update_timestamp) VALUES ('Hamza', 'hamza3@khan.com', '$2a$10$GnumULeYv/Z0Pqa7A9X1nOxKQtXMUH6UOzthxAW/pEhuixi9e751C', 'AFFILIATE', '2020-11-29 17:49:26', CURRENT_TIMESTAMP());


INSERT INTO tbl_items (name, description, price, quantity, category) VALUES
('Apple', 'Fresh and crisp red apples', 1.20, 100, 'GROCERIES'),
('Banana', 'Ripe yellow bananas, naturally sweet', 0.80, 150, 'GROCERIES'),
('Rice Bag', '5kg bag of premium white rice', 10.00, 50, 'GROCERIES'),
('Milk', '2L full cream milk', 2.50, 80, 'GROCERIES'),
('Orange Juice', 'Freshly squeezed orange juice, 1L', 3.20, 60, 'GROCERIES'),
('Laptop', '15.6-inch lightweight laptop with 16GB RAM', 850.00, 15, 'ELECTRONICS'),
('Smartphone', 'Latest 5G smartphone with 256GB storage', 1000.00, 20, 'ELECTRONICS'),
('Headphones', 'Bluetooth noise-canceling headphones', 150.00, 30, 'ELECTRONICS'),
('Smartwatch', 'Wearable smartwatch with health tracking', 200.00, 25, 'ELECTRONICS'),
('Smart TV', '65-inch 4K UHD Smart TV with HDR', 1200.00, 10, 'ELECTRONICS'),
('Car Battery', 'High-performance 12V car battery', 120.00, 40, 'AUTOMOTIVE'),
('Engine Oil', 'Fully synthetic motor oil, 4L', 45.00, 70, 'AUTOMOTIVE'),
('Car Tires', 'All-season radial tires, set of 4', 300.00, 15, 'AUTOMOTIVE'),
('Jeans', 'Classic fit blue denim jeans', 40.00, 50, 'CLOTHES'),
('Running Shoes', 'Comfortable and lightweight running shoes', 75.00, 40, 'SHOES');