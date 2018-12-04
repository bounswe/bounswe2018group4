import React from "react";
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
      locations: '',
    }
      

  }

  componentDidMount(){

    
  }


  render() {
    console.log(this.props.profileInfo);
    const { classes, ...rest } = this.props;
    const imageClasses = classNames(
      classes.imgRaised,
      classes.imgRoundedCircle,
      classes.imgFluid
    );
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
        <Parallax small filter image={require("assets/img/profile-bg.jpg")} />
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
                  <CustomInput
                        labelText={this.props.profileInfo.username}
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
                      <label htmlFor="email">Email Address</label>
                      <CustomInput
                        labelText={this.props.profileInfo.email}
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
                          
                        }}
                      />
                      <label htmlFor="locations">Locations</label>
                      <CustomInput
                        labelText={this.props.profileInfo.locations}
                        id="locations"
                        onChange={this.handleChange}
                        
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          
                        }}
                      />
                      <label htmlFor="gender">Gender</label>
                      <CustomDropdown
                        buttonText="Gender"
                        dropdownList={[
                          "Woman",
                          "Man",
                          "Other/Don't wanna say",
                        ]}
                        id="gender"
                        onChange={this.handleChange}
                        
                        formControlProps={{
                          fullWidth: true
                        }}
                        inputProps={{
                          onChange: this.handleChange,
                          type: "text",
                          
                        }}
                      />
                      
                  

                <Button simple color="primary" size="lg" onClick={this.handleRegister}>
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
