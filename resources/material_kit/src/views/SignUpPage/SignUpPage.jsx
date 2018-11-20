import React from "react";
import $ from 'jquery';
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";
// @material-ui/icons
import Footer from "components/Footer/Footer.jsx";
import Email from "@material-ui/icons/Email";
import { Link } from "react-router-dom";
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

import loginPageStyle from "assets/jss/material-kit-react/views/loginPage.jsx";

import image from "assets/img/bg7.jpg";


class SignUpPage extends React.Component {
  constructor(props) {
    super(props);
    // we use this to make the card to appear after the page has been rendered
    this.state = {
      cardAnimaton: "cardHidden",
      first_name: '',
      last_name: '',
      username: '',
      email:'',
      password: ''
    };

  
    this.handleChange = this.handleChange.bind(this);
    this.handleRegister = this.handleRegister.bind(this);

  }

  handleChange(event) {
    if(event.target.id === "first_name"){
      this.setState({first_name: event.target.value});
    }else if(event.target.id === "last_name"){
      this.setState({last_name: event.target.value});
    }else if(event.target.id === "username"){
      this.setState({username: event.target.value});
    }else if(event.target.id === "email"){
      this.setState({email: event.target.value});
    } else {
      this.setState({password: event.target.value});
    }
  }


  handleRegister(event) {
    var body = {
      username: this.state.username,
      first_name: this.state.first_name,
      last_name: this.state.last_name,
      email: this.state.email,
      password: this.state.password
    }
    $.ajax({
      url: "http://18.234.162.48:8000/auth/register/",
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
        window.location.replace("/profile-page");
      },
      error: (res, err) => {
        console.log(body);
      
        console.log("ERR " + res);
      }
    });
    event.preventDefault();
  }

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
                <Card className={classes[this.state.cardAnimaton]}>
                  <form className={classes.form} onSubmit={this.handleRegister}>
                    <CardHeader color="primary" className={classes.cardHeader}>
                      <h4>Sign Up!</h4>
                      
                    </CardHeader>
                    
                    <CardBody>

                      <CustomInput
                        labelText="Username"
                        id="username"
                        onChange={this.handleChange}
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          endAdornment: (
                            <InputAdornment position="end">
                              <Icon className={classes.inputIconsColor}>
                                account_box
                              </Icon>
                            </InputAdornment>
                          )
                          
                        }}
                      />
                      
                      <CustomInput
                        labelText="Email"
                        id="email"
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
                        labelText="First Name"
                        id="first_name"
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
                        labelText="Last Name"
                        id="last_name"
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

                      <Button simple color="primary" size="lg" onClick={this.handleRegister}>
                        Sign Up
                      </Button>
                      
                      <Link to={"/login-page"} className={classes.link}>
                        <Button simple color="primary" size="lg">
                          Login
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
    document.cookie = key + '=' + value + ';expires=' + expires.toUTCString();
}

export default withStyles(loginPageStyle)(SignUpPage);
