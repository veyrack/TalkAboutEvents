import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import { Navigbar } from "./Navigbar";
import User from "./User";
import "./style/bootstrap.min.css";
import Config from "./Config";

export class Signin extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      formGotError: true
    };
  }

  handleChange = event => {
    this.setState(
      {
        [event.target.name]: event.target.value
      },
      () => {
        this.verifyForm();
      }
    );
  };

  handleSubmit = async e => {
    e.preventDefault();
    try {
      const response = await axios.get(Config.BASE_URI + "/signin", {
        params: {
          email: this.state.email,
          mdp: this.state.password
        },
        withCredentials: true
      }
      );
      if (response.status === 200) {
        User.login(response.data);
        User.update().then(() => {
          this.props.history.push("/");
        });
      }
    } catch (error) {
      console.log("ERREUR :", error);
    }
  };

  verifyForm = () => {
    const gotErr =
      this.state.email.length < 1 || this.state.password < 1 ? true : false;
    this.setState({
      formGotError: gotErr
    });
  };

  render() {
    return (
      <div className="mainDiv ">
        <Navigbar islog="false" />
        <div className="formBox signParent p-3">
          <form onSubmit={this.handleSubmit}>

            <div className="form__group field">
              <input
                type="email"
                name="email"
                className="form__field"
                value={this.state.email}
                placeholder="email@example.com"
                onChange={this.handleChange}
              />
              <label htmlFor="email" className="form__label"><strong>Email :</strong></label>
            </div>

            <br />

            <div className="form__group field">
              <input
                type="password"
                name="password"
                className="form__field"
                value={this.state.password}
                placeholder="Mot de passe"
                onChange={this.handleChange}
              />
              <label htmlFor="password" className="form__label"><strong>Mot de passe :</strong></label>
            </div>
            <br />

            <input
              type="submit"
              className="formSubmit"
              onSubmit={this.handleSubmit}
              disabled={this.state.formGotError}
            />
          </form>

          <p className="signLink">
            <strong>Pas encore de compte ? </strong><Link to="./signup"> en créer un </Link>
          </p>
        </div>
      </div>
    );
  }
}
