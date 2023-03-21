CREATE TABLE tables (
                        id INT NOT NULL AUTO_INCREMENT,
                        table_number INT NOT NULL,
                        number_of_seats INT NOT NULL,
                        PRIMARY KEY (id)
);

CREATE TABLE customers (
                           id INT NOT NULL AUTO_INCREMENT,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           email VARCHAR(100),
                           phone VARCHAR(20),
                           PRIMARY KEY (id)
);

CREATE TABLE orders (
                        id INT NOT NULL AUTO_INCREMENT,
                        table_id INT NOT NULL,
                        customer_id INT,
                        order_date DATE,
                        total DOUBLE NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (table_id) REFERENCES tables (id),
                        FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE menu_items (
                            id INT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            price DOUBLE NOT NULL,
                            PRIMARY KEY (id)
);

CREATE TABLE order_items (
                             id INT NOT NULL AUTO_INCREMENT,
                             order_id INT NOT NULL,
                             menu_item_id INT NOT NULL,
                             quantity INT NOT NULL,
                             price DOUBLE NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (order_id) REFERENCES orders (id),
                             FOREIGN KEY (menu_item_id) REFERENCES menu_items (id)
);

INSERT INTO tables (table_number, number_of_seats) VALUES (1, 4), (2, 2), (3, 6), (4, 8), (5, 2);

INSERT INTO customers (first_name, last_name, email, phone)
VALUES ('John', 'Doe', 'johndoe@example.com', '555-1234'),
       ('Jane', 'Smith', 'janesmith@example.com', '555-5678'),
       ('Bob', 'Johnson', 'bobjohnson@example.com', '555-4321');

INSERT INTO menu_items (name, description, price)
VALUES ('Steak', 'A juicy 8oz sirloin', 18.99),
       ('Salmon', 'Grilled salmon with a lemon butter sauce', 16.99),
       ('Burger', '1/3 pound Angus beef burger with fries', 11.99),
       ('Caesar Salad', 'Crisp romaine lettuce with parmesan cheese and croutons', 8.99);

INSERT INTO orders (table_id, customer_id, order_date, total) VALUES (1, 1, '2021-03-13',36.97);

INSERT INTO order_items (order_id, menu_item_id, quantity, price) VALUES (1, 1, 1, 18.99),(1, 4, 2, 17.98);