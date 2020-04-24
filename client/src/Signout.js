import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import axios from "axios";

import User from "./User";

export class Signout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true
    };
  }

  componentDidMount() {
    User.setIsLoggedIn(false);
    //axios.get("/logout").then(() => {
    this.setState({
      loading: false
    });
    //});
  }

  render() {
    return this.state.loading ? (
      <div>LOADING</div>
    ) : (
        <Redirect to={{ pathname: "/signin" }} />
      );  
  }
}
