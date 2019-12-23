import React from 'react';

import { Form, Button, Container, Card } from 'semantic-ui-react';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './Login.css';

const LoginComponent = () => {
  return (
    <WrapperComponent>
      <Container className='userPage'>
        <Card>
          <Card.Content>
            <Card.Header>{'Who are you?'}</Card.Header>
            <br />
            <Form>
              <Form.Input
                fluid
                icon='user'
                iconPosition='left'
                placeholder='E-mail address'
              />
              <Form.Input
                fluid
                icon='lock'
                iconPosition='left'
                placeholder='Password'
                type='password'
              />
              <br />
              <Button
                secondary
                type='submit'
                onClick={() => {
                  // TODO: validare campuri
                  // setUserDetailsFilled(true);
                }}
              >
                Login
              </Button>
            </Form>
          </Card.Content>
        </Card>
      </Container>
    </WrapperComponent>
  );
};

export default LoginComponent;
