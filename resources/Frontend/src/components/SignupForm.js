import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Input from '@material-ui/core/Input';

class SignupForm extends React.Component {
  state = {
    first_name:'',
    last_name:'',
    username: '',
    email: '',
    password: ''
  };

  handle_change = e => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState(prevstate => {
      const newState = { ...prevstate };
      newState[name] = value;
      return newState;
    });
  };

  render() {
    return (
      <form onSubmit={e => this.props.handle_signup(e, this.state)}>
        <Typography component="h2" variant="display1" gutterBottom>Sign Up</Typography>
        <Input ref="first_name" type="text" name="first_name" placeholder="First Name" />
        <br />
        <br />
        <Input ref="last_name" type="text" name="last_name" placeholder="Last Name" />
        <br />
        <br />
        <Input ref="username" type="text" name="username" placeholder="Username" />
        <br />
        <br />
        <Input ref="email" type="text" name="email" placeholder="E-mail" />
        <br />
        <br />
        <Input ref="password" type="password" name="password" placeholder="Password" />
        <br />
        <br />
        <Button variant="contained" >
          Sign up
        </Button>
      </form>
    );
  }
}

export default SignupForm;

SignupForm.propTypes = {
  handle_signup: PropTypes.func.isRequired
};