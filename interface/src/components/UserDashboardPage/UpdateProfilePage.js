import React from 'react';

import { Container, Card, Form, Button } from 'semantic-ui-react';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './Dashboard.css';

const UpdateProfilePage = () => {
	return (
		<WrapperComponent>
			<Container className="userPage">
				<Card>
					<Card.Content>
						<Card.Header>{'Not you anymore?'}</Card.Header>
						<br />
						<Form>
							<Form.Input fluid icon="user" iconPosition="left" placeholder="New e-mail address" />
							<Form.Input
								fluid
								icon="lock"
								iconPosition="left"
								placeholder="New password"
								type="password"
							/>
							<br />
							<Button
								secondary
								type="submit"
								onClick={() => {
									// TODO: validare campuri
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
