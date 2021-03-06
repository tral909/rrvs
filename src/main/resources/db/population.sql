DELETE FROM user;
DELETE FROM user_role;
DELETE FROM menu;
DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM vote;

INSERT INTO user (id, name, login, password) VALUES
  (1, 'tony', 'user', '{noop}1qaz2wsx'),
  (2, 'regorov', 'admin', '{noop}qwqwqw'),
  (3, 'nagorniy', 'nag22', '{noop}qweasd'),
  (4, 'autotest', 'userautotest', '{noop}zaq1xsw2'),
  (5, 'bortenev', 'bva31', '{noop}bvabva'),
  (6, 'dubenskiy', 'duboks', '{noop}qwe321'),
  (7, 'prebluda', 'KL', '{noop}1qaz2wsx'),
  (8, 'old admin', 'i_am_adm', '{noop}qwqwqw');

INSERT INTO user_role (user_id, role) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_USER'),
  (2, 'ROLE_ADMIN'),
  (3, 'ROLE_USER'),
  (4, 'ROLE_USER'),
  (5, 'ROLE_USER'),
  (6, 'ROLE_USER'),
  (7, 'ROLE_USER'),
  (8, 'ROLE_ADMIN');

INSERT INTO restaurant (id, name, phone, address) VALUES
  (1, 'CIN CIN', '+7(495)2236547', 'г. Москва, ул. Мирская, д. 12'),
  (2, 'Старый город', '+7(495)1245298', 'г. Москва, Старый Петровско-Разумовский проезд, д. 21/3'),
  (3, 'Obed.ru', '+7(495)7778878', 'г. Москва, уд. Строителей, д. 34'),
  (4, 'Клевер', '+7(4922)389104', 'г. Владимир, ул. Красная, д. 15'),
  (5, 'Golden cock', '+8(0456)25567890', 'Riga, Skarnu iela, 22');

INSERT INTO menu (id, restaurant_id, date) VALUES
 (1, 1, '2019-01-26'),
 (2, 2, '2019-01-26'),
 (3, 3, '2019-01-26'),
 (4, 4, '2019-01-26'),
 (5, 5, '2019-01-26');

INSERT INTO dish (id, name, price) VALUES
 (1, 'Картофель жареный', 35),
 (2, 'Рис', 30),
 (3, 'Гречка', 30),
 (4, 'Макароны', 25),
 (5, 'Плов', 55),
 (6, 'Сок яблочный', 45),
 (7, 'Сок апельсиновый', 50),
 (8, 'Чай', 30),
 (9, 'Кофе', 40),
 (10, 'Riga Balzams', 210),
 (11, 'Котлета из говядины', 110),
 (12, 'Курица в панировке', 120),
 (13, 'Стейк из лосося', 300),
 (14, 'Свиные медальоны', 130),
 (15, 'Пельмени', 90),
 (16, 'Рассольник', 55),
 (17, 'Куриная лапша', 50),
 (18, 'Борщ', 60),
 (19, 'Солянка мясная', 110),
 (20, 'Суп грибной', 60),
 (21, 'Салат овощной', 45),
 (22, 'Оливье', 55),
 (23, 'Капуста с морковкой', 25),
 (24, 'Винигрет', 30),
 (25, 'Крабовый салат', 60);

 INSERT INTO menu_dish (dish_id, menu_id) VALUES
 (1, 1),
 (2, 2),
 (3, 4),
 (4, 3),
 (5, 5),
 (6, 2),
 (7, 4),
 (8, 1),
 (9, 3),
 (10, 5),
 (11, 2),
 (12, 3),
 (13, 4),
 (14, 5),
 (15, 1),
 (16, 4),
 (17, 2),
 (18, 3),
 (19, 5),
 (20, 1),
 (21, 1),
 (22, 2),
 (23, 3),
 (24, 4),
 (25, 5);

INSERT INTO vote (id, restaurant_id, user_id, date) VALUES
 (1, 1, 1, '2019-01-26'),
 (2, 3, 1, '2019-01-27'),
 (3, 3, 3, '2019-01-26'),
 (4, 2, 4, '2019-01-26'),
 (5, 4, 2, '2019-01-26'),
 (6, 4, 5, '2019-01-26'),
 (7, 5, 6, '2019-01-26'),
 (8, 5, 7, '2019-01-26');