# SmartHome Services (SHS)
Notre projet consiste à sécuriser la condition de vie des résidents et pour
améliorer la réactivité des employés face aux différents problèmes. 

L'IHM développé va permettre : 
							- Enrengistrer les utilisateurs dans la base de donnée
							- Séléctionner des utilisateurs dans la base de donnée 
							- Mettre à jour la base de donnée 
							- Supprimer des utilisateurs de la base de donnée



## Bien débuter

Ces instructions vont vous permettre d'effectuer des tests sur l'IHM.
Veuillez voir les notes ci-dessous pour constater comment fonctionne le projet sur une machine virtuelle.

### Prérequis

```
-Eclipse 
-Fichier jar devra être installé dans la VM 
-Un raccourci sera présent sur le bureau de la VM
-Utilisateur test : Identifiant: 1  / Mot de passe : plc 
-Postgree installé


### Installation

## Procédure JAR

# Génération jar sous Eclipse 

```
Ouvrir Eclipse, séléctionner le projet en l’occurrence BlackMamba pour notre groupe.

```
Ouvrir le fichier « pom.xml ».

```
Ensuite dans le fichier « pom.xml » nous pouvons modifier le groupId, qui correspond au
nom du projet, pour notre groupe cela sera « BlackMamba ».

```

```
Nous choisissons le nom du de l’artefact, nous utiliserons « BlackMamba ». Donc notre
fichier JAR aura pour nom actuellement : « BlackMamba-version.jar ».

```
Il nous faut donner une version a notre fichier JAR, notre premier fichier JAR sera donc en
version « 0.0.1 », l’utilisation du mot SNAPSHOT est la pour signifier le développement de la
version en cours.

```
le fichier JAR sera crée dans le dossier « target » du projet.
Le lieu d’enregistrement peut être modifier.

```
Se placer, dans le dossier maven de l'IHM 
Pour générer le fichier JAR de l'IHM, il faut faire un clic droit sur le fichier « pom.xml » du dossier IHM, ensuite « Run
As » et enfin cliquer sur « Maven Install »
 
```
Le fichier jar "ihm-0.0.1-SNAPSHOT-jar-with-dependencies.jar" est généré
```

# Executer le jar 

```
Se placer dans le dossier BlackMamba, puis le dossier IHM, et pour finir le dossier Target 

```
Double clique gauche sur "ihm-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
, il s'agit du fichier jar généré dans le dossier target du IHM.
```
```
l'IHM se lance

```

## Exécuter des tests

#Test 1 Test Connexion identifiant test

```
Utiliser l'identifiant test 

```
Connexion réussie : accès à l'onglet insertion client 
```
accès à la base de donnée 
```
Test avec un idenfiant non enrengistré, par exemple : Identifiant: 2 / mot de passe : popo
```
Erreur identifiant ou Mot de passe sont incorrect
```
IHM n'a pas pu se connecter sur la base de donnée 
```

#Test 2 Lire sur la base de donnée depuis l'IHM

```
Une fois identifié 

```
Cliquer sur liste des profils

```
une fenêtre s'ouvre, nous obtenons les personnes enrengistrées sur la base de donnée :

Id employé 1  || Votre nom Almeida || Prénom arnaud 


```
Pour verifier l'exactitude des données sur le lien de la base de donnée

```
Verifier sur la base de donnée que les personnes présentes sur l'ihm sont les mêmes personnes sur la base de donnée de Postgree

Id employé 1  || Votre nom Almeida || Prénom arnaud || mot_de_passe : plc
```
#Test 3 Ecrire sur la base de donnée depuis l'IHM 

```
Une fois identifié 

```
Inscrire une personne  

```
une fenêtre s'ouvre, nous obtenons les personnes enrengistré sur la base de donnée, ajouter Prenom :  et Nom :  et Mot de passe : 

```
Cliquer sur "inscrire" 
```
Verifier sur la base de donnée que l'ecriture sur l'ihm s'est réaliser sur la base de donnée de Postgree
Voici le lien : 

#Test 4 Effectuer une  Mise à jour 

```
Une fois identifié 

```
une fenêtre s'ouvre, nous obtenons les personnes enrengistré sur la base de donnée, modifier le Prenom :  et/ ou Nom : 

```
Verifier sur la base de donnée que la mise à jour sur l'ihm s'est réaliser sur la base de donnée de Postgree
Voici le lien : 

```


## Accéder à notre documentation

Pour connaître les différentes commandes de notre projet, vous pouvez vous rendre sur https://drive.google.com/drive/folders/1p7Tpvd-S86IvIGy6OW-FZrqK7S58jRtA?usp=sharing


## Historique de version

Pour retrouver un historique de nos versions, regardez directement les [tags du repository] https://github.com/pierre-arnaudAlmeida/BlackMamba.git

## Auteurs

* **BlackMamba** - *Travaille d'equipe* - 


