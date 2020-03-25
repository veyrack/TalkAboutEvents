import React, { Component } from "react";
import axios from "axios";

import "./style/editProfil.css";
import User from "./User";

export class EditProfil extends Component {
  constructor(props) {
    super(props);
    this.state = {
      description: ""
    };
  }

  handleSubmit = async e => {
    e.preventDefault();
    let data = { ...this.state };
    if (data.picture != null) data.picture = await this.getBase64(data.picture);
    // on renvoie les modifs et redirige sur profil
    // axios.put("/user", data).then(resp => {
    //   if (resp.status === 200)
    //     this.props.history.push("/profil?id=" + User.getId());
    // });
  };

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
          <label>Description</label>
          <input
            type="text"
            id="description"
            value={this.state.description}
            placeholder="Description ..."
            onChange={this.handleChange}
          />
          <input type="submit" />
        </form>
      </div>
    );
  }
}
