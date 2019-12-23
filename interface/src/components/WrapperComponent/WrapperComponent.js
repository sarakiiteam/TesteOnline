import React from "react";
import { Container } from "semantic-ui-react";
import PropTypes from "prop-types";

import "./Wrapper.css";
// import { Context as AppContext } from "../../Contexts/AppContext";

const WrapperComponent = ({ children }) => {
  // const appContext = useContext(AppContext);

  return (
    <Container className="wrapperStyle">
      {children ? children : <></>}
    </Container>
  );
};

WrapperComponent.propTypes = {
  children: PropTypes.node
};

export default WrapperComponent;
