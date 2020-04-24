On regroupe la liste des choses à faire / remarques qu'on fait ici, à lire avant chaque session de code.

# Client

# Serveur 

## 24/04 : 

### remarques globales : 

- attention a mettre les bon codes http a chaque fois, 200 = ok, 
par ex si je créer un compte alors que le mail existe deja, ca devrait pas renvoyer 200, mais le code approprié. 

- email sert a l'authentification (unique), id sert a designer quelqu'un (unique) mais sans partager son mail, par ex pour recuperer un profil, les utilisateurs n'ont pas besoin de savoir le mail des autres, on passera l'id de l'utilisateur dont on veut voir le profil, pseudo n'est pas unique et est ce qui s'affiche sur le site, dans les chats etc.
- attention a get / post /put / delete : on peut imaginer avec l'url /user : - get : recuperer un utilisateur (en donnant son id), - post : créer un utilisateur (signup) , - update : met a jour un utilisateur (update profil), - delete : supprime un utilisateur (update profile / remove profile), c'est conventionnel, mais aussi un choix, on prend une url (ici user) et get/post/put/delete sont définis dessus. par ex tu as fais un servlet pour signin, un signup, etc. c'est une façon de faire mais au final tu aura une seule méthode par servlet, tu aurais pu faire une servlet user, qui donc gerer les utilisateurs, et aurait eu les méthodes get/post/delete/put si dessus, qui auraient été appelé au bons endroits dans le client (respectivement consulté un user, créer un compte, mettre a jour son profile).

### choses à faire : 
- gestion des sessions. avec un url dédié : par ex GET sur /user qui renvoie si un utilisateur est connecté ou pas (via la session), si l'utilisateur est connecté ca devrait renvoyé l'utilisateur (id, pseudo, etc)
- url pour se deconnecter : /signout : doit détruire la session
- changé la facon de recuperer les infos d'une requete, on passe dans l'url uniquement des choses courte & futiles, par ex si je veux recuperer le profil de l'utilisateur 10,  je pourais faire /user?id=10, c'est court et ça ne dit pas grand chose. Par contre pour ce connecter, s'enregistrer, etc, il est plus conventionnel de passer par le body de la requete (sous format json) : le serveur recupere le body, le transform json > objet, et traite la requete. 
- on ne precise pas toute les infos a la creation d'un compte, juste pseudo/email/mdp (les choses obligatoires), le reste se mettera a jour dans update profile.
