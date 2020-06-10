import React, { Component } from "react";
import axios from "axios";

import "./style/editProfil.css";
import User from "./User";
import Config from "./Config";
import { Button } from "react-bootstrap";

/**
 * Page de modification du profil, accessible uniquement pour 
 * le possesseur du profil 
 */
export class EditProfil extends Component {

  constructor(props) {
    super(props);
    this.state = {
    };
  }

  // envoie au serveur 
  handleSubmit = async e => {
    e.preventDefault();
    // si on met a jour la pdp, on la serialize
    let data = { ...this.state };
    if (data.picture != null) {
      data.pdp = await this.getBase64(data.picture);
      data.picture = null;
    }
    // on renvoie les modifs et redirige sur la page profil
    axios.put(Config.BASE_URI + "/user", data, { withCredentials: true }).then(resp => {
      if (resp.status === 200)
        this.props.history.push("/profil?id=" + User.getId());
    });
  };

  // mise a jour de l'état
  handleChange = e => {
    e.preventDefault();
    if (e.target.files != null) {
      // on a selectionner un fichier
      this.setState({
        [e.target.id]: e.target.files[0]
      });
    } else {
      // on a modifier autre chose
      this.setState({
        [e.target.id]: e.target.value
      });
    }
  };

  // transforme le fichier file en base64
  getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  // fonction de suppression de l'utilisateur 
  deleteUser() {
    axios.delete(Config.BASE_URI + "/user", { withCredentials: true }).then(() => {
      window.location.href = "/signin";
    });
  }

  render() {
    return (
      <div className="editParent">
        <form onSubmit={this.handleSubmit}>
          <label>Photo de profil :</label>
          <input
            type="file"
            id="picture"
            accept=".png"
            onChange={this.handleChange}
          />
          <label>Pseudo</label>
          <input
            type="text"
            id="pseudo"
            value={this.state.pseudo}
            placeholder="Pseudo ..."
            className="editinput"
            onChange={this.handleChange}
          />
          <label>Description</label>
          <input
            type="text"
            id="bio"
            value={this.state.bio}
            placeholder="Description ..."
            className="editinput"
            onChange={this.handleChange}
          />
          <input type="submit" className="editbutton" />
        </form>
        <Button variant="danger" onClick={this.deleteUser} >
          Supprimer le compte
        </Button>
      </div>
    );
  }
}
