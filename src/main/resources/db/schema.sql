DROP TABLE user_role IF EXISTS;
DROP TABLE dish IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE vote IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE user IF EXISTS;

CREATE TABLE user
(
  id       INTEGER      PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  login    VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX user_unique_login_idx
  ON user (login);

CREATE TABLE user_role
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
  id      INTEGER      PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  phone   VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_phone_idx
  ON restaurant (phone);

CREATE TABLE menu (
  id            INTEGER PRIMARY KEY,
  restaurant_id INTEGER NOT NULL,
  date          DATE    NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE dish (
  id      INTEGER      PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  price   INTEGER      NOT NULL,
  menu_id INTEGER      NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE vote (
  id            INTEGER   PRIMARY KEY,
  restaurant_id INTEGER   NOT NULL,
  user_id       INTEGER   NOT NULL,
  datetime      TIMESTAMP NOT NULL,
  CONSTRAINT user_id_idx UNIQUE (user_id),
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);