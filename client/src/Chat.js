import React, { Component } from "react";
import io from "socket.io-client";
import axios from "axios";

import "./style/chat.css";
import User from "./User";
import Config from "./Config";
import { Link } from "react-router-dom";

/**
 * Répresente un salon de chat dédié à un evenement. 
 * L'échange de messages est géré via le protocol websocket
 * Les messages sont sauvegardés en bdd.
 */
export class Chat extends Component {
  constructor(props) {
    super(props);
    // identifiant du salon (id de l'event)
    this.toId = new URLSearchParams(props.location.search).get("to");
    this.state = {
      message: "", // message que l'utilisateur écrit
      messages: [] // ensemble des messages du salon
    };

    // création de la connexion 
    this.socket = new WebSocket("ws://localhost:8080/TalkAboutEvents/chat/" + this.toId);

    // evenement reception message
    this.socket.addEventListener("message", async (event) => {
      const message = JSON.parse(event.data);
      const user = await this.getUser(message.from); // on récupère des info sur l'expediteur
      message.from_pseudo = user.pseudo;
      message.from_pdp = user.pdp;
      message.from_id = user.id;
      this.addMessage(message); // ajout du message a la liste des messages
    })
  }

  /**
   * Quand le composant est créer, on recupère l'ensemble des messages en bdd
   */
  componentDidMount() {
    axios.get(Config.BASE_URI + "/messages", { params: { to: this.toId }, withCredentials: true }).then(res => {
      console.log(res)
      this.setState({
        messages: res.data.messages
      });
    });
  }


  /**
   * A chaque nouveau message reçu, on scroll en bas de la page
   */
  componentDidUpdate() {
    // on scroll jusqu'au message le plus recent
    let scroll = document.getElementById("chatMessages");
    scroll.scrollTop = scroll.scrollHeight;
  }

  /**
   * Envoie d'un message
   */
  handleSubmit = e => {
    e.preventDefault();
    const toSend = {
      from: User.getId(),
      to: this.toId,
      message: this.state.message
    };
    this.socket.send(JSON.stringify(toSend));
    // on nettoie le champ message
    this.setState({
      message: ""
    });
  };

  // écriture d'un message, met a jour l'état
  handleChange = e => {
    e.preventDefault();
    this.setState({
      message: e.target.value
    });
  };

  // Ajout d'un message a la liste
  addMessage = message => {
    this.setState({
      messages: [...this.state.messages, message]
    });
  };

  // Recupere des infos sur l'utilisateur d'id id
  getUser = async id => {
    let data = await axios.get(Config.BASE_URI + "/user",
      { params: { id: id }, withCredentials: true }
    );
    return data.data;
  }

  render() {
    return (
      <div className="chatParent">
        <div className="chatMessages" id="chatMessages">
          {/* on parcours l'ensemble des messages */}
          {this.state.messages.map((message, i) => {
            // return different si le message vient de nous ou quelqu'un d'autre
            return parseInt(message.from) === User.getId() ? (
              <div className="chatMe" key={i}>
                <p className="chatMeMessage"> {message.message} </p>
                <img src={message.from_pdp} alt="" className="chatMePicture" />
              </div>
            ) : (
                <div className="chatOther" key={i}>
                  <img src={message.from_pdp} alt="" className="chatOtherPicture" />
                  <p className="chatOtherMessage">
                    <Link
                      to={{
                        pathname: "/profil",
                        search: "?id=" + message.from_id
                      }}
                    >
                      {message.from_pseudo}
                    </Link>
                    {" : " + message.message}
                  </p>
                </div>
              );
          })}
        </div>
        <form onSubmit={this.handleSubmit}>
          <input
            type="text"
            name="message"
            placeholder="Entrer votre message.."
            value={this.state.message}
            onChange={this.handleChange}
          />
        </form>
      </div>
    );
  }
}
