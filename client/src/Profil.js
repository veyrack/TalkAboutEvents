import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./style/profil.css";
import User from "./User";
import Config from "./Config";
import { Button } from "react-bootstrap";

/**
 * Composant profil, affiche le profil d'un utilisateur donné, 
 * affiche un bouton de modification de profil si la page 
 * profil est celle de l'utilsiateur courant
 */
export class Profil extends Component {
  constructor(props) {
    super(props);
    // id du profil a afficher
    this.id = parseInt(
      new URLSearchParams(this.props.location.search).get("id"),
      10
    );
    this.state = {
      participations: []
    }
  }

  componentDidMount() {
    // on recupère l'user concerné
    axios.get(Config.BASE_URI + "/user", { params: { id: this.id } }).then(resp => {
      this.setState({
        ...resp.data
      });
    });

    // on recupère les participations au evenement s
    try {
      axios.get(Config.BASE_URI + "/participation",
        {
          withCredentials: true
        }).then(async resp => {
          // on récupère les evenements 
          let participations = resp.data.participations;
          // pour chaque participation on recupère les infos de l'event 
          participations = await Promise.all(participations.map(async (participation) => {
            let resp = await axios.get(Config.BASE_URI + "/events",
              {
                withCredentials: true,
                params: {
                  eventId: participation.idevent
                }
              }
            );
            participation.event = resp.data;
            return participation
          }));
          // on met a jour la liste d'evenements trouvés
          this.setState({
            participations: participations
          });
        });
    } catch (error) {
      console.log("ERREUR :", error);
    }
  }

  // indique que l'utilisateur ne participe plus a un evenement
  handleUnparticipation = async (event) => {
    await axios.delete(Config.BASE_URI + "/participation",
      {
        withCredentials: true,
        params: { idEvent: event.id }
      }
    );
    let participations = this.state.participations.filter(participation => {
      return !(participation.event == event)
    })
    this.setState({
      participations: participations
    })
  }

  render() {
    return (
      <div className="profilParent">
        {this.state == null ? (
          <p> loading ...</p>
        ) : (
            <div>
              <div className="profilTop">
                {
                  (this.state.pdp == null) ?
                    <img src={process.env.PUBLIC_URL + "/default.png"} alt="" /> :
                    <img src={this.state.pdp} alt="" />
                }
                <div className="profilTopRight">
                  <h1 className="profilPseudo"> {this.state.pseudo} </h1>
                  <label className="profilDescription">
                    {this.state.bio}
                  </label>
                  {this.id === User.getId() ? (
                    <Link to="/editProfil" className="link ml-auto">
                      Paramètres
                    </Link>
                  ) : (
                      <div />
                    )}
                </div>
              </div>
              <br/>
              <h2>Participations aux évènements : </h2>
              {/* participations */}
              <div className="participation">
                {
                  this.state.participations.map(
                    (participation, i) => {
                      return participation.event.fault == null ? // si l'appel a l'api coté serveur a fonctionner
                        // on affiche l'evenement 
                        (
                          <div className="event w-100 pl-1" key={i}>
                            <a
                              href={participation.event.eventUrl == null ? participation.event.url : participation.event.eventUrl}
                            >
                              <div className="printevent ">
                                {participation.event.name}
                              </div>
                            </a>
                            <br></br>
                            {/* Affichage du bouton de participation adequat */}
                            {
                              User.getId() === this.id ?
                                (
                                  <Button variant="dark" onClick={() => this.handleUnparticipation(participation.event)}>
                                    Ne plus participer
                                  </Button>
                                ) : (
                                  <p></p>
                                )
                            }
                            {/* Affichage du bouton de chat */}
                            <Link
                              to={{
                                pathname: "/chat",
                                search: "?to=" + participation.event.id
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
                        :
                        <div key={i}/> // l'appel a l'api a echoué
                    }
                  )
                }
              </div>

            </div>
          )}
      </div>
    );
  }
}
