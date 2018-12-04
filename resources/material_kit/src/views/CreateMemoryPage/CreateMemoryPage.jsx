import React from "react";

import Dropzone from "react-dropzone";
import styles from "./styles";
import { postService } from "../_services";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import InputLabel from '@material-ui/core/InputLabel';
import { Link } from "react-router-dom";
//import People from "@material-ui/icons/People";
// core components
import MenuItem from '@material-ui/core/MenuItem';
import Footer from "components/Footer/Footer.jsx";
import Select from '@material-ui/core/Select';
import Input from '@material-ui/core/Input';
import Header from "components/Header/Header.jsx";
import FormControl from "@material-ui/core/FormControl";
//import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import HeaderLinks from "components/Header/StaticHeaderLinks.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Typography from '@material-ui/core/Typography';

import loginPageStyle from "assets/jss/material-kit-react/views/loginPage.jsx";

import image from "assets/img/bg7.jpg";

const dashboardRoutes = [];

class CreateMemoryPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      postText: "",
      files: [],
      post: [],

      title: "",
      tags: "",
      willSendFiles: [],
      willSendStories: [],
      format: []
    };

    this.onDrop = this.onDrop.bind(this);
    this.handlePost = this.handlePost.bind(this);
  }

  componentWillUnmount() {
    // Make sure to revoke the data uris to avoid memory leaks
    const { files } = this.state;
    for (let i = files.length; i >= 0; i--) {
      const file = files[i];
      URL.revokeObjectURL(file.preview);
    }
  }

  render() {
    const { postText, title, tags } = this.state;
    const { classes, ...rest } = this.props;
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
        <div
          className={classes.pageHeader}
          style={{
            backgroundImage: "url(" + image + ")",
            backgroundSize: "cover",
            backgroundPosition: "top center"
          }}
        >
        <div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        </div>
          <div className={classes.container}>
            <GridContainer justify="center">
              <GridItem xs={10}>
                <Card className={classes[this.state.cardAnimaton]}>
                  <form className={classes.form} onSubmit={this.handlePost}>
                    <CardHeader color="primary" className={classes.cardHeader}>
                      <h4>Create Memory</h4>
                      
                    </CardHeader>
                    
                    <CardBody>
                    <div className="container">
                      <div className="panel panel-default">
                        <div className="panel-body">
                          <div className="form-group">
                            <label htmlFor="titleInput">Title</label>
                            <input
                              type="text"
                              className="form-control"
                              id="titleInput"
                              placeholder="Title"
                              value={title}
                              onChange={e => this.setState({ title: e.currentTarget.value })}
                            />
                          </div>
                          <div className="form-group">
                            <label htmlFor="tagsInput">Tags</label>
                            <h5>Divide each tag with a comma(,)</h5>
                            <input
                              type="text"
                              className="form-control"
                              id="tagsInput"
                              placeholder="#awesome"
                              value={tags}
                              onChange={e => this.setState({ tags: e.currentTarget.value })}
                            />
                          </div>
                          <div className="form-group">
                            <label htmlFor="postTextArea">Post Text Area</label>
                            <textarea
                              className="form-control"
                              rows="3"
                              id="postTextArea"
                              placeholder="Hello"
                              value={postText}
                              onChange={e => this.setState({ postText: e.currentTarget.value })}
                            />
                            <button
                              type="button"
                              className="btn btn-primary"
                              style={styles.addToPostButton}
                              onClick={() => this.addTextToPost()}
                            >
                              Add to post
                            </button>
                          </div>
                        </div>
                      </div>
                      <h5>You can click the post area to upload photo or video. Or you can drag them in the area...</h5>
                      <Dropzone id="dropzone" accept="image/*,video/*,audio/*" onDrop={this.onDrop} style={styles.dropzoneContainer}>
                        <h3 style={{ textAlign: "center" }}>{title}</h3>
                        <hr style={{ borderTop: "1px solid darkgray" }} />
                        {this.renderPost()}
                      </Dropzone>
                    </div>
                      
                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <Button simple color="primary" size="lg" onClick={this.handlePost}>
                        Share now!
                      </Button>
                      <Link to={"/home-page"} className={classes.link}>
                        <Button simple color="primary" size="lg">
                          Cancel
                        </Button>
                      </Link>
                    </CardFooter>
                  </form>
                </Card>
              </GridItem>
            </GridContainer>
          </div>
        
        </div>
        <Footer />
      </div>
    );
  }

  renderPost() {
    const { post, files, willSendFiles } = this.state;

    let renderQueue = [];
    post.forEach((el, elIndex) => {
      if (typeof el === "string") {
        // It is a text
        renderQueue.push(
          <h4 key={"TextElement" + elIndex} style={styles.textElement}>
            {el}
          </h4>
        );
      } else if (typeof el === "number") {
        // It is a media

        switch (willSendFiles[el].type.split("/")[0]) {
          case "video":
            renderQueue.push(
              <video style={styles.mediaContainer} controls key={"MediaElement" + elIndex}>
                <source src={files[el].preview} type={willSendFiles[el].type} />
                Your browser does not support HTML5 video.
              </video>
            );
            break;
          case "audio":
            renderQueue.push(
              <div style={styles.mediaContainer} key={"MediaElement" + elIndex}>
                <audio controls>
                  <source src={files[el].preview} type={willSendFiles[el].type} />
                  Your browser does not support the audio element.
                </audio>
              </div>
            );
            break;
          default:
            renderQueue.push(
              <div style={styles.mediaContainer} key={"MediaElement" + elIndex}>
                <img src={files[el].preview} style={styles.media} />
              </div>
            );
            break;
        }
      } else {
        // WTF IS THIS ??!!
        renderQueue.push(<p>!!!!------Could not convert this element------!!!!</p>);
      }
    });

    return renderQueue;
  }

  onDrop(addedfiles) {
    const { post, files, format } = this.state;

    let filesLastIndex = files.length - 1;
    let newPost = post;

    addedfiles.forEach(() => newPost.push(++filesLastIndex));

    let formatArray = [];
    addedfiles.forEach(el => {
      switch (el.type.split("/")[0]) {
        case "video":
          formatArray.push("V");
          break;
        case "audio":
          formatArray.push("A");
          break;
        default:
          formatArray.push("I");
          break;
      }
    });

    this.setState(prevState => ({
      files: [
        ...prevState.files,
        ...addedfiles.map(file => ({
          ...file,
          preview: URL.createObjectURL(file)
        }))
      ],
      post: newPost,
      willSendFiles: [...prevState.willSendFiles, ...addedfiles],
      format: [...prevState.format, ...formatArray]
    }));
  }

  addTextToPost() {
    const { postText } = this.state;

    this.setState(prevState => ({
      post: [...prevState.post, postText],
      postText: "",
      willSendStories: [...prevState.willSendStories, postText],
      format: [...prevState.format, "T"]
    }));
  }

  handlePost() {
    const tagsArray = this.state.tags.split(",");
    const title = this.state.title;
    const tags = tagsArray;
    const format =  this.state.format;
    const multimedia = this.state.willSendFiles;
    const story = this.state.willSendStories;
    // dispatch(postActions.submitPost(willSendFiles, willSendStories, format, title, tagsArray));
    postService.submitPost(multimedia)
      .then(values => {
        console.log("values", values);
        const ids = values.map(el => el.id);
        postService
        .submitPostCredential(ids, story, format, title, tags)
        .then(res => {
          console.log(res);
        })
        .catch(res => {
        });
      })
      .catch(res => {
      });
  }
    
  
}


export default withStyles(styles)(CreateMemoryPage);

