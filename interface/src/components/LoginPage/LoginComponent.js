import React, { useContext } from 'react';

import { Form, Button, Header } from 'semantic-ui-react';

import { Context as AppContext } from '../../Contexts/AppContext';

import './Login.css';

const LoginComponent = () => {
	const appContext = useContext(AppContext);

	const { setHasAccount } = appContext;

	return (
		<React.Fragment>
			<Header>{'Who are you?'}</Header>
			<br />
			<Form>
				<Form.Input fluid icon="user" iconPosition="left" placeholder="E-mail address" />
				<Form.Input fluid icon="lock" iconPosition="left" placeholder="Password" type="password" />
				<br />
				<Button
					secondary
					type="submit"
					onClick={() => {
						// TODO: validare campuri
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
