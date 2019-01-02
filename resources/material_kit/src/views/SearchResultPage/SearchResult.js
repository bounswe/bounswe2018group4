import React from "react";
import SearchResultPage from "views/SearchResultPage/SearchResultPage.jsx";
import { Link } from "react-router-dom";

/**
 *  Search component.
 */
class SearchResult extends React.Component {
  _isMounted = false;
  constructor(props) {
    super(props);
    this.state = {
      searchUserResult: []
    };
  }

  componentDidMount() {
    this._isMounted = true;
    var userToken = localStorage.getItem("token");
    var _this = this;
    var searchUser
    if (this._isMounted) {
      searchUser = this.props.location.state.searchUser;
    }
    console.log(userToken);
    fetch(
      "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/auth/user_search/"
        .concat(searchUser)
        .concat("/"),
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
        _this.setState({ searchUserResult: data });
      })

      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });
  }

  render() {
    return (
      <div>
        <SearchResultPage
          searchUser={this.props.location.state.searchUser}
          searchUserResult={this.state.searchUserResult}
        />
      </div>
    );
  }
}

export default SearchResult;
