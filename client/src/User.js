import axios from "axios";

var User = (() => {
  // attributs
  let id;
  let email;
  let pseudo;
  let picture;
  let description;
  let isLoggedIn = false;

  const set = (user, islog) => {
    id = user.id;
    email = user.email;
    pseudo = user.pseudo;
    picture = user.picture;
    description = user.description;
    isLoggedIn = islog;
  };

  const setIsLoggedIn = val => {
    isLoggedIn = val;
  };

  const getId = () => {
    return id;
  };

  const getEmail = () => {
    return email;
  };

  const getPseudo = () => {
    return pseudo;
  };

  const getPicture = () => {
    return picture;
  };

  const getDescription = () => {
    return description;
  };

  // met a jour l'utilisateur après avoir verifier aupres du serveur
  const update = async () => {
    let user = (await axios.get("/user")).data;
    let islog = !(user.id == null);
    if (islog) {
      set(user, islog);
    }
  };

  // si l'utilisateur est log, return true, sinon verifie auprès du serveur et renvoie la reponse
  const getIsLoggedIn = async () => {
    if (!isLoggedIn) await update();
    return isLoggedIn;
  };

  return {
    getId: getId,
    getEmail: getEmail,
    getPseudo: getPseudo,
    getPicture: getPicture,
    getDescription: getDescription,
    getIsLoggedIn: getIsLoggedIn,
    update: update,
    setIsLoggedIn: setIsLoggedIn
  };
})();

export default User;
