import React, { useEffect, useState } from 'react';
import './LandingPage.css';

import { Button, Image, Grid, Segment } from 'semantic-ui-react';

import logo from './logo_transparent.png';

import { Link } from 'react-router-dom';
import WrapperComponent from '../WrapperComponent/WrapperComponent';

const LandingPage = () => {
	const [ indicationsText, setIndicationsText ] = useState('default');
	const [ text, setText ] = useState('');

	useEffect(
		() => {
			switch (indicationsText) {
				case 'ask':
					setText('Create a quiz!');
					break;
				case 'answer':
					setText("Let's solve some quizzes!");
					break;
				default:
					setText('Create and solve online quizzes.');
					break;
			}
		},
		[ indicationsText ]
	);

	return (
		<WrapperComponent>
			<Grid centered columns={1} className="gridStyle">
				<Grid.Column>
					<Image centered src={logo} className="logo" size="huge" />
				</Grid.Column>

				<Grid.Row>
					<Segment className="textDisplay">{text}</Segment>
				</Grid.Row>

				<Grid.Row centered columns={2} className="buttonsRow">
					<Link to="/create-quiz">
						<Button
							id="askBtn"
							className="buttonStyle"
							size="huge"
							circular
							onMouseEnter={() => {
								setIndicationsText('ask');
							}}
							onMouseLeave={() => {
								setIndicationsText('default');
							}}
						>
							Ask
						</Button>
					</Link>

					<Link to="/solve-quiz">
						<Button
							id="answerBtn"
							className="buttonStyle"
							size="huge"
							circular
							onMouseEnter={() => {
								setIndicationsText('answer');
							}}
							onMouseLeave={() => {
								setIndicationsText('default');
							}}
						>
							Answer
						</Button>
					</Link>
				</Grid.Row>
			</Grid>
		</WrapperComponent>
	);
};

export default LandingPage;
