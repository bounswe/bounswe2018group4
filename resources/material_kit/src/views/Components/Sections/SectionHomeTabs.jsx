import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";

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

class SectionTabs extends React.Component {
  static propTypes = {
    memories: PropTypes.oneOfType([PropTypes.object, PropTypes.array])
  };

  constructor(props) {
    super(props);
    this.state = {
      memories: []
    };
  }

  handleLike(id) {
    var userToken = localStorage.getItem("token");
    var _this = this;
    console.log(userToken);
    fetch(
      "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/like_post/".concat(
        id
      ).concat(
        "/"
      ),
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
      })
      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });
  }

  handleDislike(id) {
    var userToken = localStorage.getItem("token");
    var _this = this;
    console.log(userToken);
    fetch(
      "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/dislike/".concat(
        id
      ).concat(
        "/"
      ),
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
      })
      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });
  }

  componentDidMount() {
    var userToken = localStorage.getItem("token");
    var _this = this;
    console.log(userToken);
    fetch("http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/post/list/",
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
        _this.setState({ memories: data });
      })
      .catch(function(error) {
        console.log("There has been a problem with your fetch operation: " + error.message);
      });
  }

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <div id="nav-tabs">
            <GridContainer>
              <GridItem>
                {this.state.memories.map((prop, key) => {
                  return (
                    <div>
                      <h3>
                        <small>{prop.title} by {prop.owner.username}</small>
                      </h3>
                      <CustomTabs
                        headerColor="primary"
                        tabs={[
                          {
                            tabName: "Story",
                            tabContent: (
                              <p className={classes.textCenter}>
                                {prop.texts.map((value, key) => {
                                  return (
                                    <div> {value.text} <br/>
                                      <img
                                        src={"http://ec2-18-234-162-48.compute-1.amazonaws.com:8000" + prop.multimedia[key].multimedia.media}/>
                                    </div>
                                  );
                                })
                                }
                              </p>
                            )
                          },
                          {
                            tabName: "Posted Time",
                            tabContent: (
                              <p className={classes.textCenter}>
                                {prop.posting_time}
                              </p>
                            )
                          },
                          {
                            tabName: "Tags",
                            tabContent: (
                              <div>
                                {prop.tags.map(tag => (
                                  <p className={classes.textCenter}>{tag.tag} <br/></p>
                                ))}
                              </div>
                            )
                          },
                          {
                            tabName: "Like",
                            tabContent: (
                              <div>
                                <Button
                                  onClick={() =>
                                    this.handleLike(prop.id)
                                  }
                                >
                                  Like
                                </Button>
                                <br />
                                <Button
                                  onClick={() =>
                                    this.handleDislike(prop.id)
                                  }
                                  color= "danger"
                                >
                                  Dislike
                                </Button>
                              </div>
                            )
                          }
                        ]}
                      />
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
