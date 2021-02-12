insert into pharmacies values (1, 'Bulevar oslobodjenja 1', 'Nebitan opis',15.0, 0, 15.0, 'Apoteka 1', 0);
insert into medicines values (1, 100, 'sastav', 4, 'proizvodjac', 'oblik', 'tip', 'lek 1', 0, false, 'nuspojave 1');
insert into warehouses values (1, 10, 0, 0, 1, 1);
insert into all_users values ('PATIENT', 1, 'adresa', 'grad', 'mail1@gmail.com', true, 'ime 1', 'prezime 1', '$2y$10$0nxSL00whyxO4fwB17/qBODDSWDD5BYsJsO7tT.4XN83HqtigUpjm', 'telefon', 'drzava', 0, 0, 0, null, null);

insert into medicine_reservations values (1, '2021-02-20 10:00:00', 0, 1, 1, 1);