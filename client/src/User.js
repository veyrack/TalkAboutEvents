import axios from "axios";
import Config from "./Config";

var User = (() => {
  // attributs
  let id;
  let pseudo;
  let picture;
  let description;
  let isLoggedIn = false;

  const set = (user, islog) => {
    id = user.id;
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
  }

  const getPseudo = () => {
    return pseudo;
  };

  const getPicture = () => {
    return picture;
  };

  const getDescription = () => {
    return description;
  };

  const login = (data) => {
    set(data, true)
  }

  // met a jour l'utilisateur après avoir verifier aupres du serveur
  const update = async () => {
    let response = await axios.get(Config.BASE_URI + "/user", { withCredentials: true });
    let user = response.data;
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
    getPseudo: getPseudo,
    getPicture: getPicture,
    getDescription: getDescription,
    getIsLoggedIn: getIsLoggedIn,
    login: login,
    update: update,
    setIsLoggedIn: setIsLoggedIn
  };
})();

export default User;
