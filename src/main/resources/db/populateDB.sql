DELETE
FROM votes;
DELETE
FROM dishes;
DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM restaurants;
ALTER SEQUENCE global_seq restart with 100000;

INSERT INTO users (NAME, EMAIL, PASSWORD)
VALUES ('Petr', 'admin@yandex.ru', 'admin'),
       ('Sergey', 'user@gmail.com', 'pass123'),
       ('Anna', 'Bar@gmail.com', 's3cr3t');

INSERT INTO restaurants (NAME)
VALUES ('Restaurant_1'),
       ('Restaurant_2'),
       ('Restaurant_3');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'ADMIN'),
       (100000, 'USER'),
       (100001, 'USER'),
       (100002, 'USER');

INSERT INTO menus (RESTAURANT_ID, NAME, MENU_DATE)
VALUES (100003, 'Menu_1', '2020-01-21'),
       (100004, 'Menu_2', '2020-01-21'),
       (100005, 'Menu_3', '2020-01-20');

INSERT INTO dishes (MENU_ID, NAME, PRICE)
VALUES (100006, 'Dish_1', 300.00),
       (100006, 'Dish_2', 500.00),
       (100006, 'Dish_3', 250.00),
       (100007, 'Dish_4', 450.00),
       (100007, 'Dish_5', 720.00),
       (100007, 'Dish_6', 340.00),
       (100008, 'Dish_7', 90.00),
       (100008, 'Dish_8', 790.00),
       (100008, 'Dish_9', 290.00);

INSERT INTO votes (USER_ID, MENU_ID, VOTE_DATE)
VALUES (100000, 100006, '2020-01-21'),
       (100002, 100007, '2020-01-21'),
       (100001, 100006, '2020-01-21'),
       (100000, 100008, '2020-01-20'),
       (100002, 100008, '2020-02-20');
