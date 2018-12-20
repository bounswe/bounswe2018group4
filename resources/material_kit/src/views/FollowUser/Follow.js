import React from 'react';
import FollowUser from 'views/FollowUser/FollowUser.jsx';
import { Link } from 'react-router-dom';
import $ from 'jquery';

/**
   *  Profile component. Allows user to edit their profile and saves the changes via API
   */
class Follow extends React.Component {
	constructor(){
   		super();
   		this.state = {
   						profileInfo: [],
   					};
	}

	componentDidMount(){
		var userToken = localStorage.getItem('token');
    var _this = this;
    const { handle } = this.props.match.params;
    console.log(userToken);
    var url = 'http://18.234.162.48:8000/auth/get_profile/' + handle + "/";
    console.log(url);
    fetch(url,{
      mode: 'cors',
      headers: {
        'Content-Type' : 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET',
        'Access-Control-Allow-Headers': 'Content-Type',
        
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
				<FollowUser profileInfo={this.state.profileInfo}/>
			</div>
		);
	}
}

export default Follow;