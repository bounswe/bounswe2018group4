/*eslint-disable*/
import React from "react";
// react components for routing our app without refresh
import { Link } from "react-router-dom";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
import List from "@material-ui/core/List";
import Typography from '@material-ui/core/Typography';
import Icon from "@material-ui/core/Icon";
import AccountCircle from "@material-ui/icons/AccountCircle";
import ListItem from "@material-ui/core/ListItem";
import Tooltip from "@material-ui/core/Tooltip";

// @material-ui/icons
import { Apps, CloudDownload } from "@material-ui/icons";

// core components
import CustomDropdown from "components/CustomDropdown/CustomDropdown.jsx";
import Button from "components/CustomButtons/Button.jsx";

import headerLinksStyle from "assets/jss/material-kit-react/components/headerLinksStyle.jsx";

function StaticHeaderLinks({ ...props }) {
  const { classes } = props;
  return (
    <List className={classes.list}>
      <ListItem className={classes.listItem}>
      <Link to={"/profile-page"} className={classes.link}>
        <Button
          color="transparent"
          target="_blank"
        >
        <AccountCircle className={classes.icons} /> Profile
        </Button>
        </Link>
      </ListItem>
      <ListItem className={classes.listItem}>
      <Link to={"/creatememory-page"} className={classes.link}>
        <Button
          color="transparent"
          target="_blank"
        >
        <Icon className={classes.icon} color="disabled" fontSize="large">
        add_circle
      </Icon>
      Create Memory
        </Button>
        </Link>
      </ListItem>
      <ListItem className={classes.listItem}>
      <Link to={"/login-page"} className={classes.link}>
        <Button
          color="transparent"
          target="_blank"
        >
      Log out
        </Button>
        </Link>
      </ListItem>
    </List>
  );
}

function getCookie(key) {
    var keyValue = document.cookie.match('(^|;) ?' + key + '=([^;]*)(;|$)');
    return keyValue ? keyValue[2] : null;
}

export default withStyles(headerLinksStyle)(StaticHeaderLinks);
