import React, { useContext, useEffect } from 'react';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext/index';
import { Segment, Divider } from 'semantic-ui-react';

import '../../UserDashboardPage/CreateQuizPage/CreateQuiz.css';

const Result = () => {
	const quizContext = useContext(QuizContext);
	const { results, errorResults, quizQuestions } = quizContext;

	console.log(results);

	return errorResults ? (
		<h1 style={{ color: 'red' }}>The results cannot be sent!</h1>
	) : (
		<React.Fragment>
			<Segment color={results.correctAnswers > quizQuestions.length / 2 ? 'green' : 'red'}>
				<h3 style={{ color: results.correctAnswers > quizQuestions.length / 2 ? 'green' : 'red' }}>
					You have {results.correctAnswers}/{quizQuestions.length} correct answers!
				</h3>
			</Segment>
			<Segment color={results.correctAnswers > quizQuestions.length / 2 ? 'green' : 'red'}>
				<h3>Your score is {results.score}!</h3>
			</Segment>
			<Segment>
				<h3>You were wrong about the next question: </h3>
				<br />
				<br />
				{results['quizMistakes'] &&
					results['quizMistakes'].map((question, index) => (
						<div className="question" key={index}>
							<h5>
								{question['question']} - <i>{question['points']} points</i>
							</h5>
							<h6 />
							<ol>
								<li className="correctAnswer">{question['correctAnswer']}</li>
								<li>{question['firstWrongAnswer']}</li>
								<li>{question['secondWrongAnswer']}</li>
							</ol>
							<Divider />
						</div>
					))}
			</Segment>
		</React.Fragment>
	);
};

export default Result;
