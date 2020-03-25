# Dossier de projet : Partage autour d'événements.  
  
## Sujet et fonctionnalités  
  
L'application a pour but de créer du lien social autour d'événements.  
  
Les utilisateurs pourront consulter les prochains événements atours d'eux ou pour un lieu donner (concerts, spéctacles, festivals, etc.) et indiquer qu'ils participent à un événement.  
  
Ils pourront voir quels autres utilisateurs participent également et à terme l'application permetterai de contacter les autres utilisateurs via un système de messagerie intégré (si le temps le permet) qui serait un chat public par événement.  
  
## API utilisée  
  
Pour récupérer les informations concernant les événements, l'API Ticketmaster sera utilisé : https://developer.ticketmaster.com/products-and-docs/apis/getting-started/  
  
Voici les différentes données pouvant être récupérées :  
  
![](https://developer.ticketmaster.com/assets/img/getting-started/data-model.png)  
  
Les accès à l'API se font par clé d'authentification (gratuite).  
Il y a une limite de 5 000 requêtes par jour pour l'offre gratuite, ce qui sera suffisant dans le cadre de ce projet.  
  
L'API permettra donc de retrouver des événements (notament par lieu) et les informations liées à ceux-ci (nom de l'événement, photos, date...).  
Le but est d'afficher de façon très sobre les informations principales concernant un événement et de mettre l'accent sur la discussion autour de l'événement.  
  
## Fonctionalités  
  
- Authentification  
- Modification / Suppression de profil  
- Récupérer une liste d'événement à partir d'un lieu (nom, date, photos, genre, description).  
- Indiquer une participation à un événement.  
- Pouvoir retrouver les événements auxquels on participe  
- Pouvoir consulter la liste des participants à un événement.  
- Pouvoir envoyer des messages sur le salon de chat de chaque événement (si assez de temps).  
  
## Base de données  
  
La base de donnée n'aura pas besoin de contenir les informations relatives au évènements, vu qu'elles seront récupérées via l'API, à chaque recherche d'événements que l'utilisateur demande.  
  
Les informations suivantes devront néanmoins être stockées :  
  
- Les informations relatives à l'**utilisateur** devront être stockés : identifiant, mot de passe, description, photo de profil (à voir), session, etc.  
  
- Les informations relatives à la **participation au évènements** : id d'événement (donner par L'API), id de l'utilisateur participant.  
  
- Les informations relatives au **salons de chat** :  
id d'événement (un salon publique par événement), messages dans le salon et pour chaque message, l'id de l'utilisateur qui a posté le message.  
  
## Mise à jour des données et appels à l'API  
  
L'API sera appelé uniquement pour récupérer les événements et informations associées, notamment lors de la recherche, l'utilisateur pou"ra entrer un lieu ou chercher autour de lui (coordonnées gps utilisées).  
  
La base de donnée sera mise à jour dans les cas de figures suivants :  
  
- Inscription / connection / suppression de compte / modification de profil : Mise à jour de la partie **utilisateur**.  
  
- Clique sur "participe"/"ne participe plus" sur un évènement : Mise à jour de la partie **participation au événements**.  
  
- Envoie un message dans un salon de chat : Mise à jour de la partie **salons de chat**  
  
## Description du serveur  
  
Le serveur offrira une API REST pour pouvoir interagir avec les données. Les ressources principales seront les suivantes :  
  
- utilisateurs ( /users ) : créer un compte, voir un profil, modifier son profil/le supprimer.  
- événements ( /events ) : voir des événements, participer/ne plus participer à un événement.  
- chats ( /rooms ) : voir les messages, poster un message.  
  
  
## Description du client  
  
Le client sera un site monopage, avec quatre "écrans" principaux :  
- Création de compte /Connection : fera des appels sur /users  
- Événements (page principale) : présentation des événements aux alentours, possibilité de chercher un événement (appel sur /events). Chaque événement possédera un bouton participer /ne plus participer (appel sur /events) ainsi qu'un bouton pour ouvrir le salon chat de l'événement.  
- Profil : consulter le profil de quelqu'un ou son profil, si on est sur son profil, on peut le modifier : appels sur /users.  
- Chats : voir les messages dans le salon, poster un message dans le salon : appels sur /rooms.  
  
## Requêtes  
  
L'API serveur proposera de faire des requêtes CRUD classiques via HTTP.  
  
La transmission de données sera faite au format JSON : informations utilisateurs, événements, messages