import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./style/signup.css"
import { Navigbar } from "./Navigbar";
import Config from "./Config";

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
      const response = await axios.post(Config.BASE_URI + "/signup?email=" + this.state.email + "&mdp=" + this.state.password + "&pseudo=" + this.state.pseudo,
        {
          email: this.state.email,
          mdp: this.state.password,
          pseudo: this.state.pseudo
        }
      );
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
        <div className="formBox signParent p-3" method="none">
          <form onSubmit={this.handleSubmit}>
            <div className="form__group field">
              <input
                type="text"
                name="pseudo"
                value={this.state.pseudo}
                className="form__field"
                placeholder="Pseudo"
                onChange={this.handleChange}
              />
              <label for="email" className="form__label"><strong>Pseudo :</strong></label>
            </div>

            <div className="form__group field">
              <input
                type="email"
                name="email"
                className="form__field"
                value={this.state.email}
                placeholder="email@example.com"
                onChange={this.handleChange}
              />
              <label for="email" className="form__label"><strong>Email :</strong></label>
            </div>

            <div className="form__group field">
              <input
                type="password"
                name="password"
                className="form__field"
                value={this.state.password}
                placeholder="password"
                onChange={this.handleChange}
              />
              <label for="email" className="form__label"><strong>Mot de passe :</strong></label>
            </div>
            <br />

            <input
              type="submit"
              className="formSubmit"
              value="Creer un compte"
              disabled={this.state.formGotError}
            />
          </form>

          <div className="signErrors">
            <label className="signError">{this.state.pseudoError}</label>
            <br />
            <label className="signError">{this.state.passError}</label>
          </div>
          <p>
            <strong>Déjà un comtpe ?</strong> <Link className="signLink" to="./signin"> se connecter</Link>
          </p>
        </div>
      </div>
    );
  }
}
