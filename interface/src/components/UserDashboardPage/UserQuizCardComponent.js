import React from 'react';
import { Card, Button } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import './Dashboard.css';

const UserQuizCard = ({ name, difficulty, description }) => {
	const getQuiz = () => {
		// fetch the specific quiz
	};

	return (
		<Card>
			<Card.Content>
				<Card.Header>{name}</Card.Header>
				<Card.Meta>Difficulty: {difficulty}</Card.Meta>
				<Card.Description>{description}</Card.Description>
			</Card.Content>
			<Card.Content extra>
				<div className="ui two buttons">
					<Button
						basic
						color="green"
						onClick={() => {
							getQuiz();
						}}
					>
						<Link to="/quiz-results">Results</Link>
					</Button>
					<Button
						basic
						color="black"
						onClick={() => {
							getQuiz();
						}}
					>
						<Link to="/add-questions" className="addQuestionsButton">
							Add questions
						</Link>
					</Button>
				</div>
			</Card.Content>
		</Card>
	);
};

UserQuizCard.propTypes = {
	name: PropTypes.string.isRequired,
	difficulty: PropTypes.number.isRequired,
	description: PropTypes.string.isRequired
};

export default UserQuizCard;
