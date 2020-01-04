import React, { useContext } from 'react';

import { Form, Button, Divider } from 'semantic-ui-react';
import { Context as QuizPageContext } from '../../../Contexts/QuizPageContext';

import './QuizComponent.css';

const Quiz = () => {
  const quizPageContext = useContext(QuizPageContext);

  const { quiz } = quizPageContext;
  const { questions } = quiz;

  return (
    <>
      <Form className='quizForm'>
        {questions.map((question, index) => (
          <Form.Group grouped key={index} className='question'>
            <h5>{question.title}</h5>

            <Form.Field
              control='input'
              label={question.answers[0]}
              name='answer'
              type='radio'
            />
            <Form.Field
              control='input'
              label={question.answers[1]}
              name='answer'
              type='radio'
              className='correctAnswer'
            />
            <Form.Field
              control='input'
              label={question.answers[2]}
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
