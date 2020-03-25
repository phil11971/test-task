INSERT INTO PATIENT (LASTNAME, FIRSTNAME, PHONE)
VALUES ('Filatov', 'Vlad', '8(927)729-86-66'),
       ('Amaryan', 'Guram', '8(927)729-86-66'),
       ('Scherbakov', 'Alex', '8(927)729-86-66'),
       ('Saburov', 'Nurlan', '8(927)729-86-66'),
       ('Masaev', 'Tambi', '8(927)729-86-66'),
       ('Reptiloid', 'Rustam', '8(927)729-86-66'),
       ('Belyi', 'Ruslan', '8(927)729-86-66');
INSERT INTO DOCTOR (LASTNAME, FIRSTNAME, SPEC)
VALUES ('Bikov', 'Andrey', 'Head of the therapeutic department of the hospital and head of internship'),
       ('Bikova', 'Anastasia', 'Head physician of the hospital'),
       ('Kupitman', 'Ivan', 'Doctor-Dermatovenerologist, Candidate of Medical Sciences, Head of the Department of Skin and Venereology'),
       ('Alabaev', 'Timur', 'General practitioner');
INSERT INTO RECIPE (DOCTORID, PATIENTID, DESCRIPTION, VALIDITYDATE, PRIORITY)
VALUES (1, 1, 'Corvalol', '2018-10-20', 'Cito'),
       (1, 2, 'Mezim', '2018-10-20', 'Statim'),
       (2, 1, 'Citramon', '2018-10-20', 'Normal'),
       (2, 3, 'Espumisan', '2018-10-20', 'Cito');