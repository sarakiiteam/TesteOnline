import React, { useContext, useState } from 'react';

import { Form, Button } from 'semantic-ui-react';
import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

import './CreateQuiz.css';

const AddQuestion = () => {
	const quizContext = useContext(QuizContext);
	const { quizQuestions, setQuizQuestions } = quizContext;

	const initialState = {
		text: '',
		correctAnswer: '',
		firstWrongAnswer: '',
		secondWrongAnswer: '',
		points: ''
	};

	const [ question, setQuestion ] = useState(initialState);

	return (
		<Form className="formAddQuestion">
			<Form.Input
				fluid
				icon="question circle outline"
				iconPosition="left"
				placeholder="Text"
				onChange={(event) =>
					setQuestion({
						...question,
						text: event.target.value
					})}
				value={question['text']}
			/>
			<Form.Input
				fluid
				icon="check"
				iconPosition="left"
				placeholder="Right answer"
				onChange={(event) =>
					setQuestion({
						...question,
						correctAnswer: event.target.value
					})}
				value={question['correctAnswer']}
			/>
			<Form.Input
				fluid
				icon="x"
				iconPosition="left"
				placeholder="Wrong answer"
				onChange={(event) =>
					setQuestion({
						...question,
						firstWrongAnswer: event.target.value
					})}
				value={question['firstWrongAnswer']}
			/>
			<Form.Input
				fluid
				icon="x"
				iconPosition="left"
				placeholder="Wrong answer"
				onChange={(event) =>
					setQuestion({
						...question,
						secondWrongAnswer: event.target.value
					})}
				value={question['secondWrongAnswer']}
			/>
			<Form.Input
				fluid
				icon="star"
				iconPosition="left"
				placeholder="Points"
				onChange={(event) =>
					setQuestion({
						...question,
						points: Number(event.target.value)
					})}
				value={question['points']}
			/>
			<br />
			<Button
				secondary
				type="submit"
				onClick={() => {
					// TODO: validare campuri
					setQuizQuestions([ ...quizQuestions, question ]);
					setQuestion(initialState);
				}}
			>
				Add question
			</Button>
		</Form>
	);
};

export default AddQuestion;
