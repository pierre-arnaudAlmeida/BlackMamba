--Reinitialiser les compteurs des identifiants et insertions des employees de la base
delete from employee;
select setval('employee_id_employee_seq', 1);
insert into employee (id_employee, nom_employee, prenom_employee, mot_de_passe, poste) values (1,'ALMEIDA','pierre','root','PDG');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('KEITA','raymond','raymond','VIGILE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LE DOUX','slayde','slayde','CUISINIER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SADDIKI','latifa','latifa','CUISINIERE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('DAS NEVES','nicolas','nicolas','PROFESSEUR DE MUSIQUE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GIRAUD','gilles','gilles','PROFESSEUR DE DANSE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('PIPARD','eric','eric','PROFESSEUR DE CHANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('PAULON', 'Allyson', 'f9rRZcah79', 'BUDGET/ACCOUNTING ANALYST IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('WYER', 'Sascha', 'b85kD31d', 'ACCOUNT REPRESENTATIVE I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CLOWSLEY', 'Alberto', 'tFXRwfkSV', 'SYSTEMS ADMINISTRATOR IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BYGRAVE', 'Ivor', '2SlchiPT', 'PROGRAMMER III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('TANCOCK', 'Rosalia', 'ElX645', 'PRODUCT ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('FANCOTT', 'Marcelia', 'NUlzRxgDT', 'SALES REPRESENTATIVE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CALLACHER', 'Evin', 'xIUmw5NwfJF', 'DIRECTOR OF SALES');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('AINSCOUGH', 'Cleveland', '741wDY3A', 'STRUCTURAL ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('PETERSEN', 'Roseanne', '6spKhEMxSlih', 'CLINICAL SPECIALIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GOUDIE', 'Liliane', 'l2cGdSQx399', 'SAFETY TECHNICIAN IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('KIBBY', 'Phillipe', 'adPVr7Ma', 'TAX ACCOUNTANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('KARPINSKI', 'Grannie', 'g2WATUh', 'PROJECT MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('FARR', 'Fowler', 'zAyShFDMkAX', 'STRUCTURAL ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('O'' CULLIGAN', 'Florence', '51uZQJPPoHpV', 'ADMINISTRATIVE ASSISTANT III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('FILDES', 'Philly', 'X0JY1MkvhHyP', 'PROJECT MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('JEANNEQUIN', 'Anatol', 'A7BAjVJk2', 'STAFF SCIENTIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MCMORLAND', 'Gianni', 'i4gZa7K', 'SALES ASSOCIATE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('PIOTR', 'Cori', 'cWeqmdwBu7', 'PARALEGAL');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('NUTLEY', 'Dedra', 'FPdZVLc', 'CIVIL ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('O''DONOVAN', 'Kurt', 'R1rfIwMA', 'DENTAL HYGIENIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CRISTOL', 'Issi', 'U2ypRU9NlUz', 'PROGRAMMER IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CASSLEY', 'Gal', 'C9adYE', 'DIRECTOR OF SALES');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('ALEKSEEV', 'Lura', 'NsKYJyD6b5', 'STATISTICIAN III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('STALLWOOD', 'Leticia', 'kCZh4u5VyJ4', 'ENVIRONMENTAL TECH');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HEALING', 'Maxy', 'TiAbri2C', 'BUSINESS SYSTEMS DEVELOPMENT ANALYST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LOWMAN', 'Marillin', 'iyYQ7XXfi', 'ACTUARY');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LUDGATE', 'Gerardo', 'amj2FuHndz', 'COMPUTER SYSTEMS ANALYST II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CUSTARD', 'Bonny', 'X8IrLJiUX', 'ASSISTANT PROFESSOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('DE MICHETTI', 'Gardiner', 'My3VMe', 'MEDIA MANAGER III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BETKE', 'Barby', 'eKIIKOCl', 'GEOLOGIST III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CLABBURN', 'Nathan', 'GApom3', 'SENIOR QUALITY ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BURCHALL', 'Olag', 'Rva0rUBW', 'ACCOUNT REPRESENTATIVE I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('DUCKERIN', 'Silva', 'p6hG2N4F8', 'HUMAN RESOURCES MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('TRIGHTON', 'Vance', 'uJSMUe', 'ANALYST PROGRAMMER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('YELIASHEV', 'Effie', 'C4DxE66', 'ADMINISTRATIVE OFFICER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SELDON', 'Kendrick', 'iSfPj6', 'SOCIAL WORKER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GAMON', 'Arlin', 'B8qWp4Wx', 'SENIOR EDITOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('FRANSCHINI', 'Rickert', 'F7Idnb', 'ACCOUNTANT I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GWIN', 'Wanda', 'wavqFgjkcR', 'ADMINISTRATIVE OFFICER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MEIGHAN', 'Obadiah', 'DBKUPWLaTqi', 'TAX ACCOUNTANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SALLINGS', 'Linoel', 'zVkWgjLBz', 'EDITOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MANDEVILLE', 'Hank', 'ThQIqlq', 'STAFF SCIENTIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LANKFORD', 'Errick', '2qJE6FO', 'GEOLOGIST III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('TOMNEY', 'Duky', 'KsfWw85dciw', 'NURSE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HOOKS', 'Larry', 'GV0FUdt0ol', 'ENVIRONMENTAL SPECIALIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MICHIELI', 'Tove', 'z2JGvQ', 'JUNIOR EXECUTIVE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CECCHETELLI', 'Collie', 'wnZSNMk82o0Z', 'NUCLEAR POWER ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LONGMUIR', 'Davis', 'FwkYCQoDMV10', 'ACCOUNT EXECUTIVE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MANCE', 'Cathlene', 'A9WewRCy5', 'FOOD CHEMIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BOWHAY', 'Shalne', 'exes7LB', 'ASSISTANT MEDIA PLANNER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BIGGERDIKE', 'Wye', '6Cuk1EA', 'BUDGET/ACCOUNTING ANALYST IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LOWDES', 'Alisa', 'vvXElBb', 'MEDIA MANAGER I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('DAVIO', 'Hy', 'nhZDwZ6JxXrB', 'ACCOUNT COORDINATOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BONIFANT', 'Amye', 'bLfFf5gmbLH', 'SAFETY TECHNICIAN I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GRAVET', 'Linoel', 'nz633j', 'STRUCTURAL ANALYSIS ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SEWARDS', 'Ronny', '7fCTXyEH', 'ASSISTANT PROFESSOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('O''HARE', 'Emlynn', 'zOTvJoEO', 'ADMINISTRATIVE OFFICER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('COSTER', 'Mable', 'VECKCd', 'SENIOR COST ACCOUNTANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('CLUELY', 'Kanya', 'izgXHHn', 'NURSE PRACTICIONER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MATTIONI', 'Miltie', '8qTESEAFk', 'REGISTERED NURSE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SIRET', 'Lodovico', '3LXKs9i3fl', 'HUMAN RESOURCES MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HORICK', 'Anny', 'JvW64i', 'STAFF ACCOUNTANT III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GOATHROP', 'Shel', 'Iv4rmxwjDmBo', 'SYSTEMS ADMINISTRATOR IV');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MILINGTON', 'Blythe', 'qq9rWis32', 'ANALYST PROGRAMMER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('FINNAN', 'Andy', 'w0SBYl', 'ENGINEER II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BURCH', 'Ellette', 'LVZwoZ1vnfgA', 'COMPUTER SYSTEMS ANALYST II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GUILBERT', 'Freda', 'FIltpyp', 'SOFTWARE TEST ENGINEER I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SHILLITTO', 'Zaneta', 'nfNvxB', 'SENIOR QUALITY ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MACGIOLLA', 'Clarice', '9EWFv8Lg', 'ELECTRICAL ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MCCAUGHAN', 'Roy', 'Oo1K0qnQaq3', 'NURSE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MCSHIRRIE', 'Yuma', 'C5nTLxiu4n', 'PHYSICAL THERAPY ASSISTANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MESSHAM', 'Del', 'BFh3HwtzaErg', 'ACTUARY');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('RIGDEN', 'Brinn', 'FciQHmHYhR7', 'ACCOUNTING ASSISTANT II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HOWSE', 'Chip', 'h91RhAW7UCi', 'INFORMATION SYSTEMS MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HARGATE', 'Fred', 'a6lajuKeEH', 'STAFF ACCOUNTANT I');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('LAWEE', 'Alfi', 'jrhy6mkpxCyP', 'SALES REPRESENTATIVE');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SWADLIN', 'Ivette', 'jVEeBeIoSxF2', 'SOCIAL WORKER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SALTER', 'Violette', 'NfrRvb2an', 'OCCUPATIONAL THERAPIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('KUPKE', 'Minerva', 'c6Q5yUnWJ', 'PARALEGAL');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('KINGHORNE', 'Joanne', 'rUChYa', 'PARALEGAL');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SIGGERS', 'Bond', 'mwgbw01w32Xi', 'SOFTWARE CONSULTANT');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BEHREND', 'Bartie', 'CLyHQ0GS', 'CHEMICAL ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('O''RODANE', 'Talbert', 'DlbNzk', 'INFORMATION SYSTEMS MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('WALNE', 'Cyndie', 'fAjzUEJ31', 'MARKETING MANAGER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HEGG', 'Issy', 'jiXJzxXZ', 'DATABASE ADMINISTRATOR II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BEHAN', 'Heddi', 'YByfzdvd5P', 'COMPENSATION ANALYST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HEAKER', 'Deedee', 'RcM4NJsqi', 'DATA COORDIATOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BUKAC', 'Denise', 'JV0XgOW', 'PROGRAMMER III');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('VIGNAL', 'Meryl', '7fgU98Lp1u8', 'VP QUALITY CONTROL');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MARLER', 'Lynde', 'gFQvFM1', 'PROFESSOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('SARFATI', 'Marlo', 'rL2FfJ5PtpK', 'GRAPHIC DESIGNER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('GEDNEY', 'Ker', '8EpaTbes', 'DIRECTOR OF SALES');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('STALEY', 'Kean', 'Lf4Lsp0K1R', 'DESIGN ENGINEER');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('TAGGETT', 'Alyce', 'GpxcLinLYZ', 'SPEECH PATHOLOGIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BRUNONE', 'Addy', 'U6AZXwHlbL', 'ASSOCIATE PROFESSOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('BERNARDOT', 'Angus', 'WjvbYT', 'OCCUPATIONAL THERAPIST');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('TOPING', 'Worden', 'vp4Ps07EHKs', 'FINANCIAL ADVISOR');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('MINERDO', 'Julianne', '7AdD8UWO5yK', 'DIRECTOR OF SALES');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('RADKI', 'Virgil', '0PJlio', 'OFFICE ASSISTANT II');
insert into employee (nom_employee, prenom_employee, mot_de_passe, poste) values ('HARHOFF', 'Rene', 'a12tSHxHa', 'RECRUITING MANAGER');