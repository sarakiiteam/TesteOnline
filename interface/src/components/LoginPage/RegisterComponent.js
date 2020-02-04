import React, { useContext, useState } from 'react';

import { Form, Button, Header, Message } from 'semantic-ui-react';
import { Context as AppContext } from '../../Contexts/AppContext';

import './Login.css';

const RegisterComponent = () => {
  const appContext = useContext(AppContext);
  const { setHasAccount, history } = appContext;

  const [userCredentials, setUserCredentials] = useState({
    username: '',
    password: '',
    retypedPassword: ''
  });

  const [error, setError] = useState(false);

  const validateInputs = () => {
    return userCredentials.username &&
      userCredentials.password &&
      userCredentials.password.length >= 6 &&
      userCredentials.password === userCredentials.retypedPassword
      ? true
      : false;
  };

  const handleRegister = () => {
    if (!validateInputs()) {
      setError(true);
      return;
    }
    fetch(`http://localhost:8080/api/user/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: userCredentials.username,
        password: userCredentials.password
      })
    })
      .then(data => data.json())
      .then(parsedData => {
        console.log(parsedData);
        if (parsedData['code'] === 'CREATED') {
          sessionStorage.setItem('username', userCredentials['username']);
          setError(false);
          history.push('/user-quizzes');
          return;
        }
        console.log('---------Register error-----------');
        setError(true);
      })
      .catch(error => console.log(error));
  };

  return (
    <React.Fragment>
      <Header>{'Sign up'}</Header>
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
        <Form.Input
          fluid
          icon='lock'
          iconPosition='left'
          placeholder='Re-type your password'
          type='password'
          onChange={e => {
            setUserCredentials({
              ...userCredentials,
              retypedPassword: e.target.value
            });
          }}
        />
        <br />
        {/* TO ADD ERROR FOR EVERY POSSIBLE PROBLEM */}
        <Message
          error
          visible={error}
          onDismiss={() => {
            setError(false);
          }}
          header='Wrong inputs!'
          content='Choose another credentials!'
        />
        <Button
          secondary
          type='submit'
          onClick={() => {
            handleRegister();
          }}
        >
          Sign up
        </Button>
        <br />
        <br />
        <span
          onClick={() => {
            setHasAccount(true);
          }}
          className='toRegisterSpan'
        >
          I already have an account
        </span>
      </Form>
    </React.Fragment>
  );
};

export default RegisterComponent;
