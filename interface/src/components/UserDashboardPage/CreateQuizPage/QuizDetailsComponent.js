import React, { useContext, useState } from 'react';

import { Form, Button, TextArea, Select, Message } from 'semantic-ui-react';
import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

const QuizDetails = () => {
  const quizContext = useContext(QuizContext);

  const {
    setQuizDetailsFilled,
    setQuizTitle,
    allQuizzes,
    setAllQuizzes
  } = quizContext;
  const [quizDetails, setQuizDetails] = useState({
    testName: '',
    difficulty: 'EASY',
    description: '',
    username: sessionStorage.getItem('username')
  });

  const [error, setError] = useState(false);

  const handleAddTestDetails = () => {
    console.log(quizDetails);
    if (quizDetails.testName && quizDetails.description) {
      fetch(`http://localhost:8080/api/tests/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(quizDetails)
      })
        .then(data => data.json())
        .then(parsedData => {
          if (parsedData['code'] === 'CREATED') {
            setQuizTitle(quizDetails.testName);
            setAllQuizzes([
              ...allQuizzes,
              {
                name: quizDetails['testName'],
                difficulty: quizDetails['difficulty'],
                authorName: quizDetails['username'],
                description: quizDetails['description']
              }
            ]);
            setQuizDetailsFilled(true);
            return;
          }
          console.log('---------Quiz creating error-----------');
          setError(true);
        })
        .catch(error => console.log(error));
    }
  };

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
              testName: event.target.value
            })
          }
        />
      </Form.Field>
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
      <Message
        error
        visible={error}
        onDismiss={() => {
          setError(false);
        }}
        content='The quiz has not been created!'
        header='Error at creating the new quiz!'
      />
      <br />
      <Button
        type='submit'
        onClick={() => {
          handleAddTestDetails();
        }}
      >
        To Questions!
      </Button>
    </Form>
  );
};

export default QuizDetails;
