import React, { useEffect, useState, useContext } from 'react';
import { Card, Divider, Loader } from 'semantic-ui-react';
import QuizCard from './QuizCardComponent/QuizCardComponent';

import WrapperComponent from '../WrapperComponent/WrapperComponent';
import { Context as QuizContext } from '../../Contexts/QuizPageContext';

import './QuizzesPage.css';

const QuizzesPage = () => {
  const [tests, setTests] = useState([]);
  const quizContext = useContext(QuizContext);

  const {
    setIsSolved,
    setCheckingResults,
    setErrorResults,
    setTimeIsUp,
    setAllQuizzes,
    allQuizzes
  } = quizContext;

  const initQuestion = () => {
    setIsSolved(false);
    setCheckingResults(false);
    setErrorResults(false);
    setTimeIsUp(false);
  };

  // get all the tests from server
  useEffect(() => {
    fetch(`http://localhost:8080/api/tests/users`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(data => data.json())
      .then(parsedData => {
        let allQuizzes = [];
        allQuizzes = parsedData['tests'].map(test => ({
          name: test['testName'],
          difficulty: test['testDifficulty'],
          authorName: test['proposedBy']['usern'],
          description: test['description']
        }));

        setTests(allQuizzes);
        setAllQuizzes(allQuizzes);
      });
    initQuestion();
  }, []);

  useEffect(() => {
    setTests(allQuizzes);
  }, [allQuizzes]);

  return (
    <WrapperComponent>
      <Card.Group itemsPerRow={3} centered stackable className='quizzesStyle'>
        {tests.length ? (
          tests.map((quiz, index) => {
            return (
              <QuizCard
                key={index}
                name={quiz.name}
                difficulty={quiz.difficulty}
                authorName={quiz.authorName}
                description={quiz.description}
              />
            );
          })
        ) : (
          <Loader active inline='centered'>
            Fetching all the quizzes...
          </Loader>
        )}
      </Card.Group>
      <Divider />
    </WrapperComponent>
  );
};

export default QuizzesPage;
