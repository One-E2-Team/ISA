
insert into pharmacies values (1, 'Bulevar oslobodjenja 1', 'Nebitan opis', 'Apoteka 1');
insert into pharmacies values (2, 'Bulevar oslobodjenja 2', 'Nebitan opis', 'Apoteka 2');
insert into pharmacies values (3, 'Bulevar oslobodjenja 3', 'Nebitan opis', 'Apoteka 3');
insert into pharmacies values (4, 'Bulevar oslobodjenja 4', 'Nebitan opis', 'Apoteka 4');

insert into medicines values (1, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 1', 0, false, 'nuspojave 1');
insert into medicines values (2, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 2', 0, false, 'nuspojave 2');
insert into medicines values (3, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 3', 0, false, 'nuspojave 3');
insert into medicines values (4, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 4', 0, false, 'nuspojave 4');
insert into medicines values (5, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 5', 0, false, 'nuspojave 5');
insert into medicines values (6, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 6', 0, false, 'nuspojave 6');

insert into equivalent_medicines values (1, 1, 2);
insert into equivalent_medicines values (2, 2, 1);
insert into equivalent_medicines values (3, 3, 4);
insert into equivalent_medicines values (4, 4, 3);

insert into all_users values ('PATIENT', 1, 'adresa', 'grad', 'mail1@gmail.com', true, 'ime 1', 'prezime 1', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);
insert into all_users values ('PATIENT', 2, 'adresa', 'grad', 'mail2@gmail.com', true, 'ime 2', 'prezime 2', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);
insert into all_users values ('PATIENT', 3, 'adresa', 'grad', 'mail3@gmail.com', true, 'ime 3', 'prezime 3', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);
insert into all_users values ('PATIENT', 4, 'adresa', 'grad', 'mail4@gmail.com', false, 'ime 4', 'prezime 4', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);
insert into all_users values ('PATIENT', 5, 'adresa', 'grad', 'mail5@gmail.com', false, 'ime 5', 'prezime 5', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);

insert into all_users values ('DEALER', 6, 'adresa', 'grad', 'mail6@gmail.com', false, 'ime 6', 'prezime 6', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 5, null, null, null, null);
insert into all_users values ('DEALER', 7, 'adresa', 'grad', 'mail7@gmail.com', true, 'ime 7', 'prezime 7', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 5, null, null, null, null);

insert into all_users values ('HEALTH_WORKER', 8, 'adresa', 'grad', 'mail8@gmail.com', true, 'ime 8', 'prezime 8', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 2, null, null, null, null);
insert into all_users values ('HEALTH_WORKER', 9, 'adresa', 'grad', 'mail9@gmail.com', true, 'ime 9', 'prezime 9', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 2, null, null, null, null);
insert into all_users values ('HEALTH_WORKER', 10, 'adresa', 'grad', 'mail10@gmail.com', true, 'ime 10', 'prezime 10', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 2, null, null, null, null);

insert into all_users values ('HEALTH_WORKER', 11, 'adresa', 'grad', 'mail11@gmail.com', true, 'ime 11', 'prezime 11', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 1, null, null, 1, null);
insert into all_users values ('HEALTH_WORKER', 12, 'adresa', 'grad', 'mail12@gmail.com', true, 'ime 12', 'prezime 12', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 1, null, null, 2, null);
insert into all_users values ('HEALTH_WORKER', 13, 'adresa', 'grad', 'mail13@gmail.com', true, 'ime 13', 'prezime 13', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 1, null, null, 3, null);
insert into all_users values ('HEALTH_WORKER', 14, 'adresa', 'grad', 'mail14@gmail.com', true, 'ime 14', 'prezime 14', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 1, null, null, 4, null);
insert into all_users values ('HEALTH_WORKER', 15, 'adresa', 'grad', 'mail15@gmail.com', true, 'ime 15', 'prezime 15', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 1, null, null, 1, null);

insert into all_users values ('PHARMACY_ADMIN', 16, 'adresa', 'grad', 'mail16@gmail.com', true, 'ime 16', 'prezime 16', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 3, null, null, null, 1);
insert into all_users values ('PHARMACY_ADMIN', 17, 'adresa', 'grad', 'mail17@gmail.com', true, 'ime 17', 'prezime 17', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 3, null, null, null, 2);
insert into all_users values ('PHARMACY_ADMIN', 18, 'adresa', 'grad', 'mail18@gmail.com', true, 'ime 18', 'prezime 18', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 3, null, null, null, 3);
insert into all_users values ('PHARMACY_ADMIN', 19, 'adresa', 'grad', 'mail19@gmail.com', true, 'ime 19', 'prezime 19', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 3, null, null, null, 4);
insert into all_users values ('PHARMACY_ADMIN', 20, 'adresa', 'grad', 'mail20@gmail.com', true, 'ime 20', 'prezime 20', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 3, null, null, null, 1);

insert into all_users values ('SYSTEM_ADMIN', 21, 'adresa', 'grad', 'mail21@gmail.com', true, 'ime 21', 'prezime 21', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 4, null, null, null, null);


insert into dermatologists_in_pharmacies values (8, 1);
insert into dermatologists_in_pharmacies values (9, 4);
insert into dermatologists_in_pharmacies values (10, 1);
insert into dermatologists_in_pharmacies values (10, 2);


insert into subscriptions values (1, 1);
insert into subscriptions values (1, 2);
insert into subscriptions values (2, 2);


insert into all_users_allergies values (1, 6);
insert into all_users_allergies values (1, 4);
insert into all_users_allergies values (3, 4);
insert into all_users_allergies values (4, 1);


insert into warehouses values (1, 10, 0, 1, 1);
insert into warehouses values (2, 10, 0, 2, 1);
insert into warehouses values (3, 20, 0, 3, 1);
insert into warehouses values (4, 30, 0, 4, 1);
insert into warehouses values (5, 10, 0, 2, 2);
insert into warehouses values (6, 10, 0, 5, 2);
insert into warehouses values (7, 20, 0, 6, 3);
insert into warehouses values (8, 10, 0, 1, 4);


insert into pricelists values (1, '2021-02-20 00:00:00', 500, '2021-02-01 00:00:00', 1, 1);
insert into pricelists values (2, '2021-02-20 00:00:00', 700, '2021-02-01 00:00:00', 3, 1);
insert into pricelists values (3, '2021-02-20 00:00:00', 400, '2021-02-01 00:00:00', 4, 1);
insert into pricelists values (4, '2021-02-20 00:00:00', 300, '2021-02-01 00:00:00', 2, 2);
insert into pricelists values (5, '2021-02-20 00:00:00', 790, '2021-02-01 00:00:00', 5, 2);
insert into pricelists values (6, '2021-02-20 00:00:00', 550, '2021-02-01 00:00:00', 6, 3);
insert into pricelists values (7, '2021-02-20 00:00:00', 320, '2021-02-01 00:00:00', 1, 4);


insert into working_calendars values (1, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 11, 1);
insert into working_calendars values (2, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 12, 2);
insert into working_calendars values (3, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 13, 3);
insert into working_calendars values (4, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 14, 4);
insert into working_calendars values (5, '2021-02-20 00:00:00', '2021-02-20 22:00:00', '2021-02-01 00:00:00', '2021-02-20 17:00:00', 15, 1);

insert into working_calendars values (6, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 8, 1);
insert into working_calendars values (7, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 09:00:00', 9, 4);
insert into working_calendars values (8, '2021-02-20 00:00:00', '2021-02-20 17:00:00', '2021-02-01 00:00:00', '2021-02-20 13:00:00', 10, 1);
insert into working_calendars values (9, '2021-02-20 00:00:00', '2021-02-20 12:00:00', '2021-02-01 00:00:00', '2021-02-20 07:00:00', 10, 2);




insert into examinations values (1, '2021-02-15 00:00:00', '2021-02-15 08:00:00', 'dijagnoza 23', 1000, '2021-02-15 07:30:00', 0, 1, 8, null,1);
insert into examinations values (2, '2021-02-15 00:00:00', '2021-02-15 08:30:00', 'dijagnoza 24', 1000, '2021-02-15 08:00:00', 0, 1, 9, null,2);
insert into examinations values (3, '2021-02-15 00:00:00', '2021-02-15 09:00:00', 'dijagnoza 25', 1000, '2021-02-15 08:30:00', 0, 1, 10, null,3);

insert into examinations values (4, '2021-02-15 00:00:00', '2021-02-15 08:00:00', 'dijagnoza 23', 1000, '2021-02-15 07:30:00', 0, 1, 11, null,1);
insert into examinations values (5, '2021-02-15 00:00:00', '2021-02-15 08:00:00', 'dijagnoza 24', 1000, '2021-02-15 08:00:00', 0, 1, 12, null,1);
insert into examinations values (6, '2021-02-15 00:00:00', '2021-02-15 08:00:00', 'dijagnoza 25', 1000, '2021-02-15 08:30:00', 0, 1, 13, null,1);



insert into medicine_reservations values (1, '2021-02-10 10:00:00', 0, 2, 3, 2);
insert into medicine_reservations values (2, '2021-02-12 10:00:00', 2, 3, 3, 1);
insert into medicine_reservations values (3, '2021-02-12 10:00:00', 0, 6, 3, 3);


insert into rated_pharmacies values (1, 5, 2, 1);
insert into rated_pharmacies values (2, 3, 1, 2);
insert into rated_pharmacies values (3, 4, 3, 3);
insert into rated_pharmacies values (4, 3, 1, 1);
insert into rated_pharmacies values (5, 4, 3, 2);
insert into rated_pharmacies values (6, 5, 2, 3);
insert into rated_pharmacies values (7, 4, 3, 4);
insert into rated_pharmacies values (8, 5, 2, 4);


insert into rated_health_workers values (1, 5, 11, 1);
insert into rated_health_workers values (2, 4, 12, 2);
insert into rated_health_workers values (3, 3, 13, 3);
insert into rated_health_workers values (4, 3, 14, 4);
insert into rated_health_workers values (5, 4, 15, 2);
insert into rated_health_workers values (6, 5, 11, 3);
insert into rated_health_workers values (7, 2, 12, 1);
insert into rated_health_workers values (8, 5, 13, 4);
insert into rated_health_workers values (9, 3, 14, 2);
insert into rated_health_workers values (10, 4, 15, 1);

INSERT INTO authority (name) VALUES ('ROLE_PATIENT');
INSERT INTO authority (name) VALUES ('ROLE_PHARMACIST');
INSERT INTO authority (name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO authority (name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_SYSTEM_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_DEALER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (5, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (9, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (10, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (11, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (12, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (13, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (14, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (15, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (16, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (17, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (18, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (19, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (20, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (21, 5);


INSERT INTO complaints values(1, null, 'Rados je nevaspitan!', 8, false, 1, 3);
INSERT INTO complaints values(2, null, 'Igor ne zna Vue.js!', 16, false, 1, 3);

INSERT INTO loyalty values(1,0.0,0,0,0);
INSERT INTO loyalty values(2,0.0,0,0,1);
INSERT INTO loyalty values(3,0.3,0,0,2);


insert into medicines_with_quantity values(1, 10, 1);
insert into medicines_with_quantity values(2, 100, 2);
insert into medicines_with_quantity values(3, 9, 1);
insert into medicines_with_quantity values(4, 17, 2);
insert into medicines_with_quantity values(5, 19, 3);
insert into medicines_with_quantity values(6, 5, 4);
insert into medicines_with_quantity values(7, 17, 2);
insert into medicines_with_quantity values(8, 17, 2);
insert into medicines_with_quantity values(9, 35, 2);
insert into medicines_with_quantity values(10, 10, 1);
insert into medicines_with_quantity values(11, 100, 2);
insert into medicines_with_quantity values(12, 1, 1);
insert into medicines_with_quantity values(13, 3, 2);
insert into orders values(1, 20, '2021-02-15 00:00:00', false, 1);
insert into orders values(2, 16, '2021-02-05 00:00:00', true, 1);
insert into orders values(3, 17, '2021-02-20 00:00:00', false, 2);
insert into orders values(4, 16, '2021-02-28 00:00:00', false, 1);
insert into orders values(5, 16, '2021-02-08 00:00:00', false, 1);
insert into orders_medicines_with_quantity values(2, 3);
insert into orders_medicines_with_quantity values(2, 4);
insert into orders_medicines_with_quantity values(1, 5);
insert into orders_medicines_with_quantity values(1, 6);
insert into orders_medicines_with_quantity values(3, 7);
insert into orders_medicines_with_quantity values(4, 8);
insert into orders_medicines_with_quantity values(5, 12);
insert into orders_medicines_with_quantity values(5, 13);
insert into all_users_medicines_with_quantity values(7, 2);
insert into all_users_medicines_with_quantity values(7, 1);
insert into all_users_medicines_with_quantity values(6, 11);
insert into all_users_medicines_with_quantity values(6, 10);
insert into offers values(1, '2021-02-06 00:00:00', 6969, 1, 7, 2);
insert into offers values(2, '2021-02-07 00:00:00', 666, 2, 6, 2);
insert into offers values(3, '2021-02-26 00:00:00', 69, 0, 7, 5);
insert into offers values(4, '2021-02-27 00:00:00', 169, 0, 6, 5);

