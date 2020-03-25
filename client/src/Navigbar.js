import React, { Component } from "react";
import { Link } from "react-router-dom";

import User from "./User";
import "./style/navigbar.css";

export class Navigbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: 1
    };
  }

  handlecc = e => {
    e.preventDefault();
  };

  render() {
    return (
      <nav className="navigbar">
        <Link className="link" to="/">
          TalkAboutEvents
        </Link>
        <Link
          className="link"
          to={{
            pathname: "/profil/",
            search: "?id=" + User.getId()
          }}
        >
          Profil
        </Link>
        {this.props.islog === "true" ? (
          <Link className="link" to="/signout">
            Déconnection
          </Link>
        ) : (
            <Link className="link" to="/signin">
              Connection
            </Link>
          )}
      </nav>
    );
  }
}
