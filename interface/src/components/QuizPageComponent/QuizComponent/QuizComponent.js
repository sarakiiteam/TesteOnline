import React, { useContext, useEffect, useState } from 'react';

import { Form, Button, Divider } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../../Contexts/QuizPageContext';

import './QuizComponent.css';

const Quiz = () => {
	const quizPageContext = useContext(QuizPageContext);
	const {
		quizQuestions,
		resultDetails,
		setResultDetails,
		setResults,
		setCheckingResults,
		setIsSolved,
		setErrorResults,
		timeIsUp
	} = quizPageContext;

	const [ answers, setAnswers ] = useState([]);

	useEffect(() => {
		setIsSolved(false);
	});

	useEffect(
		() => {
			if (timeIsUp) {
				sendAnswers();
			}
		},
		//eslint-disable-next-line react-hooks/exhaustive-deps
		[ timeIsUp ]
	);

	useEffect(
		() => {
			if (quizQuestions.length) {
				quizQuestions.forEach((question) => {
					answers.push({
						questionId: question['questionId'],
						questionAnswer: ''
					});
				});
				setAnswers(answers);
			}
		},
		//eslint-disable-next-line react-hooks/exhaustive-deps
		[ quizQuestions ]
	);

	const setValueForQuestion = (value, index) => {
		let aux = [ ...answers ];
		aux[index]['questionAnswer'] = value;
		setAnswers(aux);
		setResultDetails({
			...resultDetails,
			answers: answers
		});
	};

	const validateAnswers = () => {
		resultDetails.answers.forEach((answer) => {
			if (!answer.questionAnswer) {
				return false;
			}
		});
		return true;
	};

	const sendAnswers = () => {
		if (validateAnswers()) {
			console.log(resultDetails);
			setIsSolved(true);
			fetch(`http://localhost:8080/api/results/test-results/add`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(resultDetails)
			})
				.then((data) => data.json())
				.then((results) => {
					setCheckingResults(false);
					setResults(results);
				})
				.catch((error) => {
					console.log(error);
					setErrorResults(true);
				});
		}
	};

	return (
		<React.Fragment>
			<Form className="quizForm">
				{quizQuestions.map((question, index) => (
					<Form.Group grouped key={index} className="question">
						<h5>
							{index + 1}.) &nbsp;
							{question['question']} - <i>{question['points']} points</i>
						</h5>
						<Form.Field
							control="input"
							label={question['correctAnswer']}
							name={'answer' + index}
							onChange={(e) => {
								setValueForQuestion(e.target.value, index);
							}}
							value={question['correctAnswer']}
							type="radio"
						/>
						<Form.Field
							control="input"
							label={question['firstWrongAnswer']}
							name={'answer' + index}
							onChange={(e) => {
								setValueForQuestion(e.target.value, index);
							}}
							value={question['firstWrongAnswer']}
							type="radio"
						/>
						<Form.Field
							control="input"
							label={question['secondWrongAnswer']}
							name={'answer' + index}
							onChange={(e) => {
								setValueForQuestion(e.target.value, index);
							}}
							value={question['secondWrongAnswer']}
							type="radio"
						/>
						<Divider />
					</Form.Group>
				))}
			</Form>
			<br />
			<Button
				onClick={() => {
					setCheckingResults(true);
					sendAnswers();
				}}
			>
				What's the score?!
			</Button>
		</React.Fragment>
	);
};
export default Quiz;
