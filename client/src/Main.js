import React, { Component } from "react";
import { Switch, Route } from "react-router-dom";

import "./style/global.css";
import PrivateRoute from "./PrivateRoute";
import { Signin } from "./Signin";
import { Signup } from "./Signup";
import { Signout } from "./Signout";
import { Profil } from "./Profil";
import { EditProfil } from "./EditProfil";
import { Chat } from "./Chat";
import { Events } from "./Events";

export class Main extends Component {
  render() {
    return (
      <Switch>
        <PrivateRoute exact path="/" component={Events} />
        <PrivateRoute path="/chat" component={Chat} />
        <PrivateRoute path="/signout" component={Signout} />
        <PrivateRoute path="/profil" component={Profil} />
        <PrivateRoute path="/editProfil" component={EditProfil} />
        <Route path="/signin" component={Signin} />
        <Route path="/signup" component={Signup} />
      </Switch>
    );
  }
}
