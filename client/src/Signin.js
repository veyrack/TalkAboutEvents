import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import { Navigbar } from "./Navigbar";
import User from "./User";

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
      const response = await axios.post("/login", {
        email: this.state.email,
        password: this.state.password
      });
      if (response.status === 200) {
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
      <div className="mainDiv">
        <Navigbar islog="false" />
        <div className="signParent">
          <form onSubmit={this.handleSubmit}>
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
            <br />

            <label>
              Mot de passe : <br />
              <input
                type="password"
                name="password"
                value={this.state.password}
                onChange={this.handleChange}
              />
            </label>
            <br />

            <input
              type="submit"
              className="formSubmit"
              onSubmit={this.handleSubmit}
              disabled={this.state.formGotError}
            />
          </form>

          <p className="signLink">
            Pas encore de compte ? <Link to="./signup"> en créer un </Link>
          </p>
        </div>
      </div>
    );
  }
}
