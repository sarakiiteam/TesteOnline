import React, { useState, useContext } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Container } from 'semantic-ui-react';
import QuizDetails from './QuizDetailsComponent';

import { Context as DashboardContext } from '../../../Contexts/UserDashboardContext';

const CreateQuizPage = () => {
	const dashboardContext = useContext(DashboardContext);

	const { quizDetailsFilled, setQuizDetailsFilled } = dashboardContext;

	return (
		<WrapperComponent>
			{quizDetailsFilled ? (
				<Container className="userPage">
					<Card>
						<Card.Content>
							<Card.Header>{'Quiz Details'}</Card.Header>
							<br />
							<QuizDetails />
						</Card.Content>
					</Card>
				</Container>
			) : (
				<Card.Group className="quizPage">
					<Card className="quizDiv">
						<Card.Content>
							<Card.Header>{title}</Card.Header>
							<br />
						</Card.Content>
					</Card>
					<Card className="timerDiv">
						<h3>Time</h3>
						<CountdownTimer
							className="timer"
							count={30 * questions.length}
							hideDay
							hideHours
							size={30}
							onEnd={() => {
								// send data
							}}
							responsive={true}
						/>
					</Card>
				</Card.Group>
			)}
		</WrapperComponent>
	);
};

export default CreateQuizPage;
