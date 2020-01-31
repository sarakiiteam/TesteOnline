import React, { useContext } from 'react';

import { Form, Button, Divider } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../../Contexts/QuizPageContext';

import './QuizComponent.css';

const Quiz = () => {
  const quizPageContext = useContext(QuizPageContext);

  const { quizQuestions } = quizPageContext;

  return (
    <>
      <Form className='quizForm'>
        {quizQuestions.map((question, index) => (
          <Form.Group grouped key={index} className='question'>
            <h5>
              {index + 1}.) &nbsp;
              {question['question']} - <i>{question['points']} points</i>
            </h5>
            <Form.Field
              control='input'
              label={question['correctAnswer']}
              name='answer'
              type='radio'
            />
            <Form.Field
              control='input'
              label={question['firstWrongAnswer']}
              name='answer'
              type='radio'
              className='correctAnswer'
            />
            <Form.Field
              control='input'
              label={question['secondWrongAnswer']}
              name='answer'
              type='radio'
              className='wrongAnswer'
            />
            <Divider />
          </Form.Group>
        ))}
      </Form>
      <br />
      <Button>What's the score?!</Button>
    </>
  );
};
export default Quiz;
