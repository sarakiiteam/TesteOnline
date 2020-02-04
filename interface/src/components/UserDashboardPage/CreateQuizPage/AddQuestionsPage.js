import React, { useContext } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Button } from 'semantic-ui-react';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';
import { Context as AppContext } from '../../../Contexts/AppContext';

import Questions from './QuestionsComponent';
import AddQuestion from './AddQuestionComponent';

const AddQuestionsPage = () => {
	const quizContext = useContext(QuizContext);
	const appContext = useContext(AppContext);

	const { quizQuestions, setQuizDetailsFilled, quizTitle } = quizContext;
	const { history } = appContext;

	return (
		<WrapperComponent>
			<Card.Group className="createQuizCards">
				<Card className="quizQuestionsDiv">
					<Card.Content>
						<Card.Header>{quizTitle}</Card.Header>
						<br />
						<Questions questions={quizQuestions} />
						<br />
						<Button
							onClick={() => {
								setQuizDetailsFilled(false);
								history.push('/user-quizzes');
							}}
						>
							That's all
						</Button>
					</Card.Content>
				</Card>
				<Card className="addQuestionDiv">
					<h3>Add question</h3>
					<AddQuestion />
				</Card>
			</Card.Group>
		</WrapperComponent>
	);
};

export default AddQuestionsPage;
