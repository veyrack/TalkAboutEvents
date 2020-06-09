import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import axios from "axios";

import User from "./User";
import Config from "./Config"

/**
 * Composant qui gère la deconnection
 */
export class Signout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true
    };
  }

  // on deconnecte l'utilisateur
  componentDidMount() {
    User.setIsLoggedIn(false);
    axios.get(Config.BASE_URI + "/signout", { withCredentials: true }).then(() => {
      this.setState({
        loading: false
      });
    });
  }

  render() {
    return this.state.loading ? (
      <div>LOADING</div>
    ) : (
        <Redirect to={{ pathname: "/signin" }} /> // redirection sur la page de connection
      );
  }
}
