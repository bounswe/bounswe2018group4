import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import {Link} from "react-router-dom";
// @material-ui/icons
import Face from "@material-ui/icons/Face";
import Chat from "@material-ui/icons/Chat";
import Build from "@material-ui/icons/Build";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import CustomTabs from "components/CustomTabs/CustomTabs.jsx";
import tabsStyle from "assets/jss/material-kit-react/views/componentsSections/tabsStyle.jsx";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import CallMade from "@material-ui/core/SvgIcon/SvgIcon";
import Divider from "@material-ui/core/Divider";
import Button from "components/CustomButtons/Button.jsx";
import PropTypes from "prop-types";
import $ from "jquery";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";
import Typography from '@material-ui/core/Typography';

class SectionTabs extends React.Component {
  _isMounted = false;
  static propTypes = {
    searchUser: PropTypes.string,
    searchUserResult: PropTypes.oneOfType([PropTypes.object, PropTypes.array])
  };

  constructor(props) {
    super(props);
    this.state = {
      searchUserResult: []
    };

    //this.handleSearchChange = this.handleSearchChange.bind(this);
  }

  //handleSearchChange(e) {
  //  this.setState({
  //    searchUser: e.target.value
  //  });
  //  console.log(this.state.searchUser);
  // }

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
    console.log(this.props.location);
    const { classes } = this.props;
    return (
        <div className={classes.section}>
          <div className={classes.container}>
            <div id="nav-tabs">
              <GridContainer>
                <GridItem>
                  {this.state.searchUserResult.map((prop, key) => {
                    return (
                        <div>
                          <CustomTabs
                              headerColor="primary"
                              tabs={[
                                {
                                  tabName: "User",
                                  tabContent: (
                                      <Link to={"/follow/".concat(prop.id).concat("/")} className={classes.link}>
                                          <Button simple color="primary" size="lg">
                                              {prop.username}
                                          </Button>
                                      </Link>
                                  )
                                }
                              ]}
                          />
                          <br />
                          <br />
                        </div>
                    );
                  })}
                </GridItem>
              </GridContainer>
            </div>
          </div>
        </div>
    );
  }
}

export default withStyles(tabsStyle)(SectionTabs);