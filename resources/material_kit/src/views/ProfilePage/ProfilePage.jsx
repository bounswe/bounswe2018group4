import React from "react";
import $ from 'jquery';
// nodejs library that concatenates classes
import classNames from "classnames";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import Camera from "@material-ui/icons/Camera";
import Palette from "@material-ui/icons/Palette";
import Favorite from "@material-ui/icons/Favorite";
import Footer from "components/Footer/Footer.jsx";
// core components
import Header from "components/Header/Header.jsx";
//import Footer from "components/Footer/Footer.jsx";
import Button from "components/CustomButtons/Button.jsx";
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import HeaderLinks from "components/Header/StaticHeaderLinks.jsx";
import NavPills from "components/NavPills/NavPills.jsx";
import Parallax from "components/Parallax/Parallax.jsx";

import profile from "assets/img/faces/profile.jpg";
import CustomInput from "components/CustomInput/CustomInput.jsx";
import CustomDropdown from "components/CustomDropdown/CustomDropdown.jsx";
import InputAdornment from "@material-ui/core/InputAdornment";
import Icon from "@material-ui/core/Icon";
import Email from "@material-ui/icons/Email";

import studio1 from "assets/img/examples/studio-1.jpg";
import studio2 from "assets/img/examples/studio-2.jpg";
import studio3 from "assets/img/examples/studio-3.jpg";
import studio4 from "assets/img/examples/studio-4.jpg";
import studio5 from "assets/img/examples/studio-5.jpg";
import work1 from "assets/img/examples/olu-eletu.jpg";
import work2 from "assets/img/examples/clem-onojeghuo.jpg";
import work3 from "assets/img/examples/cynthia-del-rio.jpg";
import work4 from "assets/img/examples/mariya-georgieva.jpg";
import work5 from "assets/img/examples/clem-onojegaw.jpg";
import PropTypes from 'prop-types';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';

import profilePageStyle from "assets/jss/material-kit-react/views/profilePage.jsx";


const dashboardRoutes = [];

class ProfilePage extends React.Component {
  static propTypes = {
        /** Current user */
        profileInfo: PropTypes.oneOfType([PropTypes.object,PropTypes.array])
    }
  

  constructor(props) {
    super(props);
    this.state = {
      username: '',
      first_name: '',
      last_name: '',
      email:'',
      gender: '',
      location: '',
    }
      
    this.handleChange = this.handleChange.bind(this);
    this.handleEdit = this.handleEdit.bind(this);
  }

  componentDidMount(){

    
  }
  handleEdit(event) {
    var str = "";
 
    var first_name = this.state.first_name;
    if(first_name.localeCompare(str) == 0){
      first_name = this.props.profileInfo.first_name;
    }
    var last_name = this.state.last_name;
    if(last_name.localeCompare(str) == 0){
      last_name = this.props.profileInfo.last_name;
    }
   
    var gender = this.state.gender;
    if(gender == null || gender.localeCompare(str) == 0){
      gender = this.props.profileInfo.gender;
    }
    var location = this.state.location;
    if(location == null || location.localeCompare(str) == 0){
      location = this.props.profileInfo.location;
    }

    var body = {
      //username: username,
      first_name: first_name,
      last_name: last_name,
      //email: email,
      gender: gender,
      location: location,
      
    }
    var userToken = localStorage.getItem('token');
    $.ajax({
      url: "http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/auth/profile_update/",
      data: JSON.stringify(body),
      type: "POST",
      crossDomain : true,
      headers: {
        'Content-Type' : 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Authorization' : 'JWT ' + userToken,
      },
      beforeSend: () => {
        console.log();
      },
      success: (res) => {
        
        console.log("SUCCESS! Result: " + res);
        
      },
      error: (res, err) => {
        console.log(body);
      
        console.log("ERR " + res);
      }
    });
    console.log(body);
   

    event.preventDefault();
  }

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  render() {
    console.log(this.props.profileInfo);
    const { classes, ...rest } = this.props;
    const imageClasses = classNames(
      classes.imgRaised,
      classes.imgRoundedCircle,
      classes.imgFluid
    );
    const gen = this.props.profileInfo.gender;
    var genString = "";
    if (gen == 1){
      genString = "Male";
    }else if(gen == 2){
      genString = "Female";
    }else{
      genString = "Other\/Don't wanna say";
    }
    const navImageClasses = classNames(classes.imgRounded, classes.imgGallery);
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
        <Parallax small filter image={require("assets/img/bg2.jpg")} />
        <div className={classNames(classes.main, classes.mainRaised)}>
          <div>
            <div className={classes.container}>
              <GridContainer justify="center">
                <GridItem xs={12} sm={12} md={6}>
                  <div className={classes.profile}>
                    <div>
                      <img src={profile} alt="..." className={imageClasses} />
                    </div>
                    <div className={classes.name}>
                      <h3 className={classes.title}></h3>
                      
                    </div>
                  </div>
                </GridItem>
              </GridContainer>
              
              
              
                  <label htmlFor="username">Username</label>
                  <p/>
                  <label htmlFor="username">{this.props.profileInfo.username}</label>
                  <p/>
                      <label htmlFor="email">Email Address</label>
                      <p/>
                      <label htmlFor="email">{this.props.profileInfo.email}</label>
                      <p/>

                      <label htmlFor="first_name">First Name</label>
                      <CustomInput
                        labelText={this.props.profileInfo.first_name}
                        id="first_name"
                        onChange={this.handleChange}
                        
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          name: 'first_name',
                          id: 'first_name',
                          

                        }}
                      />
                      <label htmlFor="last_name">Lastname</label>
                      <CustomInput
                        labelText={this.props.profileInfo.last_name}
                        id="last_name"
                        onChange={this.handleChange}
                        
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          name: 'last_name',
                          id: 'last_name',
                          
                        }}
                      />
                      <label htmlFor="location">Locations</label>
                      <CustomInput
                        labelText={this.props.profileInfo.location}
                        id="location"
                        onChange={this.handleChange}
                        
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          name: 'location',
                          id: 'location',
                          
                        }}
                      />
                      <label htmlFor="gender">Gender</label>
                      <p/>
                      
                     
                      <label htmlFor="gender">{genString}</label>
            
                      
                      
                      <p/>
                      <Select
                        labelText={this.props.profileInfo.gender}
                        value={this.state.gender}
                        onChange={this.handleChange}
                        inputProps={{
                          name: 'gender',
                          id: 'gender',
                        }}
                      >

                        
                        <MenuItem value={"1"}>Male</MenuItem>
                        <MenuItem value={"2"}>Female</MenuItem>
                        <MenuItem value={"3"}>OtherDon't wanna say</MenuItem>
                      </Select>
                      <p/>
                  

                <Button simple color="primary" size="lg" onClick={this.handleEdit}>
                        Edit Profile
                </Button>
             
                      
            </div>
            
          </div>
          <Footer

           />
        </div>
      
      </div>
    );
  }
}

export default withStyles(profilePageStyle)(ProfilePage);
