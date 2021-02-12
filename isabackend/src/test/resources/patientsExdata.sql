insert into pharmacies values (1, 'Bulevar oslobodjenja 1', 'Nebitan opis',15.0, 0, 15.0, 'Apoteka 1', 0);
insert into all_users values ('PATIENT', 1, 'adresa', 'grad', 'mail1@gmail.com', true, 'ime 1', 'prezime 1', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);
insert into all_users values ('HEALTH_WORKER', 2, 'adresa', 'grad', 'mail8@gmail.com', true, 'ime 8', 'prezime 8', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 2, null, null, null, null);

insert into rated_health_workers values (1, 5, 2, 1);

insert into examinations values (1, '2021-02-20 00:00:00', '2021-02-20 08:00:00', 'dijagnoza 23', 1000, '2021-02-20 07:30:00', 4, 1, 2, 1,1);