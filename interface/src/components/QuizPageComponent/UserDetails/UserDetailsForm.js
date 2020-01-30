import React, { useContext, useState } from 'react';

import { Form, Button, Message } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../../Contexts/QuizPageContext';

const UserDetails = () => {
  const quizPageContext = useContext(QuizPageContext);
  const {
    resultDetails,
    setResultDetails,
    setUserDetailsFilled
  } = quizPageContext;

  const [guestInputs, setGuestInputs] = useState({
    lastName: '',
    firstName: ''
  });

  const [warning, setWarning] = useState(false);

  return (
    <Form>
      <Form.Field>
        <input
          placeholder='First Name'
          value={guestInputs.firstName}
          onChange={e => {
            setGuestInputs({
              ...guestInputs,
              firstName: e.target.value
            });
          }}
        />
      </Form.Field>
      <Form.Field>
        <input
          placeholder='Last Name'
          value={guestInputs.lastName}
          onChange={e => {
            setGuestInputs({
              ...guestInputs,
              lastName: e.target.value
            });
          }}
        />
      </Form.Field>
      <br />
      <Message
        warning
        visible={warning}
        onDismiss={() => {
          setWarning(false);
        }}
        header='Inputs are not correct!'
        content='Enter some valid data!'
      />
      <Button
        type='submit'
        onClick={() => {
          if (
            guestInputs.lastName &&
            guestInputs.firstName &&
            !/\d/.test(guestInputs.lastName) &&
            !/\d/.test(guestInputs.firstName)
          ) {
            setUserDetailsFilled(true);
            setResultDetails({
              ...resultDetails,
              guestName: guestInputs.firstName + guestInputs.lastName
            });
          }
          setWarning(true);
        }}
      >
        That's me!
      </Button>
    </Form>
  );
};

export default UserDetails;
