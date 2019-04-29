# SmartHome Services (SHS)
Notre projet consiste a  securiser la condition de vie des residents et pour
ameliorer la reactivite des employes face aux differents problemes. 

L'IHM developpe va permettre : 
							- Enrengistrer les utilisateurs dans la base de donnee
							- Selectionner des utilisateurs dans la base de donnee 
							- Mettre a  jour la base de donnee 
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

```

# I - MOCK 
## Generation jar 

Se placer, dans le module  maven gui present dans le dossier BlackMamb

Pour generer le fichier JAR du mock, il faut faire

```

un clic droit sur le fichier Â« pom.xml Â» du dossier  mock, ensuite Â« Ru

As Â» et enfin cliquer sur Â« Maven Install Â

```

Le fichier jar "mock-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est generer

## Lancement du mock

Lancer le Mock.jar, qui va  permettre de permettront de reproduire le comportement d'objet réels de maniere controlee. 
Cela va permettre de tester le comportement des objets réagissant sur l'IHM. 



# II - SERVEUR
## 1) Generer le jar Pool

Se placer, dans le module  maven pool present dans le dossier BlackMamba
Pour generer le pool.jar , il faut faire : 

```
un clic droit sur le fichier Â« pom.xml Â» du dossier pool, ensuite 
Â« Run As Â» et enfin cliquer sur Â« Maven Install Â»

```
Executer le jar du serveur 

`
Se placer dans le dossier BlackMamba, puis le dossier pool, et pour finir le dossier Target 
Il faut lancer ihm du serveur et cliquer sur lancer le serveur

```.
Double clique gauche sur "pool-0.0.1-SNAPSHOT-jar-with-dependencies.jar

```"
il s'agit du fichier jar generer dans le dossier target du IHM.
`
Le serveur est en marchee

```
Executer le jar  gui 
```
```
Se placer dans le dossier BlackMamba, puis le dossier IHM, et pour finir le dossier Target 


Double clique gauche sur "gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
, il s'agit du fichier jar generer dans le dossier target du IHM.

```
l'IHM se lance

```
## 2) Lancer le serveur

cliquer sur launch 




# III-IHM 
## 1) Generation du jar Deathkiss gui 
```
Ouvrir Eclipse, selectionner le projet en l'occurrence BlackMamba pour notre groupe puis selectionner le module maven gui .

```
Ouvrir le fichier « pom.xml».
```
Ensuite dans le fichier Â« pom.xml Â» nous pouvons modifier le groupId, qui correspond au nom du projet, pour notre groupe cela sera Â« BlackMamba Â»

```
 <groupId>com.blackmamba</groupId> 
```


Nous choisissons le nom du de l artefact, nous utiliserons 

```
<artifactId>deathkis_gui</artifactId>.

```
Donc notre fichier JAR aura pour nom actuellement :

```
 « deathkiss_gui-version.jar ».

```
Il nous faut donner une version a notre fichier JAR, notre premier fichier JAR sera donc en
version Â« 0.0.1 Â», l utilisation du mot SNAPSHOT est la pour signifier le developpement de la
version en cours.


le fichier jar sera cree dans le dossier
 
```
Â« target Â» du projet.
Le lieu d enrengistrement peut etre modifier.

``` 

```

## 2) Executer des tests
pre requis : 
- Serveur en marche 
- Gui en marche 
- BiAnalysis en marche 

###Test 1 Test Connexion identifiant test

Utiliser l'identifiant test 

```
Connexion reussie: acces a l'onglet insertion client 
```
acces a  la base de donnee 
```
Test avec un idenfiant non enrengistre, par exemple : Identifiant: 2 / mot de passe : popo
```
Erreur identifiant ou Mot de passe sont incorrect
```
IHM n'a pas pu se connecter sur la base de donnee 


###Test 2 Lire sur la base de donnee depuis l'IHM

```
Une fois identifie 

```
Cliquer sur liste des profils

```
une fenetre s'ouvre, nous obtenons les personnes presentes sur la base de donnee :

Id employa© 1  || Votre nom Almeida || prenom arnaud 


```
Pour verifier l'exactitude des donnees sur le lien de la base de donnee

```
Verifier sur la base de donnee que les personnes presentes sur l'ihm sont les maªmes personnes sur la base de donnee de Postgree

Id employa© 1  || Votre nom Almeida || prenom arnaud || mot_de_passe : plc
```
###Test 3 Ecrire sur la base de donnee depuis l'IHM 

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

###Test 4 Effectuer une  Mise a  jour 

```
Une fois identifie 

```
une fenetre s'ouvre, nous obtenons les personnes enrengistre sur la base de donnee, modifier le Prenom :  et/ ou Nom : 

```
Verifier sur la base de donnee que la mise a  jour sur l'ihm s'est realise sur la base de donnee de Postgree
Voici le lien : 

```



 
```
Le fichier jar "gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```

`

Generation BiAnalysis.jar 

Se placer, dans le module  maven gui present dans le dossier BlackMamba puis module "BiAnalysis
Pour generer le fichier JAR de BiAnalysis, il faut faire 
```
un clic droit sur le fichier Â« pom.xml Â» du dossier IHM, ensuite Â« Run
As Â» et enfin cliquer sur Â« Maven Install Â»
 
```
Le fichier jar "BiAnalysis-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est genere
```


# Acceder a  notre documentationn

Pour connaitre les differentes commandes de notre projet, vous pouvez vous rendre sur https://drive.google.com/drive/folders/1Whu-TJRyjkN2xbLY668l2u1LvBM6SHde


# Historique de versionn

Pour retrouver un historique de nos versions, regardez directement les [tags du repository] https://github.com/pierre-arnaudAlmeida/BlackMamba.git

# Auteurs

* **BlackMamba** - *Travail d'equipe* - 


