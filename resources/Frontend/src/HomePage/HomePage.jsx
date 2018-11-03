import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { userActions } from '../_actions';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

const styles = {
  card: {
    maxWidth: 345,
  },
  media: {
    height: 140,
  },
};

class HomePage extends React.Component {
    componentDidMount() {
        //change this line for get posts of user
        this.props.dispatch(userActions.getAll());
    }

    handleDeleteUser(id) {
        return (e) => this.props.dispatch(userActions.delete(id));
    }

    render() {
        const { user, users } = this.props;
        return (
            <div>
                <h1>Hi {user.user.username}!</h1>
                <p>Welcome to Memorist!</p>
                
                <p>
                <br/>

                    <Button size="large"  variant="contained" color="primary"  style={{ fontSize: '10px' }} component={Link} to="/createpost" className="btn btn-link">
                          Create memory
                    </Button>
                    <br/>
                    <br/>
                </p>
                <Card className={this.props.card}>
      <CardActionArea>
        <CardMedia
          className={this.props.media}
          image="/static/images/cards/contemplative-reptile.jpg"
          title="Contemplative Reptile"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            Day 1
          </Typography>
          <Typography component="p">
            <b>@cemalaytekin posted:</b> <br/>
			<b>Posted Time:</b>  October 20 17:23 <br/>
			<b>Mentioned Time:</b>  June 1st, 2016 <br/>
			<b>Location:</b>  Kilyos <br/>
			<b>Story:</b>  I was a lovely day.. We had just got out of preparation exam called PROFICIENCY. It was the first day of summer and we planned a night we can spend on the sands of Kilyos. We torched a light and we played guitar, we sang together. I had the chance to speak with her first time in that night. Until the dawn... So, Kilyos has a special meaning for us. If you stay in there for your preparation year as I did, do not forget that what happens in Kilyos stays in Kilyos except the love... <br/>
			<b>Tags:</b> FirstLove, MyDearKilyos
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary">
          Like
        </Button>
        <Button size="small" color="primary">
          Comment
        </Button>
      </CardActions>
      <CardActionArea>
        <CardMedia
          className={this.props.media}
          image="/static/images/cards/contemplative-reptile.jpg"
          title="Contemplative Reptile"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            Secret Tunnels of Robert
          </Typography>
          <Typography component="p">
            <b>@cemiloz posted:</b>  <br/>
			<b>Title:</b>  Secret Tunnels of Robert <br/>
			<b>Posted Time:</b>  October 22 11:43 <br/>
			<b>Mentioned Time:</b>  1950s <br/>
			<b>Location:</b>  Boğaziçi University <br/>
			<b>Story:</b>  While we were studying at Robert College, (It is known as Boğaziçi University right now) there were tunnels between every building of faculties. Professors would get angry when we use them but they wouldn't lock them anyway. They were very useful. <br/>
			<b>Tags:</b>  RobertCollege, SecretTunnels, Classified
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary">
          Like
        </Button>
        <Button size="small" color="primary">
          Comment
        </Button>
      </CardActions>
    </Card>
    <br/>
    <br/>
    <Button size="large" variant="contained" color="primary"  style={{ fontSize: '10px' }} component={Link} to="/login" className="btn btn-link">
                          Log out
                    </Button>
    <br/>
    <br/>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { users, authentication } = state;
    const { user } = authentication;
    return {
        user,
        users
    };
}

const connectedHomePage = connect(mapStateToProps)(HomePage);
export { connectedHomePage as HomePage };