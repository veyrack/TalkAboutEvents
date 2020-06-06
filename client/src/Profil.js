import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./style/profil.css";
import User from "./User";
import Config from "./Config";

export class Profil extends Component {
  constructor(props) {
    super(props);
    this.id = parseInt(
      new URLSearchParams(this.props.location.search).get("id"),
      10
    );
  }

  componentDidMount() {
    // on recupère l'user concerné
    axios.get(Config.BASE_URI + "/user", { params: { id: this.id } }).then(resp => {
      this.setState({
        ...resp.data
      });
    });
  }

  render() {
    return (
      <div className="profilParent">
        {this.state == null ? (
          <p> loading ...</p>
        ) : (
            <div className="profilTop">
              <img src={this.state.pdp} alt="" />
              <div className="profilTopRight">
                <h1 className="profilPseudo"> {this.state.pseudo} </h1>
                <label className="profilDescription">
                  {this.state.bio}
                </label>
                {this.id === User.getId() ? (
                  <Link to="/editProfil" className="link">
                    Paramètres
                  </Link>
                ) : (
                    <div />
                  )}
              </div>
            </div>
          )}
      </div>
    );
  }
}
