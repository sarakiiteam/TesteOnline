import React, { useContext, useState } from 'react';

import { Form, Button, Header } from 'semantic-ui-react';

import { Context as AppContext } from '../../Contexts/AppContext';

import './Login.css';

const LoginComponent = () => {
	const appContext = useContext(AppContext);
	const { setHasAccount, history } = appContext;

	const [ userCredentials, setUserCredentials ] = useState({
		username: '',
		password: ''
	});

	return (
		<React.Fragment>
			<Header>{'Who are you?'}</Header>
			<br />
			<Form>
				<Form.Input
					fluid
					icon="user"
					iconPosition="left"
					placeholder="E-mail address"
					onChange={(e) => {
						setUserCredentials({
							...userCredentials,
							username: e.target.value
						});
					}}
				/>
				<Form.Input
					fluid
					icon="lock"
					iconPosition="left"
					placeholder="Password"
					type="password"
					onChange={(e) => {
						setUserCredentials({
							...userCredentials,
							password: e.target.value
						});
					}}
				/>
				<br />
				<Button
					secondary
					type="submit"
					onClick={() => {
						// TODO: validare campuri
						sessionStorage.setItem('username', userCredentials['username']);
						history.push('/user-quizzes');
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
					className="toRegisterSpan"
				>
					I don't have an account
				</span>
			</Form>
		</React.Fragment>
	);
};

export default LoginComponent;
