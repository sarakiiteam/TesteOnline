import React from 'react';

import { Form, Button, Header } from 'semantic-ui-react';

import './Login.css';

const RegisterComponent = () => {
	return (
		<React.Fragment>
			<Header>{'Sign up'}</Header>
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
					Sign up
				</Button>
			</Form>
		</React.Fragment>
	);
};

export default RegisterComponent;
