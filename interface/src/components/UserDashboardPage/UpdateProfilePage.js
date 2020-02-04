import React, { useState } from 'react';

import { Container, Card, Form, Button, Message } from 'semantic-ui-react';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './Dashboard.css';

const UpdateProfilePage = () => {
  const [successVisibility, setSuccessVisibility] = useState(false);
  const [errorVisibility, setErrorVisibility] = useState(false);

  const [userCredentials, setUserCredentials] = useState({
    username: '',
    password: ''
  });

  const validateInputs = () => {
    return userCredentials.username &&
      userCredentials.password &&
      userCredentials.password.length >= 6
      ? true
      : false;
  };

  const handleUpdate = () => {
    if (!validateInputs()) {
      setErrorVisibility(true);
      return;
    }
    fetch(`http://localhost:8080/api/user/update`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userCredentials)
    })
      .then(data => data.json())
      .then(parsedData => {
        console.log(parsedData);
        if (parsedData['code'] === 'OK') {
          sessionStorage.setItem('username', userCredentials['username']);
          setErrorVisibility(false);
          setSuccessVisibility(true);
          return;
        }
        console.log('---------Update error-----------');
        setErrorVisibility(true);
      })
      .catch(error => console.log(error));
  };

  return (
    <WrapperComponent>
      <Container className='userPage'>
        <Card>
          <Card.Content>
            <Card.Header>{'Not you anymore?'}</Card.Header>
            <br />
            <Form>
              <Form.Input
                fluid
                icon='user'
                iconPosition='left'
                placeholder='New e-mail address'
                onChange={e => {
                  setUserCredentials({
                    ...userCredentials,
                    username: e.target.value
                  });
                }}
              />
              <Form.Input
                fluid
                icon='lock'
                iconPosition='left'
                placeholder='New password'
                type='password'
                onChange={e => {
                  setUserCredentials({
                    ...userCredentials,
                    password: e.target.value
                  });
                }}
              />
              <Message
                success
                visible={successVisibility}
                onDismiss={() => {
                  setSuccessVisibility(false);
                }}
                header='Profile Updated'
                content='Your credentials have been updated!'
              />
              <Message
                error
                visible={errorVisibility}
                onDismiss={() => {
                  setErrorVisibility(false);
                }}
                header='Profile was not updated'
                content='Your inputs are not valid!'
              />
              <br />
              <Button
                secondary
                type='submit'
                onClick={() => {
                  handleUpdate();
                }}
              >
                Update
              </Button>
            </Form>
          </Card.Content>
        </Card>
      </Container>
    </WrapperComponent>
  );
};

export default UpdateProfilePage;
