import React, { useContext } from 'react';

import { Container, Card } from 'semantic-ui-react';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

import LoginComponent from './LoginComponent';
import RegisterComponent from './RegisterComponent';
import { Context as AppContext } from '../../Contexts/AppContext';

import './Login.css';

const LoginPage = () => {
	const appContext = useContext(AppContext);

	const { hasAccount, setHasAccount } = appContext;

	return (
		<WrapperComponent>
			<Container className="userPage">
				<Card>
					<Card.Content>{hasAccount ? <LoginComponent /> : <RegisterComponent />}</Card.Content>
				</Card>
			</Container>
		</WrapperComponent>
	);
};

export default LoginPage;
