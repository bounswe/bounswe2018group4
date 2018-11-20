import React from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import InputLabel from '@material-ui/core/InputLabel';
import { Link } from "react-router-dom";
//import People from "@material-ui/icons/People";
// core components
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Input from '@material-ui/core/Input';
import Header from "components/Header/Header.jsx";
import FormControl from "@material-ui/core/FormControl";
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

import image from "assets/img/bg7.jpg";


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
    var data = {
      title: this.state.title,
      tags: this.state.tags,
      location: this.state.location,
      story1: this.state.story1,
      multimedia1: this.state.multimedia1,
      story2: this.state.story2,
      multimedia2: this.state.multimedia2,
      story3: this.state.story3,
      multimedia3: this.state.multimedia3,
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
      liked_users: ["me"]
    }
    var userToken = getCookie("token");
    fetch('http://18.234.162.48:8000/post/create/',
    {
      mode: 'cors',
      headers: {
        'Content-Type' : 'application/json',
        'Accept' : 'application/json',
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
                          onChange: this.handleChange,
                          type: "textarea"
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
                          onChange: this.handleChange,
                          type: "textarea"
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
                          onChange: this.handleChange,
                          type: "textarea"
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
                      <FormControl>
                        <InputLabel htmlFor="start_decade">Decade       </InputLabel>
                        <Select
                          value={this.state.start_decade}
                          onChange={this.handleChange}
                          id="start_decade"
                          input={<Input name="start_decade" id="start_decade" value={this.state.start_decade}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1900}>1900s</MenuItem>
                          <MenuItem value={1910}>1910s</MenuItem>
                          <MenuItem value={1920}>1920s</MenuItem>
                          <MenuItem value={1930}>1930s</MenuItem>
                          <MenuItem value={1940}>1940s</MenuItem>
                          <MenuItem value={1950}>1950s</MenuItem>
                          <MenuItem value={1960}>1960s</MenuItem>
                          <MenuItem value={1970}>1970s</MenuItem>
                          <MenuItem value={1980}>1980s</MenuItem>
                          <MenuItem value={1990}>1990s</MenuItem>
                          <MenuItem value={2000}>2000s</MenuItem>
                          <MenuItem value={2010}>2010s</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel htmlFor="start_year">Year       </InputLabel>
                        <Select
                          value={this.state.start_year}
                          onChange={this.handleChange}
                          id="start_year"
                          input={<Input name="start_year" id="start_year" value={this.state.start_year}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={10}>Ten</MenuItem>
                          <MenuItem value={20}>Twenty</MenuItem>
                          <MenuItem value={30}>Thirty</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Month       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="start_month" id="start_month" value={this.state.start_month}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Day       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="start_day" id="start_day" value={this.state.start_day}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                          <MenuItem value={24}>24</MenuItem>
                          <MenuItem value={25}>25</MenuItem>
                          <MenuItem value={26}>26</MenuItem>
                          <MenuItem value={27}>27</MenuItem>
                          <MenuItem value={28}>28</MenuItem>
                          <MenuItem value={29}>29</MenuItem>
                          <MenuItem value={30}>30</MenuItem>
                          <MenuItem value={31}>31</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Hour       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="start_hour" id="start_hour" value={this.state.start_hour}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={0}>00</MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Minute       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="start_minute" id="start_minute" value={this.state.start_minute}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={0}>00</MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                          <MenuItem value={24}>24</MenuItem>
                          <MenuItem value={25}>25</MenuItem>
                          <MenuItem value={26}>26</MenuItem>
                          <MenuItem value={27}>27</MenuItem>
                          <MenuItem value={28}>28</MenuItem>
                          <MenuItem value={29}>29</MenuItem>
                          <MenuItem value={30}>30</MenuItem>
                          <MenuItem value={31}>31</MenuItem>
                          <MenuItem value={32}>32</MenuItem>
                          <MenuItem value={33}>33</MenuItem>
                          <MenuItem value={34}>34</MenuItem>
                          <MenuItem value={35}>35</MenuItem>
                          <MenuItem value={36}>36</MenuItem>
                          <MenuItem value={37}>37</MenuItem>
                          <MenuItem value={38}>38</MenuItem>
                          <MenuItem value={39}>39</MenuItem>
                          <MenuItem value={40}>40</MenuItem>
                          <MenuItem value={41}>41</MenuItem>
                          <MenuItem value={42}>42</MenuItem>
                          <MenuItem value={43}>43</MenuItem>
                          <MenuItem value={44}>44</MenuItem>
                          <MenuItem value={45}>45</MenuItem>
                          <MenuItem value={46}>46</MenuItem>
                          <MenuItem value={47}>47</MenuItem>
                          <MenuItem value={48}>48</MenuItem>
                          <MenuItem value={49}>49</MenuItem>
                          <MenuItem value={50}>50</MenuItem>
                          <MenuItem value={51}>51</MenuItem>
                          <MenuItem value={52}>52</MenuItem>
                          <MenuItem value={53}>53</MenuItem>
                          <MenuItem value={54}>54</MenuItem>
                          <MenuItem value={55}>55</MenuItem>
                          <MenuItem value={56}>56</MenuItem>
                          <MenuItem value={57}>57</MenuItem>
                          <MenuItem value={58}>58</MenuItem>
                          <MenuItem value={59}>59</MenuItem>
                        </Select>
                      </FormControl>
                      <br />

                      <Typography variant="title" gutterBottom>
                        End date (optional)
                      </Typography>


                      <FormControl>
                        <InputLabel>Decade       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="end_decade" id="end_decade" value={this.state.end_decade}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1900}>1900s</MenuItem>
                          <MenuItem value={1910}>1910s</MenuItem>
                          <MenuItem value={1920}>1920s</MenuItem>
                          <MenuItem value={1930}>1930s</MenuItem>
                          <MenuItem value={1940}>1940s</MenuItem>
                          <MenuItem value={1950}>1950s</MenuItem>
                          <MenuItem value={1960}>1960s</MenuItem>
                          <MenuItem value={1970}>1970s</MenuItem>
                          <MenuItem value={1980}>1980s</MenuItem>
                          <MenuItem value={1990}>1990s</MenuItem>
                          <MenuItem value={2000}>2000s</MenuItem>
                          <MenuItem value={2010}>2010s</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Year       </InputLabel>
                        <Select
                          value={this.state.end_year}
                          onChange={this.handleChange}
                          id="end_year"
                          input={<Input name="end_year" id="end_year" value={this.state.end_year}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={10}>Ten</MenuItem>
                          <MenuItem value={20}>Twenty</MenuItem>
                          <MenuItem value={30}>Thirty</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Month       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="end_month" id="end_month" value={this.state.end_month}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Day       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="end_day" id="end_day" value={this.state.end_day}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                          <MenuItem value={24}>24</MenuItem>
                          <MenuItem value={25}>25</MenuItem>
                          <MenuItem value={26}>26</MenuItem>
                          <MenuItem value={27}>27</MenuItem>
                          <MenuItem value={28}>28</MenuItem>
                          <MenuItem value={29}>29</MenuItem>
                          <MenuItem value={30}>30</MenuItem>
                          <MenuItem value={31}>31</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Hour       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="end_hour" id="end_hour" value={this.state.end_hour}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={0}>00</MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                        </Select>
                      </FormControl>
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <FormControl>
                        <InputLabel>Minute       </InputLabel>
                        <Select
                          onChange={this.handleChange}
                          input={<Input name="end_minute" id="end_minute" value={this.state.end_minute}/>}
                        >
                          <MenuItem value="">
                            <em>None</em>
                          </MenuItem>
                          <MenuItem value={0}>00</MenuItem>
                          <MenuItem value={1}>01</MenuItem>
                          <MenuItem value={2}>02</MenuItem>
                          <MenuItem value={3}>03</MenuItem>
                          <MenuItem value={4}>04</MenuItem>
                          <MenuItem value={5}>05</MenuItem>
                          <MenuItem value={6}>06</MenuItem>
                          <MenuItem value={7}>07</MenuItem>
                          <MenuItem value={8}>08</MenuItem>
                          <MenuItem value={9}>09</MenuItem>
                          <MenuItem value={10}>10</MenuItem>
                          <MenuItem value={11}>11</MenuItem>
                          <MenuItem value={12}>12</MenuItem>
                          <MenuItem value={13}>13</MenuItem>
                          <MenuItem value={14}>14</MenuItem>
                          <MenuItem value={15}>15</MenuItem>
                          <MenuItem value={16}>16</MenuItem>
                          <MenuItem value={17}>17</MenuItem>
                          <MenuItem value={18}>18</MenuItem>
                          <MenuItem value={19}>19</MenuItem>
                          <MenuItem value={20}>20</MenuItem>
                          <MenuItem value={21}>21</MenuItem>
                          <MenuItem value={22}>22</MenuItem>
                          <MenuItem value={23}>23</MenuItem>
                          <MenuItem value={24}>24</MenuItem>
                          <MenuItem value={25}>25</MenuItem>
                          <MenuItem value={26}>26</MenuItem>
                          <MenuItem value={27}>27</MenuItem>
                          <MenuItem value={28}>28</MenuItem>
                          <MenuItem value={29}>29</MenuItem>
                          <MenuItem value={30}>30</MenuItem>
                          <MenuItem value={31}>31</MenuItem>
                          <MenuItem value={32}>32</MenuItem>
                          <MenuItem value={33}>33</MenuItem>
                          <MenuItem value={34}>34</MenuItem>
                          <MenuItem value={35}>35</MenuItem>
                          <MenuItem value={36}>36</MenuItem>
                          <MenuItem value={37}>37</MenuItem>
                          <MenuItem value={38}>38</MenuItem>
                          <MenuItem value={39}>39</MenuItem>
                          <MenuItem value={40}>40</MenuItem>
                          <MenuItem value={41}>41</MenuItem>
                          <MenuItem value={42}>42</MenuItem>
                          <MenuItem value={43}>43</MenuItem>
                          <MenuItem value={44}>44</MenuItem>
                          <MenuItem value={45}>45</MenuItem>
                          <MenuItem value={46}>46</MenuItem>
                          <MenuItem value={47}>47</MenuItem>
                          <MenuItem value={48}>48</MenuItem>
                          <MenuItem value={49}>49</MenuItem>
                          <MenuItem value={50}>50</MenuItem>
                          <MenuItem value={51}>51</MenuItem>
                          <MenuItem value={52}>52</MenuItem>
                          <MenuItem value={53}>53</MenuItem>
                          <MenuItem value={54}>54</MenuItem>
                          <MenuItem value={55}>55</MenuItem>
                          <MenuItem value={56}>56</MenuItem>
                          <MenuItem value={57}>57</MenuItem>
                          <MenuItem value={58}>58</MenuItem>
                          <MenuItem value={59}>59</MenuItem>
                        </Select>
                      </FormControl>
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
      </div>
    );
  }
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : null;
}

export default withStyles(loginPageStyle)(CreateMemoryPage);
