import React from "react";
import $ from 'jquery';
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";
import { Link } from "react-router-dom";
// @material-ui/icons
import Footer from "components/Footer/Footer.jsx";
import Email from "@material-ui/icons/Email";
//import People from "@material-ui/icons/People";
// core components
import Header from "components/Header/Header.jsx";
//import HeaderLinks from "components/Header/HeaderLinks.jsx";
//import Footer from "components/Footer/Footer.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import Button from "components/CustomButtons/Button.jsx";
import Card from "components/Card/Card.jsx";
import CardBody from "components/Card/CardBody.jsx";
import CardHeader from "components/Card/CardHeader.jsx";
import CardFooter from "components/Card/CardFooter.jsx";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import Typography from '@material-ui/core/Typography';

import loginPageStyle from "assets/jss/material-kit-react/views/loginPage.jsx";

import image from "assets/img/bg2.jpg";


class LoginPage extends React.Component {
  constructor(props) {
    super(props);
    // we use this to make the card to appear after the page has been rendered
    this.state = {
      cardAnimaton: "cardHidden",
      username : '',
      password : ''
    };

  
    this.handleChange = this.handleChange.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

  }

  handleChange(event) {
    if(event.target.id === "username"){
      this.setState({username: event.target.value});
    } else {
      this.setState({password: event.target.value});
    }
  }


  handleLogin(event) {
    var body = {
      username: this.state.username,
      password: this.state.password
    }
    $.ajax({
      url: "http://18.234.162.48:8000/auth/login/",
      data: JSON.stringify(body),
      type: "POST",
      crossDomain : true,
      headers: {
        "Content-Type" : "application/json",
        "Accept" : "application/json"
      },
      beforeSend: () => {
        console.log();
      },
      success: (res) => {
        var token = res.token;
        console.log("SUCCESS! Token: " + token);
        setCookie("token", token);
        localStorage.setItem('token', token);
        window.location.replace("/home-page");
      },
      error: (res, err) => {
        console.log(body);
      
        console.log("ERR " + res);
      }
    });
    event.preventDefault();
  }

  componentDidMount() {
    if(getCookie("token") != null){
      this.setState({isLoggedIn: true});
    } else{
      this.setState({isLoggedIn: false});     
    }
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
    const isLoggedIn = this.state.isLoggedIn;   
    /*if(isLoggedIn){
      window.location.replace("/creatememory-page");     
    } else {*/
      return (
      <div>
        <Header
          absolute
          color="transparent"
          brand="MEMORIST"
          
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
          <div className={classes.container}>
            <GridContainer justify="center">
              <GridItem xs={12} sm={12} md={4}>
                <Typography variant="h2" gutterBottom>
                  Share your memory!
                </Typography>
                <br />
                <br />
                <br />
                <Card className={classes[this.state.cardAnimaton]}>
                  <form className={classes.form} onSubmit={this.handleLogin}>
                    <CardHeader color="primary" className={classes.cardHeader}>
                      <h4>Login</h4>
                      
                    </CardHeader>
                    
                    <CardBody>
                      
                      <CustomInput
                        labelText="Email"
                        id="username"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "email",
                          endAdornment: (
                            <InputAdornment position="end">
                              <Email className={classes.inputIconsColor} />
                            </InputAdornment>
                          )
                        }}
                      />
                      <CustomInput
                        labelText="Password"
                        id="password"
                    
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "password",
                          endAdornment: (
                            <InputAdornment position="end">
                              <Icon className={classes.inputIconsColor}>
                                lock_outline
                              </Icon>
                            </InputAdornment>
                          )
                        }}
                      />
                    </CardBody>
                    <CardFooter className={classes.cardFooter}>
                      <Button simple color="primary" size="lg" onClick={this.handleLogin}>
                        Login
                      </Button>
                      
                      <Link to={"/signup-page"} className={classes.link}>
                        <Button simple color="primary" size="lg">
                          Sign Up
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

function setCookie(key, value) {
    var expires = new Date();
    expires.setTime(expires.getTime() + (1 * 24 * 60 * 60 * 1000));
    document.cookie = key + '=' + value + ";path=/" + ';expires=' + expires.toUTCString();
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : null;
}

export default withStyles(loginPageStyle)(LoginPage);
