import React, { useContext } from "react";

import { Form, Button } from "semantic-ui-react";
import { Context as QuizPageContext } from "../../../Contexts/QuizPageContext";

const UserDetails = () => {
  const quizPageContext = useContext(QuizPageContext);
  const { userDetailsFilled, setUserDetailsFilled } = quizPageContext;

  return (
    <Form>
      <Form.Field>
        <input placeholder="First Name" />
      </Form.Field>
      <Form.Field>
        <input placeholder="Last Name" />
      </Form.Field>
      <br />
      <Button
        type="submit"
        onClick={() => {
          // TODO: validare campuri
          setUserDetailsFilled(true);
        }}
      >
        That's me!
      </Button>
    </Form>
  );
};

export default UserDetails;
