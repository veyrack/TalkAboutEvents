import React, { Component } from "react";
import { Link } from "react-router-dom";

import User from "./User";
import "./style/navigbar.css";

/**
 * Barre de navigation 
 */
export class Navigbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: 1
    };
  }

  render() {
    return (
      <nav className="navigbar">
        <Link className="link" to="/">
          TalkAboutEvents
        </Link>
        {/* Si on est connecté on affiche un bouton vers la page profil */}
        {this.props.islog === "true" ? (
          <Link
            className="link"
            to={{
              pathname: "/profil/",
              search: "?id=" + User.getId()
            }}
          >
            Profil
          </Link>
        ) : null}
        {/* si on est connecté on affiche un bouton vers deconnection, sinon vers connection */}
        {this.props.islog === "true" ? (
          <Link className="link" to="/signout">
            Déconnexion
          </Link>
        ) : (
            <Link className="link" to="/signin">
              Connexion
            </Link>
          )}
      </nav>
    );
  }
}
