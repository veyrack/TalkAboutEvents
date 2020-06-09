# Installation 

Les exemples suivant sont donnés pour ubuntu 18.04, trouvez les commandes équivalentes pour votre système si nécéssaire. 

## Installation de la base de données

Installation de mysql : `sudo apt install mysql-server`


**Les commandes suivantes sont à effectués depuis le repertoire server :**

`sudo mysql`

Puis dans la console mysql :

Création de l'utilisateur talkaboutevents :

`CREATE USER 'talkaboutevents'@'localhost' IDENTIFIED WITH mysql_native_password BY 'talkaboutevents';`

Création de la bdd talkaboutevents :

`CREATE DATABASE talkaboutevents;`

Dons de privilèges sur la bdd :

`GRANT ALL ON talkaboutevents.* TO 'talkaboutevents'@'localhost';`

Se placer sur la bonne bdd :

`USE talkaboutevents;`

Remplissage de la bdd :

`SOURCE talkaboutevents.sql;`

## Lancement de serveur 

Le serveur est lancé via eclipse, le projet est déjà configuré, ayant pour dossier source *server*, dans eclipse, faire : 

`Open project from filesystem` et selectionné le dossier serveur. 

On pourra lancer le serveur embarqué tomcat (qui se lance sur le port 8080) via la vue "servers" de eclipse : `window > show view > servers`

## Lancement du client 

Pour lancer le client, vous devez installé npm & nodejs :

`sudo apt install nodejs`

et :

`sudo apt install npm`

On peut maintenant lancé le client en ce placant dans le dossier 'client' et en executant : `npm start`

Une fois le client lancé (il se lance sur le port 3000), votre navigateur devrait ouvrir une page internet, si il ne le fait pas, vous pouvez acceder à l'url :

`http://localhost:3000/`

Le client et le serveur peuvent maintenant communiqué. 


## Organisation du serveur

### packages

- db : doit contenir une classe par table de la db, chaque classe réalisant les requêtes associés à la table (une classe User, une Message, etc)

- security : contient tout ce qui est relatif a la sécurité de l'app, gestion des CORS, authentification, etc.

- members : gère l'inscription/connexion/déconnexion

puis un package par entité / chemin, par exemple le package user gèrera tout les chemins /user, pareil pour messages, events, etc.

### Routes 

Voici la spécifications des chemins du serveur. Il faut bien penser à gérer les eventuelles cas d'erreurs en renvoyant les bons codes HTTP.

- /user  
    - get : vérifie si l'utilisateur à une session, s'il n'en a pas on renvoie une erreur accès non autorisé, si il en à une on renvoie l'utilisateur associé à cette session. si un champ id est passé a la requete, on renvoie simplement l'utilisateur d'id id (attention pas renvoyé mdp...)
    - update : met a jour l'utilisateur de la session, attention un ou plusieurs champs peut etre mis à jour. Si le mdp est mis à jour, il doit être crypté. 
    - delete : supprime l'utilisateur, on invalide sa session 

- /signin 
    - get : ok si les identifiants sont les bons, code erreur sinon

- /signup 
    - post : enregistre l'utiisateur si les champs sont ok, erreur sinon

- /signout 
    - get : détruit la session

- /message a voir plus tard, ca risque d'etre particulier

- /events 
    - get : récupère les evenements, des filtres doivent pouvoir être appliqué (il faut qu'on ce mette d'accord), au debut on va faire simple : uniquement par localisation (a voir si on passe un nom ou des coordonnées gps selon l'api)
    - post : avec un id d'event et l'utilisateur de la session, indique que l'utilisateur participe a l'evennement (faire ca plus tard, pas une prioritée du tout)
    - delete : avec un id d'event, enleve la participation de l'utilisateur a l'evenement  
