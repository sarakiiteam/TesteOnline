import React, { useState, useContext } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Container, Button } from 'semantic-ui-react';
import QuizDetails from './QuizDetailsComponent';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

const CreateQuizPage = () => {
  const quizContext = useContext(QuizContext);

  const { quizDetailsFilled, quizQuestions } = quizContext;

  return (
    <WrapperComponent>
      {!quizDetailsFilled ? (
        <Container className='userPage'>
          <Card>
            <Card.Content>
              <Card.Header>{'Quiz Details'}</Card.Header>
              <br />
              <QuizDetails />
            </Card.Content>
          </Card>
        </Container>
      ) : (
        <Card.Group className='quizPage'>
          <Card className='quizDiv'>
            <Card.Content className='questions'>
              <Card.Header>{'Questions'}</Card.Header>
              <br />
              {quizQuestions.map((question, index) => (
                <div className='question'>
                  <h5>
                    {index + 1}.) &nbsp;
                    {question['text']} - <i>{question['points']} points</i>
                  </h5>
                  <h6></h6>
                  <ol>
                    <li className='correctAnswer'>
                      {question['correctAnswer']}
                    </li>
                    <li>{question['firstWrongAnswer']}</li>
                    <li>{question['secondWrongAnswer']}</li>
                  </ol>
                </div>
              ))}
              <Button>That's all</Button>
            </Card.Content>
          </Card>
          <Card className='timerDiv'>
            <h3>Time</h3>
          </Card>
        </Card.Group>
      )}
    </WrapperComponent>
  );
};

export default CreateQuizPage;
