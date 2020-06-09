import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Button, Image } from "react-bootstrap";
import "./style/events.css"
import Config from "./Config";

/**
 * Composant des evenements, permet la recherche d'evenements
 */
export class Events extends Component {
  constructor(props) {
    super(props);
    this.state = {
      keyword: "", // recherche par mot clé
      city: "", // recherche par villes
      events: [] // evenements trouvés
    };
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
      // on recupère des informations des evenements selon les filtres appliqués
      axios.get(Config.BASE_URI + "/events",
        {
          withCredentials: true,
          params: { keyword: this.state.keyword, city: this.state.city }
        }).then(async events => {
          // on récupère les evenements 
          events = (events.data._embedded == null) ? [] : events.data._embedded.events;
          // Pour chaque evenement, on regarde si l'utilisateur y participe ou pas et le stocke dans l'evenement
          events = await Promise.all(events.map(async (event) => {
            let resp = await axios.get(Config.BASE_URI + "/participation",
              {
                withCredentials: true,
                params: {
                  eventId: event.id
                }
              }
            );
            event.participate = resp.data;
            return event
          }));
          // on met a jour la liste d'evenements trouvés
          this.setState({
            events: events
          });
        });
    } catch (error) {
      console.log("ERREUR :", error);
    }
  };

  // indique que l'utilisateur participe à un evenement 
  handleParticipation = async (event) => {
    await axios.post(Config.BASE_URI + "/participation",
      {},
      {
        withCredentials: true,
        params: { idEvent: event.id }
      }
    );
    event.participate = true;
    this.forceUpdate(); // force la mise a jour du composant 
  }

  // indique que l'utilisateur ne participe plus a un evenement
  handleUnparticipation = async (event) => {
    await axios.delete(Config.BASE_URI + "/participation",
      {
        withCredentials: true,
        params: { idEvent: event.id }
      }
    );
    event.participate = false;
    this.forceUpdate(); // force la mise a jour du composant
  }

  render() {
    return (
      <div className="row justify-content-center">
        <div className="eventsSearch col-md-10 row justify-content-center recherche">
          <form onSubmit={this.handleSubmit} className=" inline-block pt-2">

            {/* mot clé */}
            <h5 className="font-weight-bold">Recherche d'évènements</h5>
            <div className="form__group field">
              <input
                type="text"
                name="keyword"
                className="form__field_event"
                value={this.state.label}
                placeholder="mot-clé"
                onChange={this.handleChange}
              />
              <label htmlFor="keyword" className="form__label"><strong>Mot-Clé :</strong></label>
            </div>

            {/* ville */}
            <br />
            <div className="form__group field">
              <input
                type="text"
                name="city"
                className="form__field_event"
                value={this.state.label}
                placeholder="ville"
                onChange={this.handleChange}
              />
              <label htmlFor="city" className="form__label"><strong>Ville :</strong></label>
            </div>
            <br />

            {/* envoie */}
            <input
              type="submit"
              className="formSubmit btn btn-outline-primary col-sm-12"
              onSubmit={this.handleSubmit}
            />
          </form>
        </div>

        {/* Affichage des evenements trouvés */}
        <div className="eventsParent col-md-4 row justify-content-center pt-5">
          {
            this.state.events.map((event, i) => {
              return (
                <div className="event w-100" key={i}>
                  <a
                    href={event.eventUrl == null ? event.url : event.eventUrl}
                  >
                    <div className="printevent ">
                      {event.name}
                    </div>
                  </a>
                  <br></br>
                  {/* Affichage du bouton de participation adequat */}
                  {
                    event.participate ?
                      (
                        <Button variant="dark" onClick={() => this.handleUnparticipation(event)}>
                          Ne plus participer
                        </Button>
                      ) : (
                        <Button variant="dark" onClick={() => this.handleParticipation(event)}>
                          Participer
                        </Button>
                      )
                  }
                  {/* Affichage du bouton de chat */}
                  <Link
                    to={{
                      pathname: "/chat",
                      search: "?to=" + event.id
                    }}
                    className="link pl-2"
                    key={i}
                  >
                    <Button>
                      Chat
                    </Button>
                  </Link>
                </div>
              )
            })
          }
        </div>
      </div>
    );
  }
}
