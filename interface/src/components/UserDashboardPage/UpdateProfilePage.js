import React, { useState } from 'react';

import { Container, Card, Form, Button, Message } from 'semantic-ui-react';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './Dashboard.css';

const UpdateProfilePage = () => {
	const [ successVisibility, setSuccessVisibility ] = useState(false);
	const [ errorVisibility, setErrorVisibility ] = useState(false);

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
							<Message
								success
								visible={successVisibility}
								onDismiss={() => {
									setSuccessVisibility(false);
								}}
								header="Profile Updated"
								content="Your credentials have been updated!"
							/>
							<Message
								error
								visible={errorVisibility}
								onDismiss={() => {
									setErrorVisibility(false);
								}}
								header="Profile was not updated"
								content="Your inputs are not valid!"
							/>
							<br />
							<Button
								secondary
								type="submit"
								onClick={() => {
									// TODO: validare campuri
									// check data
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
