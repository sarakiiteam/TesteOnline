import React, { useContext } from 'react';

import UserDetails from './UserDetails/UserDetailsForm';
import WrapperComponent from '../WrapperComponent/WrapperComponent';
import Quiz from './QuizComponent/QuizComponent';

import './QuizPage.css';

import { Card, Container, Loader, Dimmer } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../Contexts/QuizPageContext';
import CountdownTimer from 'react-component-countdown-timer';
import Result from './ResultComponent/Result';

const QuizPage = () => {
	const quizPageContext = useContext(QuizPageContext);
	const { userDetailsFilled, quizTitle, quizQuestions, isSolved, setTimeIsUp, checkingResults } = quizPageContext;

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
							<Card.Header>{quizTitle}</Card.Header>
							<br />
							{!isSolved ? (
								<Quiz />
							) : checkingResults ? (
								<React.Fragment>
									<br />
									<br />
									<br />
									<Dimmer active inverted>
										<Loader active inline="centered">
											Checking your answers..
										</Loader>
									</Dimmer>
									<br />
									<br />
									<br />
									<br />
								</React.Fragment>
							) : (
								<Result />
							)}
						</Card.Content>
					</Card>
					{!isSolved && (
						<Card className="timerDiv">
							<h3>Time</h3>
							<CountdownTimer
								className="timer"
								count={30 * quizQuestions.length}
								hideDay
								hideHours
								size={30}
								onEnd={() => {
									setTimeIsUp(true);
								}}
								responsive={true}
							/>
						</Card>
					)}
				</Card.Group>
			)}
		</WrapperComponent>
	);
};

export default QuizPage;
