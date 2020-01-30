import React, { useEffect, useState } from 'react';
import { Card, Divider, Loader } from 'semantic-ui-react';
import QuizCard from './QuizCardComponent/QuizCardComponent';

import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './QuizzesPage.css';

const QuizzesPage = () => {
  const [tests, setTests] = useState([]);

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
        setTests(
          parsedData['tests'].map(test => ({
            name: test['testName'],
            difficulty: test['testDifficulty'],
            authorName: test['proposedBy']['usern'],
            description: test['description']
          }))
        );
      });
  }, []);

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
          <Loader active inline='centered' />
        )}
      </Card.Group>
      <Divider />
    </WrapperComponent>
  );
};

export default QuizzesPage;
