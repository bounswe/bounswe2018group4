import React from "react";

import Dropzone from "react-dropzone";
import withStyles from "@material-ui/core/styles/withStyles";
import styles from "./styles";
import { postService } from "../_services";

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

    return (
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
        <button type="button" className="btn btn-primary" onClick={this.handlePost}>
          Post it
        </button>
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

