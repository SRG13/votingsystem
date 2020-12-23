DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE restaurants
(
    id          INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name        VARCHAR UNIQUE NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE users
(
    id              INTEGER   DEFAULT nextval('global_seq') PRIMARY KEY,
    name            VARCHAR                 NOT NULL,
    email           VARCHAR                 NOT NULL,
    password        VARCHAR                 NOT NULL,
    registered_time TIMESTAMP(0) DEFAULT now() NOT NULL,
    enabled         BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_unique_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE menus
(
    id            INTEGER   DEFAULT nextval('global_seq') PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    name          VARCHAR,
    menu_date     DATE DEFAULT now(),
    CONSTRAINT menus_unique_date_restaurant_id_idx UNIQUE (menu_date, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id      INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    menu_id INTEGER       NOT NULL,
    name    VARCHAR       NOT NULL,
    price   DECIMAL(6, 2) NOT NULL,
    CONSTRAINT dishes_unique_idx UNIQUE (menu_id, name, price),
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id              INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    menu_id         INTEGER NOT NULL,
    user_id         INTEGER NOT NULL,
    vote_date_time  TIMESTAMP(0) DEFAULT now() NOT NULL,
    CONSTRAINT votes_unique_vote_date_user_id_idx UNIQUE (vote_date_time, user_id),
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
