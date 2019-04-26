# SmartHome Services (SHS)
Notre projet consiste a� securiser la condition de vie des residents et pour
ameliorer la reactivite des employes face aux differents problemes. 

L'IHM developpe va permettre : 
							- Enrengistrer les utilisateurs dans la base de donnee
							- Selectionner des utilisateurs dans la base de donnee 
							- Mettre a� jour la base de donnee 
							- Supprimer des utilisateurs de la base de donnee



## Bien debuter

Ces instructions vont vous permettre d'effectuer des tests sur l'IHM.
Veuillez voir les notes ci-dessous pour constater comment fonctionne le projet sur une machine virtuelle.

### Prerequis

```
-Eclipse 
-Fichier jar IHM devra etre installe dans la VM 
-Fichier jar Serveur
-Fichier jar Mock
-Fichier jar BiAnalysis
-Un raccourci sera present sur le bureau de la VM
-Utilisateur test : Identifiant: 1  / Mot de passe : plc 
-Postgree installe
-

### Installation

## Procedure JAR

# Generation jar sous Eclipse 

Generation jar du dossier 
```
Ouvrir Eclipse, selectionner le projet en l'occurrence BlackMamba pour notre groupe puis selectionner le module maven gui .

```
Ouvrir le fichier � pom.xml�.

```
Ensuite dans le fichier « pom.xml » nous pouvons modifier le groupId, qui correspond au nom du projet, pour notre groupe cela sera « BlackMamba »

```
 <groupId>com.blackmamba</groupId> 
```


Nous choisissons le nom du de l artefact, nous utiliserons 
```
<artifactId>deathkiss</artifactId>.

```
Donc notre fichier JAR aura pour nom actuellement : « blackmamba-version.jar ».

```
Il nous faut donner une version a notre fichier JAR, notre premier fichier JAR sera donc en
version « 0.0.1 », l utilisation du mot SNAPSHOT est la pour signifier le developpement de la
version en cours.

```
le fichier jar sera cree dans le dossier « target » du projet.
Le lieu d enrengistrement peut etre modifier.
```


Generation Gui.jar

Se placer, dans le module  maven gui present dans le dossier BlackMamba
Pour generer le fichier JAR de l'IHM, il faut faire 
```
un clic droit sur le fichier « pom.xml » du dossier IHM, ensuite « Run
As » et enfin cliquer sur « Maven Install »
 
```
Le fichier jar "gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```


Generation Pool.jar
Se placer, dans le module  maven pool present dans le dossier BlackMamba
Pour generer le fichier JAR de l'IHM, il faut faire un clic droit sur le fichier « pom.xml » du dossier IHM, ensuite « Run
As » et enfin cliquer sur « Maven Install »
 
```
Le fichier jar "gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```

Generation Mock.jar 

Se placer, dans le module  maven gui present dans le dossier BlackMamba
Pour generer le fichier JAR du mock, il faut faire 
```
un clic droit sur le fichier « pom.xml » du dossier  mock, ensuite « Run
As » et enfin cliquer sur « Maven Install »
 
```
Le fichier jar "mock-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```

Generation BiAnalysis.jar 

Se placer, dans le module  maven gui present dans le dossier BlackMamba puis module "BiAnalysis
Pour generer le fichier JAR de BiAnalysis, il faut faire 
```
un clic droit sur le fichier « pom.xml » du dossier IHM, ensuite « Run
As » et enfin cliquer sur « Maven Install »
 
```
Le fichier jar "BiAnalysis-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```

# Executer les fichiers jar 

Executer le jar du serveur 

```
Se placer dans le dossier BlackMamba, puis le dossier pool, et pour finir le dossier Target 
Il faut lancer ihm du serveur et cliquer sur lancer le serveur.
Double clique gauche sur "pool-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
il s'agit du fichier jar generer dans le dossier target du IHM.
```
Le serveur est en marche

```
Executer le jar  gui 
```
```
Se placer dans le dossier BlackMamba, puis le dossier IHM, et pour finir le dossier Target 

```
Double clique gauche sur "gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
, il s'agit du fichier jar generer dans le dossier target du IHM.
```
```
l'IHM se lance



## Executer des tests
pre requis : 
- Serveur en marche 
- Gui en marche 
- BiAnalysis en marche 


# Simulation Mock 

Lancer le Mock.jar, qui va  permettre de permettront de reproduire le comportement d'objet r�els de maniere controlee. 
Cela va permettre de tester le comportement des objets r�agissant sur l'IHM. 

#Test 1 Test Connexion identifiant test

Utiliser l'identifiant test 

```
Connexion reussie: acces a l'onglet insertion client 
```
acces a� la base de donnee 
```
Test avec un idenfiant non enrengistre, par exemple : Identifiant: 2 / mot de passe : popo
```
Erreur identifiant ou Mot de passe sont incorrect
```
IHM n'a pas pu se connecter sur la base de donnee 


#Test 2 Lire sur la base de donnee depuis l'IHM

```
Une fois identifie 

```
Cliquer sur liste des profils

```
une fenetre s'ouvre, nous obtenons les personnes presentes sur la base de donnee :

Id employa� 1  || Votre nom Almeida || prenom arnaud 


```
Pour verifier l'exactitude des donnees sur le lien de la base de donnee

```
Verifier sur la base de donnee que les personnes presentes sur l'ihm sont les ma�mes personnes sur la base de donnee de Postgree

Id employa� 1  || Votre nom Almeida || prenom arnaud || mot_de_passe : plc
```
#Test 3 Ecrire sur la base de donnee depuis l'IHM 

```
Une fois identifie 

```
Inscrire une personne  

```
une fenetre s'ouvre, nous obtenons les personnes enrengistre sur la base de donnee, ajouter Prenom :  et Nom :  et Mot de passe : 

```
Cliquer sur "inscrire" 
```
Verifier sur la base de donnee que l'ecriture sur l'ihm s'est realise sur la base de donnee de Postgree
Voici le lien : 

#Test 4 Effectuer une  Mise a� jour 

```
Une fois identifie 

```
une fenetre s'ouvre, nous obtenons les personnes enrengistre sur la base de donnee, modifier le Prenom :  et/ ou Nom : 

```
Verifier sur la base de donnee que la mise a� jour sur l'ihm s'est realise sur la base de donnee de Postgree
Voici le lien : 

```





## Acceder a� notre documentation

Pour connaitre les differentes commandes de notre projet, vous pouvez vous rendre sur https://drive.google.com/drive/folders/1Whu-TJRyjkN2xbLY668l2u1LvBM6SHde


## Historique de version

Pour retrouver un historique de nos versions, regardez directement les [tags du repository] https://github.com/pierre-arnaudAlmeida/BlackMamba.git

## Auteurs

* **BlackMamba** - *Travail d'equipe* - 


