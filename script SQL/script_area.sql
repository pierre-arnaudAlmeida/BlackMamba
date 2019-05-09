--Reinitialiser les compteurs des identifiants et insertions des parties communes de base
delete from capteur;
delete from partie_commune;
select setval('partie_commune_id_partie_commune_seq', 1);
insert into partie_commune (id_partie_commune, nom_partie_commune, etage_partie_commune, surface, max_sensor) values (0, 'EMPTY', 0, 0, 9999);
insert into partie_commune (id_partie_commune, nom_partie_commune, etage_partie_commune, surface, max_sensor) values (1, 'ENTRANCE HALL', 0, 75, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('LIVING ROOM', 0, 50, 9);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('SITTING ROOM', 0, 50, 9);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E0-A', 0, 25, 10);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E0-B', 0, 25, 10);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E0-C', 0, 30, 10);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('ELEVATOR A', 0, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('ELEVATOR B', 0, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('DINING ROOM', 0, 50, 11);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('STAFF ROOM', 1, 50, 9);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('LIVING ROOM', 1, 50, 8);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E1-A', 1, 30, 12);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E1-B', 1, 25, 16);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E1-C', 1, 25, 16);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('CORRIDOR E1-D', 1, 30, 10);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_sensor) values ('DINING ROOM', 1, 50, 11);