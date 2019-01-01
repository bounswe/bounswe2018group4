import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import SectionSearchTabs from "../Components/Sections/SectionSearchTabs.jsx";

// @material-ui/icons

// core components
import Header from "components/Header/Header.jsx";
import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import Button from "components/CustomButtons/Button.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import HeaderLinks from "components/Header/StaticHeaderLinks.jsx";
import Parallax from "components/Parallax/Parallax.jsx";
import PropTypes from 'prop-types';
import Divider from '@material-ui/core/Divider';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import Paper from '@material-ui/core/Paper';
import CallMade from "@material-ui/icons/CallMade";
import Search from '@material-ui/icons/Search';
import InputAdornment from '@material-ui/core/InputAdornment';
import TextField from '@material-ui/core/TextField';

import landingPageStyle from "assets/jss/material-kit-react/views/landingPage.jsx";

// Sections for this page
import Icon from "@material-ui/core/Icon";
import InputLabel from "@material-ui/core/InputLabel";
import Input from "@material-ui/core/Input";
import FormControl from "@material-ui/core/FormControl";
import Typography from "@material-ui/core/Typography";

const dashboardRoutes = [];

const styles = {

  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none"
  }
};

class SearchResultPage extends React.Component {
  _isMounted = false;
  static propTypes = {
    searchUser: PropTypes.string,
    searchUserResult: PropTypes.oneOfType([PropTypes.object, PropTypes.array]),
  }

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
    console.log(userToken);
    fetch(
      "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/auth/user_search/"
        .concat(this.props.searchUser)
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
        console.log(data);
        _this.setState({ searchUserResult: data });
      })

      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });

  }

  render() {
    const { classes, ...rest } = this.props;
    console.log(this.props.searchUser);
    return (
      <div>
        <Header
          color="transparent"
          routes={dashboardRoutes}
          brand="MEMORIST"
          rightLinks={<HeaderLinks />}
          fixed
          changeColorOnScroll={{
            height: 400,
            color: "white"
          }}
          {...rest}
        />
        <Parallax filter image={require("assets/img/bg2.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={6}>
                <h1 className={classes.title}>Your Story Starts With Us.</h1>

                <br />
                <Button
                  color="danger"
                  size="lg"
                  href="https://github.com/bounswe/bounswe2018group4"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  Go to Github
                </Button>
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>
        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <br />
            <br />
            <Typography variant="display1" gutterBottom>
              Usernames matching with &quot;{this.props.searchUser}&quot;
            </Typography>
            <SectionSearchTabs searchUser={this.props.searchUser} searchUserResult={this.state.searchUserResult} />
          </div>
        </div>
        <Footer />
      </div>
    );
  }
}

export default withStyles(landingPageStyle)(SearchResultPage);
