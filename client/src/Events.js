import React, { Component } from "react";
import axios from "axios";
import { Button } from "react-bootstrap";

import Config from "./Config";


export class Events extends Component {
  constructor(props) {
    super(props);
    this.state = {
      label: "",
      events: []
    };
  }

  componentDidMount() {
    // // on recupère des informations des evenements autour
    // axios.get(Config.BASE_URI + "/events",
    //   {
    //     withCredentials: true,
    //     params: { label: this.state.label }
    //   }).then(events => {
    //     this.setState({
    //       events: (events.data._embedded == null) ? [] : events.data._embedded.events
    //     });
    //   });
  }

  handleChange = event => {
    this.setState(
      {
        [event.target.name]: event.target.value
      }
    );
  };

  handleSubmit = async e => {
    e.preventDefault();
    try {
      // on recupère des informations des evenements autour
      axios.get(Config.BASE_URI + "/events",
        {
          withCredentials: true,
          params: { label: this.state.label }
        }).then(events => {
          this.setState({
            events: (events.data._embedded == null) ? [] : events.data._embedded.events
          });
        });
    } catch (error) {
      console.log("ERREUR :", error);
    }
  };

  render() {
    return (
      <div>
        <div className="eventsSearch">
          <form onSubmit={this.handleSubmit}>

            <div className="form__group field">
              <input
                type="text"
                name="label"
                className="form__field"
                value={this.state.label}
                placeholder="mot-clé"
                onChange={this.handleChange}
              />

            </div>
            <br />

            <input
              type="submit"
              className="formSubmit"
              onSubmit={this.handleSubmit}
            />
          </form>
        </div>
        <div className="eventsParent">
          {
            this.state.events.map((event, i) => {
              return (
                <div>
                  <a
                    href={event.eventUrl}
                  >
                    <div>
                      {event.name}
                    </div>
                  </a>
                  <Button variant="dark">
                    Participé
                </Button>
                  <Button variant="success">
                    Chat
                  </Button>
                </div>
              )
            })
          }
        </div>
      </div>
    );
  }
}
