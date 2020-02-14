INSERT INTO PATIENT (LASTNAME, FIRSTNAME, PHONE)
VALUES ('Филатов', 'Владислав', '7999999'),
       ('Амарян', 'Гурам', '7999999'),
       ('Щербаков', 'Алексей', '7999999'),
       ('Сабуров', 'Нурлан', '7999999'),
       ('Масаев', 'Тамби', '7999999'),
       ('Рептилоид', 'Рустам', '7999999'),
       ('Белый', 'Руслан', '7999999');

INSERT INTO DOCTOR (LASTNAME, FIRSTNAME, SPEC)
VALUES ('Быков', 'Андрей', 'Заведующий терапевтическим отделением больницы и руководитель интернатуры'),
       ('Быкова', 'Анастасия', 'Главный врач больницы'),
       ('Купитман', 'Иван', 'Доктор-дерматовенеролог, кандидат медицинских наук, заведующий кожно-венерологическим отделением больницы'),
       ('Алабаев', 'Тимур', 'Врач-терапевт');

INSERT INTO RECIPE (DOCTORID, PATIENTID, DESC, VALIDITY, PRIORITY)
VALUES (1, 1, 'Корвалол', 45, 'Cito'),
       (1, 2, 'Мезим', 50, 'Statim'),
       (2, 1, 'Цитрамон', 412, 'Normal'),
       (2, 3, 'Эспумизан', 24, 'Cito');