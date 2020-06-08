import React, { Component } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Button, Image } from "react-bootstrap";
import "./style/events.css"
import Config from "./Config";


export class Events extends Component {
  constructor(props) {
    super(props);
    this.state = {
      keyword: "",
      city: "",
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
          params: { keyword: this.state.keyword, city: this.state.city }
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
      <div className="row justify-content-center">
        <div className="eventsSearch col-md-10 row justify-content-center">
          <form onSubmit={this.handleSubmit} className=" inline-block">

            <div className="form__group field">
              <input
                type="text"
                name="keyword"
                className="form__field"
                value={this.state.label}
                placeholder="mot-clé"
                onChange={this.handleChange}
              />
              <label htmlFor="keyword" className="form__label"><strong>Mot-Clé :</strong></label>
            </div>
            <br />
            <div>
              <input
                type="text"
                name="city"
                className="form__field"
                value={this.state.label}
                placeholder="ville"
                onChange={this.handleChange}
              />
              <label htmlFor="city" className="form__label"><strong>Ville :</strong></label>
            </div>
            <br />

            <input
              type="submit"
              className="formSubmit btn btn-outline-primary col-sm-12"
              onSubmit={this.handleSubmit}
            />
          </form>
        </div>
        <div className="eventsParent col-md-4 row justify-content-center pt-5">
          {
            this.state.events.map((event, i) => {
              return (
                <div className="event w-100">
                  <a
                    href={event.eventUrl == null ? event.url : event.eventUrl}
                  >
                    <div>
                      {event.name}
                    </div>
                  </a>
                  {/* <div>
                    {
                      event.attractions.map((image, i) => {
                        return <Image
                          src={image.url}
                        />
                      })
                    }
                  </div> */}
                  <Button variant="dark">
                    Participé
                  </Button>
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
