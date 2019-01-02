import React from "react";

import Dropzone from "react-dropzone";
import styles from "./styles";
import { postService } from "../_services";
import { compose, withProps } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker } from "react-google-maps"

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import InputLabel from '@material-ui/core/InputLabel';
import {Link} from "react-router-dom";
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
import Map from "views/CreateMemoryPage/Map.jsx";

import loginPageStyle from "assets/jss/material-kit-react/views/loginPage.jsx";

import image from "assets/img/bg2.jpg";

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
            format: [],
            decade1: "",
            year1: "",
            month1: "",
            day1: "",
            hour1: "",
            minute1: "",
            decade2: "",
            year2: "",
            month2: "",
            day2: "",
            hour2: "",
            minute2: "",
            location_type: 1,
            location_list: []
        };

        this.onDrop = this.onDrop.bind(this);
        this.handlePost = this.handlePost.bind(this);
    }

    componentWillUnmount() {
        // Make sure to revoke the data uris to avoid memory leaks
        const {files} = this.state;
        for (let i = files.length; i >= 0; i--) {
            const file = files[i];
            URL.revokeObjectURL(file.preview);
        }
    }

  render() {
        const {postText, title, tags, decade1, year1, month1, day1, hour1, minute1, decade2, year2, month2, day2, hour2, minute2} = this.state;
        const {classes, ...rest} = this.props;
        return (
        <div>
        <Header
        color="transparent"
        routes={dashboardRoutes}
        brand="MEMORIST"
        rightLinks={<HeaderLinks/>}
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
                                                            <br/>
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="titleInput"
                                                                placeholder="Title"
                                                                value={title}
                                                                onChange={e => this.setState({title: e.currentTarget.value})}
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="tagsInput">Tags - Divide each tag with a
                                                                comma(,) -</label>
                                                            <br/>
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="tagsInput"
                                                                placeholder="#awesome"
                                                                value={tags}
                                                                onChange={e => this.setState({tags: e.currentTarget.value})}
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="timeInput1">Time</label>
                                                            <br/>
                                                            Decade:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="decade1Input"
                                                                placeholder="2010"
                                                                min="1900" max="2010" step="10"
                                                                value={decade1}
                                                                onChange={e => this.setState({decade1: e.currentTarget.value})}
                                                            />
                                                            Year:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="year1Input"
                                                                min="1900" max="2019"
                                                                placeholder="2019"
                                                                value={year1}
                                                                onChange={e => this.setState({year1: e.currentTarget.value})}
                                                            />
                                                            Month:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="month1Input"
                                                                min="1" max="12"
                                                                placeholder="1"
                                                                value={month1}
                                                                onChange={e => this.setState({month1: e.currentTarget.value})}
                                                            />
                                                            Day:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="day1Input"
                                                                min="1" max="31"
                                                                placeholder="2"
                                                                value={day1}
                                                                onChange={e => this.setState({day1: e.currentTarget.value})}
                                                            />
                                                            < br/>
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="hour1Input"
                                                                min="0" max="23"
                                                                placeholder="18"
                                                                value={hour1}
                                                                onChange={e => this.setState({hour1: e.currentTarget.value})}
                                                            />
                                                            :
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="minute1Input"
                                                                min="0" max="59"
                                                                placeholder="30"
                                                                value={minute1}
                                                                onChange={e => this.setState({minute1: e.currentTarget.value})}
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="timeInput2">End Time (optional)</label>
                                                            <br/>
                                                            Decade:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="decade2Input"
                                                                placeholder="2010"
                                                                min="1900" max="2010" step="10"
                                                                value={decade2}
                                                                onChange={e => this.setState({decade2: e.currentTarget.value})}
                                                            />
                                                            Year:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="year2Input"
                                                                min="1900" max="2019"
                                                                placeholder="2019"
                                                                value={year2}
                                                                onChange={e => this.setState({year2: e.currentTarget.value})}
                                                            />
                                                            Month:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="month2Input"
                                                                min="1" max="12"
                                                                placeholder="1"
                                                                value={month2}
                                                                onChange={e => this.setState({month2: e.currentTarget.value})}
                                                            />
                                                            Day:
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="day2Input"
                                                                min="1" max="31"
                                                                placeholder="2"
                                                                value={day2}
                                                                onChange={e => this.setState({day2: e.currentTarget.value})}
                                                            />
                                                            < br/>
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="hour2Input"
                                                                min="0" max="23"
                                                                placeholder="18"
                                                                value={hour2}
                                                                onChange={e => this.setState({hour2: e.currentTarget.value})}
                                                            />
                                                            :
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="minute2Input"
                                                                min="0" max="59"
                                                                placeholder="30"
                                                                value={minute2}
                                                                onChange={e => this.setState({minute2: e.currentTarget.value})}
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="postTextArea">Post Text Area</label>
                                                            <br/>
                                                            <textarea
                                                                className="form-control"
                                                                rows="3"
                                                                id="postTextArea"
                                                                placeholder="Hello"
                                                                value={postText}
                                                                onChange={e => this.setState({postText: e.currentTarget.value})}
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
                                                <h5>You can click the post area to upload photo or video. Or you can
                                                    drag them in the area...</h5>
                                                <Dropzone id="dropzone" accept="image/*,video/*,audio/*"
                                                          onDrop={this.onDrop} style={styles.dropzoneContainer}>
                                                    <h3 style={{textAlign: "center"}}>{title}</h3>
                                                    <hr style={{borderTop: "1px solid darkgray"}}/>
                                                    {this.renderPost()}
                                                </Dropzone>
                                                <br/>
                                                <br/>
                                                <Map
                                                    google={this.props.google}
                                                    center={{lat: 41.015137, lng: 28.979530}}
                                                    height='300px'
                                                    zoom={15}
                                                    ref={(map) => {this.map = map;}} {...this.props}
                                                />
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
                <Footer/>
            </div>
        );
    }

    renderPost() {
        const {post, files, willSendFiles} = this.state;

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
                                <source src={files[el].preview} type={willSendFiles[el].type}/>
                                Your browser does not support HTML5 video.
                            </video>
                        );
                        break;
                    case "audio":
                        renderQueue.push(
                            <div style={styles.mediaContainer} key={"MediaElement" + elIndex}>
                                <audio controls>
                                    <source src={files[el].preview} type={willSendFiles[el].type}/>
                                    Your browser does not support the audio element.
                                </audio>
                            </div>
                        );
                        break;
                    default:
                        renderQueue.push(
                            <div style={styles.mediaContainer} key={"MediaElement" + elIndex}>
                                <img src={files[el].preview} style={styles.media}/>
                            </div>
                        );
                        break;
                }
            } else {
                renderQueue.push(<p>!!!!------Could not convert this element------!!!!</p>);
            }
        });

        return renderQueue;
    }

    onDrop(addedfiles) {
            const {post, files, format} = this.state;

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
        const {postText} = this.state;

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
        const format = this.state.format;
        const multimedia = this.state.willSendFiles;
        const story = this.state.willSendStories;
        let date_type;
        let date_format;
        let date_string1 = "";
        let date_string2 = "";
        const location_type = 0;
        const PointLocation = {
            location_name: this.map.state.address,
            location_coordinate_latitude: this.map.state.markerPosition.lat,
            location_coordinate_longitude: this.map.state.markerPosition.lng
        };
        const location_list = [PointLocation];
        if (this.state.year1 == "" && this.state.decade1 != "") {
            // Only decade given
            date_string1 = this.state.decade1.toString().concat("s");
            date_format = "d";
        }
        else if (this.state.month1 == "" && this.state.year1 != "") {
            // Only year given
            date_string1 = this.state.year1.toString();
            date_format = "y";
        }
        else if (this.state.day1 == "" && this.state.year1 != "" && this.state.month1 != "") {
            // Only year and month given
            date_string1 = this.state.month1.toString().concat(". month of ").concat(this.state.year1.toString());
            date_format = "ym";
        }
        else if (this.state.year1 != "" && this.state.month1 != "" && this.state.day1 != "" && this.state.hour1 == "") {
            // Only year, month and day given
            date_string1 = this.state.day1.toString().concat(".").concat(this.state.month1.toString()).concat(".").concat(this.state.year1.toString());
            date_format = "ymd";
        }
        else {
            // Full time
            date_string1 = this.state.day1.toString().concat(".").concat(this.state.month1.toString()).concat(".")
                .concat(this.state.year1.toString()).concat(" ")
                .concat(this.state.hour1.toString()).concat(":")
                .concat(this.state.minute1.toString());
            date_format = "full";
        }
        if (this.state.decade2 == "" && this.state.year2 == "" && this.state.month2 == ""
            && this.state.day2 == "" && this.state.hour2 == "" && this.state.minute2 == "") {
            date_type = 0;
        }
        else {
            if (this.state.year2 == "" && this.state.decade2 != "" && date_format == "d") {
                // Only decade given
                date_string2 = this.state.decade2.toString().concat("s");
                date_type = 1;
            }
            else if (this.state.month2 == "" && this.state.year2 != "" && date_format == "y") {
                // Only year given
                date_string2 = this.state.year2.toString();
                date_type = 1;
            }
            else if (this.state.day2 == "" && this.state.year2 != "" && this.state.month2 != "" && date_format == "ym") {
                // Only year and month given
                date_string2 = this.state.month2.toString().concat(". month of ").concat(this.state.year2.toString());
                date_type = 1;
            }
            else if (this.state.year1 != "" && this.state.month1 != "" && this.state.day1 != "" && this.state.hour1 == "" && date_format == "ymd") {
                // Only year, month and day given
                date_string2 = this.state.day2.toString().concat(".").concat(this.state.month2.toString())
                    .concat(".").concat(this.state.year2.toString());
                date_type = 1;
            }
            else {
                // Full time
                date_string2 = this.state.day2.toString().concat(".").concat(this.state.month2.toString()).concat(".")
                    .concat(this.state.year2.toString()).concat(" ")
                    .concat(this.state.hour2.toString()).concat(":")
                    .concat(this.state.minute2.toString());
                date_type = 1;
            }
        }
        // dispatch(postActions.submitPost(willSendFiles, willSendStories, format, title, tagsArray));
        postService.submitPost(multimedia)
            .then(values => {
                console.log("values", values);
                const ids = values.map(el => el.id);
                postService
                    .submitPostCredential(ids, story, format, title, tags, date_type, date_string1, date_string2, date_format, location_type, location_list)
                    .then(res => {
                        console.log(res);
                        window.location.replace("/home-page");
                    })
                    .catch(res => {
                    });
            })
            .catch(res => {
            });
    }


}


export default withStyles(styles)(CreateMemoryPage);

