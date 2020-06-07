import React, { Component } from "react";
import io from "socket.io-client";
import axios from "axios";

import "./style/chat.css";
import User from "./User";
import Config from "./Config";

export class Chat extends Component {
  constructor(props) {
    super(props);
    this.toId = new URLSearchParams(props.location.search).get("to");
    // // room
    // this.room =
    //   User.getId() < this.toId
    //     ? User.getId() + ":" + this.toId
    //     : this.toId + ":" + User.getId();
    // // state
    this.state = {
      message: "",
      messages: []
    };

    // création de la connexion 
    this.socket = new WebSocket("ws://localhost:8080/TalkAboutEvents/chat/" + this.toId);

    this.socket.addEventListener("message", async (event) => {
      const message = JSON.parse(event.data);
      this.addMessage(message);
    })

    // io
    // this.socket = io("localhost:8080/TalkAboutEvents/chat");
    // this.socket.emit("suscribe", this.room);
    // this.socket.on("message", message => {
    //   this.addMessage(message);
    // });
  }

  componentDidMount() {
    // axios.get("/messages?user=" + this.toId).then(res => {
    //   this.setState({
    //     messages: res.data
    //   });
    // });
  }

  componentDidUpdate() {
    // on scroll jusqu'au message le plus recent
    // let scroll = document.getElementById("chatMessages");
    // scroll.scrollTop = scroll.scrollHeight;
  }

  // send the message to the room
  handleSubmit = e => {
    e.preventDefault();
    const toSend = {
      from: User.getId(),
      to: this.toId,
      message: this.state.message
    };
    this.socket.send(JSON.stringify(toSend));
    // this.socket.emit("message", {
    //   room: this.room,
    //   from: User.getId(),
    //   fromPseudo: User.getPseudo(),
    //   to: this.toId,
    //   message: this.state.message
    // });
    this.setState({
      message: ""
    });
  };

  handleChange = e => {
    e.preventDefault();
    this.setState({
      message: e.target.value
    });
  };

  addMessage = message => {
    this.setState({
      messages: [...this.state.messages, message]
    });
  };

  render() {
    return (
      <div className="chatParent">
        <div className="chatMessages" id="chatMessages">
          {this.state.messages.map((message, i) => {
            return parseInt(message.from) === User.getId() ? (
              <div className="chatMe" key={i}>
                <p className="chatMeMessage"> {message.message} </p>
                {/* <img src={message.picture} alt="" className="chatMePicture" /> */}
              </div>
            ) : (
                <div className="chatOther" key={i}>
                  {/* <img
                    src={message.picture}
                    alt=""
                    className="chatOtherPicture"
                  /> */}
                  <p className="chatOtherMessage"> {message.message} </p>{" "}
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
