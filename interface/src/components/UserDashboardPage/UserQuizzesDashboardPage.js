import React, { useEffect, useState } from 'react';
import { Card, Divider, Loader } from 'semantic-ui-react';

import WrapperComponent from '../WrapperComponent/WrapperComponent';

import './Dashboard.css';
import UserQuizCard from './UserQuizCardComponent';

const UserQuizzes = () => {
  const [quizzes, setQuizzes] = useState([]);

  useEffect(() => {
    fetch(
      `http://localhost:8080/api/tests/users/,
      ${sessionStorage.getItem('username')}`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      }
    )
      .then(data => data.json())
      .then(parsedData => {
        console.log(parsedData);
        setQuizzes(parsedData['tests']);
      })
      .catch(error => console.log(error));
  }, []);

  return (
    <WrapperComponent>
      <Card.Group itemsPerRow={3} centered stackable className='quizzesStyle'>
        {quizzes ? (
          quizzes.map((quiz, index) => (
            <UserQuizCard
              key={index}
              name={quiz.testName}
              difficulty={quiz.testDifficulty}
              description={quiz.description}
            />
          ))
        ) : (
          <Loader active inline='centered'>
            Fetching your quizzes..
          </Loader>
        )}
      </Card.Group>
      <Divider />
    </WrapperComponent>
  );
};

export default UserQuizzes;
