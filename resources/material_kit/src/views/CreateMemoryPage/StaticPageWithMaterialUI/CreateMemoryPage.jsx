import React from "react";
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
    // we use this to make the card to appear after the page has been rendered
    this.state = {
      cardAnimaton: "cardHidden",
      title: '',
      story1: '',
      multimedia1: '',
      story2: '',
      multimedia2: '',
      story3: '',
      multimedia3: '',
      tags: '',
      location: '',
      start_decade: '',
      start_year: '',
      start_month: '',
      start_day: '',
      start_hour: '',
      start_minute: '',
      end_decade: '',
      end_year: '',
      end_month: '',
      end_day: '',
      end_hour: '',
      end_minute: '',
      liked_users: ''
    };

  
    this.handleChange = this.handleChange.bind(this);
    this.handleCreate = this.handleCreate.bind(this);

  }

  handleChange(event) {
    if(event.target.id === "tags"){
      this.setState({tags: event.target.value});
    }else if(event.target.id === "location"){
      this.setState({location: event.target.value});
    }else if(event.target.id === "title"){
      this.setState({title: event.target.value});
    }else if(event.target.id === "story1"){
      this.setState({story1: event.target.value});
    }else if(event.target.id === "multimedia1"){
      this.setState({multimedia1: event.target.value});
    }else if(event.target.id === "story2"){
      this.setState({story2: event.target.value});
    }else if(event.target.id === "multimedia2"){
      this.setState({multimedia2: event.target.value});
    }else if(event.target.id === "story3"){
      this.setState({story3: event.target.value});
    }else if(event.target.id === "multimedia3"){
      this.setState({multimedia3: event.target.value});
    }else if(event.target.id === "start_decade"){
      this.setState({start_decade: event.target.value});
    }else if(event.target.id === "start_year"){
      console.log(event.target.value);
      this.setState({start_year: event.target.value});
    }else if(event.target.id === "start_month"){
      this.setState({start_month: event.target.value});
    }else if(event.target.id === "start_day"){
      this.setState({start_day: event.target.value});
    }else if(event.target.id === "start_hour"){
      this.setState({start_hour: event.target.value});
    }else if(event.target.id === "start_minute"){
      this.setState({start_minute: event.target.value});
    }else if(event.target.id === "end_decade"){
      this.setState({end_decade: event.target.value});
    }else if(event.target.id === "end_year"){
      this.setState({end_year: event.target.value});
    }else if(event.target.id === "end_month"){
      this.setState({end_month: event.target.value});
    }else if(event.target.id === "end_day"){
      this.setState({end_day: event.target.value});
    }else if(event.target.id === "end_hour"){
      this.setState({end_hour: event.target.value});
    }else if(event.target.id === "end_minute"){
      this.setState({end_minute: event.target.value});
    }else if(event.target.id === "liked_users"){
      this.setState({liked_users: event.target.value});
    }
  }


  handleCreate(event) {
    event.preventDefault();
    var tagsArray = this.state.tags.split(',');
    var data = {
      title: this.state.title,
      tags: {tags: tagsArray},
      location: this.state.location,
      format: {"format": ['T', 'M', 'T', 'M', 'T', 'M']},
      story: {"story": ["dfghj","this.state.story2", "this.state.story3"]},
      multimedia: [this.state.multimedia1, this.state.multimedia2, this.state.multimedia3],
      start_decade: this.state.start_decade,
      start_year: this.state.start_year,
      start_month: this.state.start_month,
      start_day: this.state.start_day,
      start_hour: this.state.start_hour,
      start_minute: this.state.start_minute,
      end_decade: this.state.end_decade,
      end_year: this.state.end_year,
      end_month: this.state.end_month,
      end_day: this.state.end_day,
      end_hour: this.state.end_hour,
      end_minute: this.state.end_minute,
    }
    var userToken = getCookie("token");
    fetch('http://18.234.162.48:8000/post/create/',
    {
      mode: 'cors',
      headers: {
        'Content-Type' : 'multipart/form-data',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Authorization' : 'JWT ' + userToken,
      },
        method: 'POST',
        body: JSON.stringify(data),
      })
    .then((fetchRes) => fetchRes.json())
        .then(function(res) {
          const response = {
            responseBody: res
          };
        })
      .catch((error) => {
        console.error(error);
      })
  };

  componentDidMount() {
    // we add a hidden class to the card and after 700 ms we delete it and the transition appears
    setTimeout(
      function() {
        this.setState({ cardAnimaton: "" });
      }.bind(this),
      700
    );
  }

  render() {
    const { classes, ...rest } = this.props;
    return (
      <div>
        <Link to={"/home-page"} className={classes.link}>
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
        </Link>
        <div
          className={classes.pageHeader}
          style={{
            backgroundImage: "url(" + image + ")",
            backgroundSize: "cover",
            backgroundPosition: "top center"
          }}
        >
          <div className={classes.container}>
            <GridContainer justify="center">
              <GridItem xs={12}>
                <Card className={classes[this.state.cardAnimaton]}>
                  <form className={classes.form} onSubmit={this.handleCreate}>
                    <CardHeader color="primary" className={classes.cardHeader}>
                      <h4>Create Memory</h4>
                      
                    </CardHeader>
                    
                    <CardBody>

                      <CustomInput
                        labelText="Title"
                        id="title"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          rows: "4"
                        }}
                      />
                      <CustomInput
                        labelText="Story 1"
                        id="story1"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          multiline: true,
                          rows: 3,
                          onChange: this.handleChange,
                          type: "text"
                        }}
                      />
                      <Button
                         label='Multimedia 1'>
                         <input type="file" accept= "audio/*,image/*,video/*" />
                      </Button>
                      <CustomInput
                        labelText="Story2"
                        id="story2"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          multiline: true,
                          rows: 3,
                          onChange: this.handleChange,
                          type: "text"
                        }}
                      />
                      <Button
                         label='Multimedia 2'>
                         <input type="file" accept= "audio/*,image/*,video/*" />
                      </Button>
                      <CustomInput
                        labelText="Story3"
                        id="story3"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          multiline: true,
                          rows: 3,
                          onChange: this.handleChange,
                          type: "text"
                        }}
                      />
                      <Button
                         label='Multimedia 3'>
                         <input type="file" accept= "audio/*,image/*,video/*" />
                      </Button>
                      <CustomInput
                        labelText="Tags"
                        id="tags"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                        }}
                      />
                      <CustomInput
                        labelText="Location"
                        id="location"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                        }}
                      />
                      <CustomInput
                        labelText="Start decade"
                        id="start_decade"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                        }}
                      />
                      <CustomInput
                        labelText="Start year"
                        id="start_year"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="Start month"
                        id="start_month"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="Start day"
                        id="start_day"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="Start hour"
                        id="start_hour"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="Start minute"
                        id="start_minute"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />


                      <CustomInput
                        labelText="End decade"
                        id="end_decade"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                        }}
                      />
                      <CustomInput
                        labelText="End year"
                        id="end_year"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="End month"
                        id="end_month"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="End day"
                        id="end_day"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="End hour"
                        id="end_hour"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                      <CustomInput
                        labelText="End minute"
                        id="end_minute"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "number",
                        }}
                      />
                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <Button simple color="primary" size="lg" onClick={this.handleCreate}>
                        Share now!
                      </Button>
                      <Link to={"/landing-page"} className={classes.link}>
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
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : null;
}

export default withStyles(loginPageStyle)(CreateMemoryPage);
