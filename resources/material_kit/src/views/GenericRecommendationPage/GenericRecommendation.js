import React from "react";
import GenericRecommendationPage from "views/GenericRecommendationPage/GenericRecommendationPage.jsx";
import { Link } from "react-router-dom";

/**
 *  Recommendation component.
 */
class GenericRecommendation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      result: []
    };
  }

  componentDidMount() {
    var userToken = localStorage.getItem("token");
    var _this = this;
    console.log(userToken);
    fetch(
      "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/top_memories/",
      {
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "GET",
          "Access-Control-Allow-Headers": "Content-Type",
          "Authorization": "JWT " + userToken
        },
        method: "GET"
      })
      .then(response => response.json())
      .then(function(data) {
        //console.log(data);
        _this.setState({ result: data });
      })

      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });
  }

  render() {
    return (
      <div>
        <GenericRecommendationPage
          result={this.state.result}
        />
      </div>
    );
  }
}

export default GenericRecommendation;
