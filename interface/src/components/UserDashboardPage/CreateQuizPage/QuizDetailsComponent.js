import React, { useContext } from 'react';

import { Form, Button, TextArea, Select } from 'semantic-ui-react';
import WrapperComponent from '../../WrapperComponent/WrapperComponent';
// import { Context as QuizPageContext } from '../../../Contexts/QuizPageContext';

const QuizDetails = () => {
	// const quizPageContext = useContext(QuizPageContext);
	// const { QuizDetailsFilled, setQuizDetailsFilled } = quizPageContext;

	const options = [
		{
			key: 'easy',
			value: 'easy',
			text: 'EASY'
		},
		{
			key: 'medium',
			value: 'medium',
			text: 'MEDIUM'
		},
		{
			key: 'hard',
			value: 'hard',
			text: 'HARD'
		}
	];

	return (
		<Form>
			<Form.Field>
				<input placeholder="Test Name" />
			</Form.Field>
			{/* username to be took from localstorage (as token) */}
			<Form.Field>
				<Select placeholder="Difficulty" options={options} />
			</Form.Field>
			<Form.Field>
				<TextArea placeholder="Description" />
			</Form.Field>
			<br />
			<Button
				type="submit"
				onClick={() => {
					// TODO: validare campuri
					// setQuizDetailsFilled(true);
				}}
			>
				To Questions!
			</Button>
		</Form>
	);
};

export default QuizDetails;
