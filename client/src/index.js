import React, { Component } from "react";
import { render } from "react-dom";
import { BrowserRouter } from "react-router-dom";

import { Main } from "./Main";

class App extends Component {
  render() {
    return <Main />;
  }
}

render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById("root")
);
