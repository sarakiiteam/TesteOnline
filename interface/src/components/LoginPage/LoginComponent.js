import React, { useContext, useState } from 'react';

import { Form, Button, Header, Message } from 'semantic-ui-react';

import { Context as AppContext } from '../../Contexts/AppContext';

import './Login.css';

const LoginComponent = () => {
  const appContext = useContext(AppContext);
  const { setHasAccount, history } = appContext;

  const [userCredentials, setUserCredentials] = useState({
    username: '',
    password: ''
  });

  const [error, setError] = useState(false);

  const handleLogin = () => {
    fetch(`http://localhost:8080/api/user/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userCredentials)
    })
      .then(data => data.json())
      .then(parsedData => {
        if (parsedData['code'] === 'OK') {
          sessionStorage.setItem('username', userCredentials['username']);
          history.push('/user-quizzes');
          return;
        }
        console.log('---------Login error-----------');
        setError(true);
      })
      .catch(error => console.log(error));
  };

  return (
    <React.Fragment>
      <Header>{'Who are you?'}</Header>
      <br />
      <Form>
        <Form.Input
          fluid
          icon='user'
          iconPosition='left'
          placeholder='E-mail address'
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
          placeholder='Password'
          type='password'
          onChange={e => {
            setUserCredentials({
              ...userCredentials,
              password: e.target.value
            });
          }}
        />
        <br />
        <Message
          error
          visible={error}
          onDismiss={() => {
            setError(false);
          }}
          header='Wrong credentials!'
          content='Wrong username or password!'
        />
        <Button
          secondary
          type='submit'
          onClick={() => {
            // TODO: validare campuri
            handleLogin();
          }}
        >
          Login
        </Button>
        <br />
        <br />
        <span
          onClick={() => {
            setHasAccount(false);
          }}
          className='toRegisterSpan'
        >
          I don't have an account
        </span>
      </Form>
    </React.Fragment>
  );
};

export default LoginComponent;
