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

INSERT INTO restaurants (ID, NAME)
VALUES (100000, 'Скотина'),
       (100001, 'Бумбараш'),
       (100002, 'Fratelli');

INSERT INTO users (ID, NAME, EMAIL, PASSWORD)
VALUES (100300, 'Petr1488', 'PetrGo@yandex.ru', '123'),
       (100305, 'Woop', 'False@gmail.com', '1422'),
       (100308, 'Foo', 'Bar@gmail.com', '321');

INSERT INTO user_roles (user_id, role)
VALUES (100308, 'ADMIN'),
       (100300, 'USER'),
       (100305, 'USER');

INSERT INTO menus (ID, RESTAURANT_ID, NAME, MENU_DATE)
VALUES (100400, 100000, 'Unnamed', '2020-01-21'),
       (100401, 100001, 'Holiday-menu', '2020-01-21'),
       (100402, 100002, 'Unnamed', '2020-01-11');

INSERT INTO dishes (ID, MENU_ID, NAME, PRICE)
VALUES (101000, 100400, 'Кубанский борщ с копченой сметаной', 300),
       (101001, 100400, 'Свинина на гриле', 500),
       (101002, 100400, 'Колбаса чоризо', 250),
       (101003, 100401, 'Карпаччо из говядины', 450),
       (101004, 100401, 'Цезарь с крветками', 720),
       (101005, 100401, 'Хачапури по имеретински', 340),
       (101006, 100402, 'Хинкали с бараниной', 90),
       (101007, 100402, 'Тар-тар из лосося', 790),
       (101008, 100402, 'Оливье с курицей гриль', 290);

INSERT INTO votes (ID, MENU_ID, USER_ID, VOTE_DATE)
VALUES (102000, 100400, 100300, '2020-01-30 10:00:00'),
       (102001, 100402, 100305, '2020-01-30 10:00:00'),
       (102002, 100400, 100308, '2020-01-31 13:18:47'),
       (102003, 100401, 100305, '2020-01-31 17:54:31'),
       (102004, 100401, 100305, '2020-02-01 15:44:11');
