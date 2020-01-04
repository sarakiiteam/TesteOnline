import React, { useContext } from 'react';
import { Card, Button } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import './Dashboard.css';

import { Context as Appcontext } from '../../Contexts/AppContext';

const UserQuizCard = ({ name, difficulty, description }) => {
	const appContext = useContext(Appcontext);

	const { history } = appContext;

	const getQuiz = () => {
		// fetch the specific quiz and set it to context
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
							history.push('/quiz-results');
						}}
					>
						Results
					</Button>
					<Button
						basic
						color="black"
						onClick={() => {
							getQuiz();
							history.push('/add-questions');
						}}
					>
						Add questions
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
