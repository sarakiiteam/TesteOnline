import React, { useState, useContext } from 'react';

import UserDetails from './UserDetails/UserDetailsForm';
import WrapperComponent from '../WrapperComponent/WrapperComponent';
import Quiz from './QuizComponent/QuizComponent';

import './QuizPage.css';

import { Card, Container } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../Contexts/QuizPageContext';
import CountdownTimer from 'react-component-countdown-timer';

const QuizPage = () => {
	const quizPageContext = useContext(QuizPageContext);
	const { userDetailsFilled, quiz } = quizPageContext;

	const { title, questions } = quiz;

	return (
		<WrapperComponent>
			{!userDetailsFilled ? (
				<Container className="userPage">
					<Card>
						<Card.Content>
							<Card.Header>{'Who are you?'}</Card.Header>
							<br />
							<UserDetails />
						</Card.Content>
					</Card>
				</Container>
			) : (
				<Card.Group className="quizPage">
					<Card className="quizDiv">
						<Card.Content>
							<Card.Header>{title}</Card.Header>
							<br />
							<Quiz />
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

export default QuizPage;
