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
import CustomInput from "components/CustomInput/CustomInput.jsx";
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";
import Typography from '@material-ui/core/Typography';
import {Link} from "react-router-dom";

class SectionTabs extends React.Component {
  _isMounted = false;
  static propTypes = {
    result: PropTypes.oneOfType([PropTypes.object, PropTypes.array])
  };

  constructor(props) {
    super(props);
    this.state = {
      result: []
    };
  }

  componentDidMount() {
    this._isMounted = true;
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
        console.log(data);
        _this.setState({ result: data });
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
                {this.state.result.map((prop, key) => {
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
                                tabName: "Mentioned Time",
                                tabContent: (
                                    <div>
                                      <p className={classes.textCenter}>
                                        {prop.mentioned_time.length > 0 &&
                                        <div>
                                          {prop.mentioned_time[0].date_string1}
                                        </div>
                                        }
                                      </p>
                                      <p className={classes.textCenter}>
                                        {((prop.mentioned_time.length > 0) && (prop.mentioned_time[0].date_string2 !== "")) &&
                                        <div>
                                          until {prop.mentioned_time[0].date_string2}
                                        </div>
                                        }
                                      </p>
                                    </div>
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
                                      <p className={classes.textCenter}>
                                        {prop.numlikes} like(s) <br />
                                        <Button
                                            onClick={() =>
                                                this.handleLike(prop.id)
                                            }
                                        >
                                          Like
                                        </Button>
                                        <Button
                                            onClick={() =>
                                                this.handleDislike(prop.id)
                                            }
                                            color= "danger"
                                        >
                                          Unlike
                                        </Button>
                                      </p>
                                    </div>
                                )
                              },
                              {
                                tabName: "Comment",
                                tabContent: (
                                    <div>
                                      <CustomInput
                                          id="comment"
                                          onChange={this.handleCommentChange}
                                          formControlProps={{
                                            fullWidth: true
                                          }}
                                          inputProps={{
                                            onChange: this.handleCommentChange,
                                            type: "text"

                                          }}
                                      />
                                      <Button simple color="primary" size="lg" onClick={() => this.handleComment(prop.id)}>
                                        Comment
                                      </Button>
                                      <Typography variant="display1" gutterBottom>
                                        Comments
                                      </Typography>
                                      {prop.comments.map((value, key) => {
                                        return (
                                            <div>
                                              <b>{value.owner.username}:</b> {value.comment}
                                            </div>
                                        );
                                      })}
                                    </div>
                                )
                              },
                              {
                                tabName: "Annotation",
                                tabContent: (
                                    <div>
                                      <CustomInput
                                          id="start"
                                          onChange={this.handleStartChange}
                                          inputProps={{
                                            onChange: this.handleStartChange,
                                            type: "text",
                                            placeholder: "Start index"
                                          }}
                                      />
                                      &nbsp; &nbsp;
                                      <CustomInput
                                          id="end"
                                          onChange={this.handleEndChange}
                                          inputProps={{
                                            onChange: this.handleEndChange,
                                            type: "text",
                                            placeholder: "End index"
                                          }}
                                      />
                                      &nbsp; &nbsp;
                                      <CustomInput
                                          id="annotation"
                                          onChange={this.handleAnnotationChange}
                                          inputProps={{
                                            onChange: this.handleAnnotationChange,
                                            type: "text",
                                            placeholder: "Annotation"
                                          }}
                                      />
                                      &nbsp;
                                      <Button default color="primary" size="lg" onClick={() => this.handleAnnotation(prop.id)}>
                                        Annotate
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
