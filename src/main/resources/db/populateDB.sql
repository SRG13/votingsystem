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
       ('Anna', 'bar@gmail.com', 's3cr3t');

INSERT INTO restaurants (NAME)
VALUES ('107 West'),
       ('11B EXPRESS'),
       ('Cesario');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'ADMIN'),
       (100000, 'USER'),
       (100001, 'USER'),
       (100002, 'USER');

INSERT INTO menus (RESTAURANT_ID, NAME, MENU_DATE)
VALUES  (100003, 'Weekend Brunch', '2020-09-05'),
        (100004, 'Homemade Specialties', '2020-09-05'),
        (100005, 'Special Menu', '2020-09-05'),
        (100004, 'Pizza Break', '2020-11-07'),
        (100005, 'Night Pastas', '2020-11-07'),
        (100003, 'Seafood Day', '2020-11-11'),
        (100005, 'Appetizers Sale', '2020-11-11');

INSERT INTO menus (RESTAURANT_ID, NAME)
VALUES (100003, 'Burrito Dinner'),
       (100004, 'Salad Morning');


INSERT INTO dishes (MENU_ID, NAME, PRICE)
VALUES (100006, 'Caesar Salad', 9.00),
       (100006, 'Aged Tofu', 7.50),
       (100006, 'Coconut Shrimp', 10.50),

       (100007, 'Homemade Lasagna', 12.00),
       (100007, 'Chicken Marsala', 14.00),
       (100007, 'Spinach Ravioli', 12.00),
       (100007, 'Manicotti', 11.00),

       (100008, 'Bruschetta', 10.00),
       (100008, 'Red Snapper', 29.00),
       (100008, 'Caesar Salad', 14.00),
       (100008, 'Buffalo Mozzarella', 12.00),

       (100009, 'Mozzarella Sticks', 6.00),
       (100009, '14 Medium Cheese Pizza', 13.00),
       (100009, 'Chicken Parmigiana', 10.00),

       (100010, 'Ravioli Fiorentina', 20.00),
       (100010, 'Meat Lasagna', 20.00),
       (100010, 'Conchiglie Gorgonzola', 25.00),
       (100010, 'Linguine Pancetta', 19.00),

       (100011, 'Grilled Norwegian Salmon', 21.00),
       (100011, 'Blackened Mississippi Catfish', 20.00),
       (100011, 'Pan Seared Atlantic Salmon', 23.00),

       (100012, 'Crab Cakes', 14.00),
       (100012, 'Caprese', 11.00),
       (100012, 'House Salad', 12.00),

       (100013, 'Cheese Burrito', 17.00),
       (100013, 'Smoked Chicken', 16.00),
       (100013, 'Pork Burrito', 11.50),
       (100013, 'Steak & Egg Burrito', 13.00),
       (100013, 'Tongue Burrito', 15.50),

       (100014, 'Arugula Salad', 9.50),
       (100014, 'Spinach with Goat Cheese Salad', 12.00),
       (100014, 'Caesar Salad', 10.00),
       (100014, 'Mescaline Mix with Grilled Chicken', 12.00);


INSERT INTO votes (USER_ID, MENU_ID, VOTE_DATE_TIME)
VALUES (100000, 100007, '2020-09-05 13:00:14'),
       (100001, 100007, '2020-09-05 08:10:03'),
       (100002, 100010, '2020-09-05 21:45:00'),

       (100001, 100010, '2020-11-07 10:01:11'),
       (100002, 100010, '2020-11-07 18:13:55'),

       (100000, 100011, '2020-11-11 19:55:43'),
       (100001, 100012, '2020-11-11 03:13:33'),
       (100002, 100011, '2020-11-11 01:00:03');



INSERT INTO votes (USER_ID, MENU_ID)
VALUES (100000, 100013),
       (100001, 100014);
