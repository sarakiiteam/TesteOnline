import React from "react";
import { Menu } from "semantic-ui-react";
import { Link } from "react-router-dom";

import logo from "./justLogo.png";

const MenuComponent = () => {
  return (
    <Menu inverted fixed="top" borderless>
      <Link to="/home">
        <Menu.Item name="Home" color="green">
          <img src={logo} alt="logo" />
        </Menu.Item>
      </Link>
    </Menu>
  );
};

export default MenuComponent;
