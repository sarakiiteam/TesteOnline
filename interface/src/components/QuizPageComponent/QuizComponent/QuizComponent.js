import React, { useContext, useEffect, useState } from 'react';

import { Form, Button, Divider, Radio } from 'semantic-ui-react';
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
	const [ error, setError ] = useState(false);

	useEffect(() => {
		setIsSolved(false);
	}, []);

	useEffect(
		() => {
			if (timeIsUp) {
				sendAnswers();
			}
		},
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
		answers.forEach((answer) => {
			if (!answer) {
				setError(true);
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
			{/* <Message
				error
				visible={error}
				onDismiss={() => {
					setError(false);
				}}
				content="You have to answer to all the questions!"
				header="Quizz is not completed!"
			/> */}
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
