import React, { useContext } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Container, Button } from 'semantic-ui-react';
import QuizDetails from './QuizDetailsComponent';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';
import Questions from './QuestionsComponent';
import AddQuestion from './AddQuestionComponent';

const CreateQuizPage = () => {
	const quizContext = useContext(QuizContext);

	const { quizDetailsFilled, quizQuestions } = quizContext;

	return (
		<WrapperComponent>
			{!quizDetailsFilled ? (
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
				<Card.Group className="createQuizCards">
					<Card className="quizQuestionsDiv">
						<Card.Content>
							<Card.Header>{'Questions'}</Card.Header>
							<br />
							<Questions questions={quizQuestions} />
							<br />
							<Button>That's all</Button>
						</Card.Content>
					</Card>
					<Card className="addQuestionDiv">
						<h3>Add question</h3>
						<AddQuestion />
					</Card>
				</Card.Group>
			)}
		</WrapperComponent>
	);
};

export default CreateQuizPage;
