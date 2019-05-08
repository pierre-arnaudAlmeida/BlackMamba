--Order of deletion of tables
--drop table employee;
--drop table historique;
--drop table gerer;
--drop table capteur;
--drop table partie_commune;
--drop table resident;
--drop table message;

--Destruction of base
DROP DATABASE pds;

--Creation base
CREATE DATABASE pds
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

--Creation tables
CREATE TABLE public.employee
(
    id_employee integer NOT NULL,
    nom_employee text COLLATE pg_catalog."default" NOT NULL,
    prenom_employee text COLLATE pg_catalog."default" NOT NULL,
    mot_de_passe text COLLATE pg_catalog."default" NOT NULL,
    poste text COLLATE pg_catalog."default",
    CONSTRAINT employee_pkey PRIMARY KEY (id_employee)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.employee
    OWNER to postgres;
    
CREATE TABLE public.resident
(
    id_resident integer NOT NULL,
    nom_resident text COLLATE pg_catalog."default" NOT NULL,
    prenom_resident text COLLATE pg_catalog."default",
    CONSTRAINT resident_pkey PRIMARY KEY (id_resident)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.resident
    OWNER to postgres;

CREATE TABLE public.partie_commune
(
    id_partie_commune integer NOT NULL,
    nom_partie_commune text COLLATE pg_catalog."default" NOT NULL,
    etage_partie_commune integer NOT NULL,
    CONSTRAINT partie_commune_pkey PRIMARY KEY (id_partie_commune)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.partie_commune
    OWNER to postgres;

CREATE TABLE public.capteur
(
    id_capteur integer NOT NULL,
    type_capteur text COLLATE pg_catalog."default" NOT NULL,
    etat text COLLATE pg_catalog."default" NOT NULL,
    id_partie_commune integer,
    type_alert text COLLATE pg_catalog."default",
    sensibilite text COLLATE pg_catalog."default",
    heure_debut time without time zone,
    heure_fin time without time zone,
    parametre text COLLATE pg_catalog."default",
    mise_a_jour timestamp(4) without time zone,
    CONSTRAINT capteur_pkey PRIMARY KEY (id_capteur),
    CONSTRAINT id_partie_commune FOREIGN KEY (id_partie_commune)
        REFERENCES public.partie_commune (id_partie_commune) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.capteur
    OWNER to postgres;

CREATE INDEX fki_id_partie_commune
    ON public.capteur USING btree
    (id_partie_commune)
    TABLESPACE pg_default;

CREATE TABLE public.historique
(
    id_historique integer NOT NULL,
    date_historique timestamp without time zone NOT NULL,
    etat_capteur text COLLATE pg_catalog."default" NOT NULL,
    type_alerte text COLLATE pg_catalog."default" NOT NULL,
    id_capteur integer NOT NULL,
    CONSTRAINT historique_pkey PRIMARY KEY (id_historique),
    CONSTRAINT id_capteur FOREIGN KEY (id_capteur)
        REFERENCES public.capteur (id_capteur) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.historique
    OWNER to postgres;

CREATE TABLE public.badger
(
    id_resident integer NOT NULL,
    id_capteur integer NOT NULL,
    date_badger timestamp without time zone NOT NULL,
    CONSTRAINT badger_pkey PRIMARY KEY (id_resident, id_capteur),
    CONSTRAINT id_resident FOREIGN KEY (id_resident)
        REFERENCES public.resident (id_resident) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.badger
    OWNER to postgres;

CREATE INDEX fki_id_resident
    ON public.badger USING btree
    (id_resident)
    TABLESPACE pg_default;
    
CREATE TABLE public.message
(
    id_message integer NOT NULL,
    id_capteur integer,
    date_alerte timestamp without time zone,
    seuil text COLLATE pg_catalog."default",
    CONSTRAINT message_pkey PRIMARY KEY (id_message)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.message
    OWNER to postgres;
    