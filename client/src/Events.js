import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export class Events extends Component {
  constructor(props) {
    super(props);
    this.state = {
      events: []
    };
  }

  componentDidMount() {
    // on recupère des informations des evenements autour
    // axios.get("/events").then(events => {
    //   this.setState({
    //     events: events.data
    //   });
    // });
  }

  render() {
    return (
      <div className="eventsParent">
        <p>Events</p>
      </div>
    );
  }
}
