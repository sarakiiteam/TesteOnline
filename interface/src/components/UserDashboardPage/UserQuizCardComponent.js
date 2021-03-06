import React, { useContext } from 'react';
import { Card, Button } from 'semantic-ui-react';
import PropTypes from 'prop-types';

import './Dashboard.css';

import { Context as Appcontext } from '../../Contexts/AppContext';
import { Context as QuizContext } from '../../Contexts/QuizPageContext';

const UserQuizCard = ({ name, difficulty, description }) => {
  const appContext = useContext(Appcontext);
  const quizContext = useContext(QuizContext);

  const { history } = appContext;
  const { setQuizTitle, setQuizQuestions } = quizContext;

  const getQuiz = () => {
    fetch(`http://localhost:8080/api/tests/${name}/questions`)
      .then(data => data.json())
      .then(parsedData => {
        console.log(parsedData);
        setQuizQuestions(parsedData['questions']);
      })
      .catch(error => console.log(error));
  };

  return (
    <Card>
      <Card.Content>
        <Card.Header>{name}</Card.Header>
        <Card.Meta>
          <span
            style={
              difficulty === 'EASY'
                ? { color: 'green' }
                : difficulty === 'MEDIUM'
                ? { color: 'orange' }
                : difficulty === 'HARD'
                ? { color: 'red' }
                : { color: 'blue' }
            }
          >
            {difficulty}
          </span>
        </Card.Meta>
        <Card.Description>{description}</Card.Description>
      </Card.Content>
      <Card.Content extra>
        <div className='ui two buttons'>
          <Button
            basic
            color='green'
            onClick={() => {
              setQuizTitle(name);
              history.push('/quiz-results');
            }}
          >
            Results
          </Button>
          <Button
            basic
            color='black'
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
  difficulty: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired
};

export default UserQuizCard;
