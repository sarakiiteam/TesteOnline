import React from 'react';
import PropTypes from 'prop-types';

import { Divider } from 'semantic-ui-react';

import './CreateQuiz.css';

const Questions = ({ questions }) => (
	<div className="questionsDiv">
		{questions.map((question, index) => (
			<div className="question" key={index}>
				<h5>
					{index + 1}.) &nbsp;
					{question['text']} - <i>{question['points']} points</i>
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
	</div>
);

Questions.propTypes = {
	questions: PropTypes.array.isRequired
};

export default Questions;
