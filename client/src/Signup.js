import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./style/signup.css";
import { Navigbar } from "./Navigbar";

export class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      // password
      password: "",
      passError: "",
      // pseudo
      pseudo: "",
      pseudoError: "",
      // gender
      gender: "h",
      formGotError: true
    };
  }

  handleChange = event => {
    this.setState(
      {
        [event.target.name]: event.target.value
      },
      () => {
        this.verifyPassword();
        this.verifyPseudo();
      }
    );
  };

  handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await axios.post("/user", {
        email: this.state.email,
        password: this.state.password,
        pseudo: this.state.pseudo,
        gender: this.state.gender
      });
      if (response.status === 200) {
        console.log("user created");
        this.props.history.push("/signin");
      }
    } catch (error) {
      console.log("ERREUR :", error);
    }
  };

  verifyPassword = () => {
    var passErr = "";
    if (this.state.password.length > 5) {
      passErr = "";
    } else {
      passErr = "Password is to short";
    }
    this.setState(
      {
        passError: passErr
      },
      () => {
        this.verifyForm();
      }
    );
  };

  verifyPseudo = () => {
    var nErr = "";
    if (this.state.pseudo.length > 3) {
      nErr = "";
    } else {
      nErr = "Pseudo is to short";
    }
    this.setState(
      {
        pseudoError: nErr
      },
      () => {
        this.verifyForm();
      }
    );
  };

  verifyForm = () => {
    const gotErr =
      this.state.passError + this.state.pseudoError === "" ? false : true;
    this.setState({
      formGotError: gotErr
    });
  };

  render() {
    return (
      <div className="mainDiv">
        <Navigbar islog="false" />
        <div className="signParent" method="none">
          <form onSubmit={this.handleSubmit}>
            <label>
              Pseudo : <br />
              <input
                type="text"
                name="pseudo"
                value={this.state.pseudo}
                placeholder="Pseudo"
                onChange={this.handleChange}
              />
            </label>

            <label>
              Email : <br />
              <input
                type="email"
                name="email"
                value={this.state.email}
                placeholder="email@example.com"
                onChange={this.handleChange}
              />
            </label>

            <label>
              Mot de passe : <br />
              <input
                type="password"
                name="password"
                value={this.state.password}
                onChange={this.handleChange}
              />
            </label>

            <label>
              Homme
              <input
                type="radio"
                name="gender"
                value="h"
                onChange={this.handleChange}
                checked
              />
            </label>

            <label>
              Femme
              <input
                type="radio"
                name="gender"
                value="f"
                onChange={this.handleChange}
              />
            </label>

            <input
              type="submit"
              className="formSubmit"
              value="Create an account"
              disabled={this.state.formGotError}
            />
          </form>

          <div className="signErrors">
            <label className="signError">{this.state.pseudoError}</label>
            <br />
            <label className="signError">{this.state.passError}</label>
          </div>
          <p>
            Déjà un comtpe ?
            <Link className="signLink" to="./signin">
              se connecter
            </Link>
          </p>
        </div>
      </div>
    );
  }
}