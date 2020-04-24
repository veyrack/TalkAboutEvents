# Installation 

Les exemples suivant sont donnés pour ubuntu 18.04, trouvez les commandes équivalentes pour votre système si nécéssaire. 

## Installation de la base de données

Installation de mysql : 


**Les commandes suivantes sont à effectués depuis le repertoire server :**

`sudo mysql`

Puis dans la console mysql :

Création de l'utilisateur talkaboutevents :

`CREATE USER 'talkaboutevents'@'localhost' IDENTIFIED WITH mysql_native_password BY 'talkaboutevents';`

Création de la bdd talkaboutevents :

`CREATE DATABASE talkaboutevents`

Dons de privilèges sur la bdd :

`GRANT ALL ON talkaboutevents.* TO 'talkaboutevents'@'localhost';`

Remplissage de la bdd :

`SOURCE talkaboutevents.sql`

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
