import React, { Component } from "react";
import User from "./User";
import { Redirect, Route } from "react-router-dom";
import { Navigbar } from "./Navigbar";

class PrivateRoute extends Component {
  state = {
    loading: true,
    isLoggedIn: false
  };

  componentDidMount() {
    this.setState({
      loading: false,
      isLoggedIn: false
    });
    // on verifie si l'utilisateur est connecter, sinon on redirige vers /signin
    // User.getIsLoggedIn().then(islog => {
    //   this.setState({
    //     loading: false,
    //     isLoggedIn: islog
    //   });
    // });
  }

  render() {
    const { component: Component, ...rest } = this.props;
    return (
      <div className="mainDiv">
        <Navigbar islog="true" />
        {/* on redirige vers la bonne route une fois que 
        loading est fixer a false, en fonction de isLoggedIn */}
        <Route
          {...rest}
          render={props =>
            this.state.isLoggedIn ? (
              <Component {...props} />
            ) : this.state.loading ? (
              <div>LOADING</div>
            ) : (
              <Redirect
                to={{
                  pathname: "/signin"
                }}
              />
            )
          }
        />
      </div>
    );
  }
}

export default PrivateRoute;
