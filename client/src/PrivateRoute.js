import React, { Component } from "react";
import User from "./User";
import { Redirect, Route } from "react-router-dom";
import { Navigbar } from "./Navigbar";

/**
 * Gère la sécuritée des routes
 * si l'utilisateur est connecté (session active)
 * on le redirige vers le composant demandé, 
 * sinon on le redirige vers le composant signin
 */
class PrivateRoute extends Component {

  state = {
    loading: true, // la page est en train de chargé
    isLoggedIn: false // l'utilisateur est connecté (sessiona ctive)
  };

  componentDidMount() {
    //on verifie si l'utilisateur est connecter
    User.getIsLoggedIn().then(islog => {
      this.setState({
        loading: false,
        isLoggedIn: islog
      });
    });
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
