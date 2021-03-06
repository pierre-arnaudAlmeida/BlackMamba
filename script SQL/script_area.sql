--Reinitialiser les compteurs des identifiants et insertions des parties communes de base
delete from capteur;
delete from partie_commune where id_partie_commune !=0;
select setval('partie_commune_id_partie_commune_seq', 1);
insert into partie_commune (id_partie_commune, nom_partie_commune, etage_partie_commune, surface, max_capteur) values (0, 'EMPTY', 0, 0, 9999);
insert into partie_commune (id_partie_commune, nom_partie_commune, etage_partie_commune, surface, max_capteur) values (1, 'ENTRANCE HALL E0', 0, 75, 17);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('LIVING ROOM E0', 0, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('SITTING ROOM E0', 0, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E0-A', 0, 25, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E0-B', 0, 25, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E0-C', 0, 30, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E0-D', 0, 30, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E0-E', 0, 30, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('ELEVATOR A E0', 0, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('ELEVATOR B E0', 0, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('STAFF ROOM E0', 0, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('KITCHEN E0', 0, 50, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('INFIRMARY E0', 0, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('RELAXATION ROOM E0', 0, 80, 19);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('DINING ROOM E0', 0, 50, 15);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('STAFF ROOM E1', 1, 50, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('LIVING ROOM A E1', 1, 50, 12);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-A', 1, 30, 16);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-B', 1, 25, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-C', 1, 25, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-D', 1, 30, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-E', 1, 30, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('CORRIDOR E1-F', 1, 30, 20);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('DINING ROOM E1', 1, 50, 15);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('INFIRMARY E1', 1, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('RELAXATION ROOM E1', 1, 80, 19);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('ELEVATOR A E1', 1, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('ELEVATOR B E1', 1, 5, 2);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('KITCHEN E1', 1, 50, 14);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('SITTING ROOM E1', 1, 50, 13);
insert into partie_commune (nom_partie_commune, etage_partie_commune, surface, max_capteur) values ('LIVING ROOM B E1', 1, 50, 13);