import React from 'react';
import ProfilePage from 'views/ProfilePage/ProfilePage.jsx';
import { Link } from 'react-router-dom';

/**
   *  Profile component. Allows user to edit their profile and saves the changes via API
   */
class Profile extends React.Component {
	constructor(){
   		super();
   		this.state = {
   						profileInfo: [],
   					};
	}

	componentDidMount(){
		var userToken = localStorage.getItem('token');
    var _this = this;
    console.log(userToken);
    fetch('http://ec2-18-234-162-48.compute-1.amazonaws.com:8000/auth/profile/',
    {
      mode: 'cors',
      headers: {
        'Content-Type' : 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Authorization' : 'JWT ' + userToken,
      },
        method: 'GET',
      })
    .then(response => response.json())
      .then(function(data){
          console.log(data);
          _this.setState({profileInfo: data});
        })

    .catch(function(error) {
        console.log('There has been a problem with your fetch operation: ' + error.message);
    });

    }

	render() {
		return(
			<div >
				<ProfilePage profileInfo={this.state.profileInfo}/>
			</div>
		);
	}
}
export default Profile;