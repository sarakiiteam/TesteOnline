import React, { useContext, useState } from 'react';

import { Form, Button, TextArea, Select } from 'semantic-ui-react';
import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

const QuizDetails = () => {
  const quizContext = useContext(QuizContext);

  const { setQuizDetailsFilled } = quizContext;
  const [quizDetails, setQuizDetails] = useState({
    name: '',
    difficulty: 'EASY',
    description: ''
    // username: '' get from localstorage
  });

  const options = [
    {
      key: 'easy',
      value: 'EASY',
      text: 'EASY'
    },
    {
      key: 'medium',
      value: 'MEDIUM',
      text: 'MEDIUM'
    },
    {
      key: 'hard',
      value: 'HARD',
      text: 'HARD'
    }
  ];

  return (
    <Form>
      <Form.Field>
        <input
          placeholder='Name'
          onChange={event =>
            setQuizDetails({
              ...quizDetails,
              name: event.target.value
            })
          }
        />
      </Form.Field>
      {/* username to be took from localstorage (as token) */}
      <Form.Field>
        <Select
          placeholder='Difficulty'
          options={options}
          onChange={(event, data) =>
            setQuizDetails({
              ...quizDetails,
              difficulty: data.value
            })
          }
        />
      </Form.Field>
      <Form.Field>
        <TextArea
          placeholder='Description'
          onChange={(event, data) =>
            setQuizDetails({
              ...quizDetails,
              description: data.value
            })
          }
        />
      </Form.Field>
      <br />
      <Button
        type='submit'
        onClick={() => {
          // TODO: validare campuri
          setQuizDetailsFilled(true);
        }}
      >
        To Questions!
      </Button>
    </Form>
  );
};

export default QuizDetails;
