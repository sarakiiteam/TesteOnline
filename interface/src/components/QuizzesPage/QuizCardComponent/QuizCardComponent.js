import React, { useContext } from 'react';
import { Card, Button } from 'semantic-ui-react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

const QuizCard = ({ name, difficulty, authorName, description }) => {
	const quizContext = useContext(QuizContext);

	const { setQuizQuestions, setQuizTitle, setResultDetails, resultDetails } = quizContext;

	const getQuiz = () => {
		fetch(`http://localhost:8080/api/tests/${name}/questions`)
			.then((data) => data.json())
			.then((parsedData) => {
				setQuizTitle(name);
				setResultDetails({
					...resultDetails,
					testName: name
				});
				setQuizQuestions(parsedData['questions']);
			})
			.catch((error) => console.log(error));
	};

	return (
		<Card>
			<Card.Content>
				<Card.Header>{name}</Card.Header>
				<Card.Meta>
					<span
						style={
							difficulty === 'EASY' ? (
								{ color: 'green' }
							) : difficulty === 'MEDIUM' ? (
								{ color: 'orange' }
							) : difficulty === 'HARD' ? (
								{ color: 'red' }
							) : (
								{ color: 'blue' }
							)
						}
					>
						{difficulty}
					</span>
					&nbsp;&nbsp; • {authorName}
				</Card.Meta>
				<Card.Description>{description}</Card.Description>
			</Card.Content>
			<Card.Content extra>
				<Link to="/quiz">
					<div className="ui two buttons">
						<Button
							basic
							color="green"
							onClick={() => {
								getQuiz();
							}}
						>
							Solve
						</Button>
					</div>
				</Link>
			</Card.Content>
		</Card>
	);
};

QuizCard.propTypes = {
	name: PropTypes.string.isRequired,
	difficulty: PropTypes.string.isRequired,
	authorName: PropTypes.string.isRequired,
	description: PropTypes.string.isRequired
};

export default QuizCard;
