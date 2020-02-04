import React, { useContext, useState } from 'react';

import { Form, Button, Message } from 'semantic-ui-react';
import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

import './CreateQuiz.css';

const AddQuestion = () => {
  const quizContext = useContext(QuizContext);
  const { quizQuestions, setQuizQuestions, quizTitle } = quizContext;

  const initialState = {
    question: '',
    answer: '',
    wrong1: '',
    wrong2: '',
    points: 0
  };

  const [question, setQuestion] = useState(initialState);
  const [error, setError] = useState(false);

  const handleAddNewQuestion = () => {
    fetch(`http://localhost:8080/api/tests/${quizTitle}/questions/add`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(question)
    })
      .then(data => data.json())
      .then(parsedData => {
        if (parsedData['code'] === 'CREATED') {
          setQuizQuestions([...quizQuestions, question]);
          setQuestion(initialState);
          return;
        }
        console.log('---------Question creating error-----------');
        setError(true);
      })
      .catch(error => console.log(error));
  };

  return (
    <Form className='formAddQuestion'>
      <Form.Input
        fluid
        icon='question circle outline'
        iconPosition='left'
        placeholder='Text'
        onChange={event =>
          setQuestion({
            ...question,
            question: event.target.value
          })
        }
        value={question['question']}
      />
      <Form.Input
        fluid
        icon='check'
        iconPosition='left'
        placeholder='Right answer'
        onChange={event =>
          setQuestion({
            ...question,
            answer: event.target.value
          })
        }
        value={question['answer']}
      />
      <Form.Input
        fluid
        icon='x'
        iconPosition='left'
        placeholder='Wrong answer'
        onChange={event =>
          setQuestion({
            ...question,
            wrong1: event.target.value
          })
        }
        value={question['wrong1']}
      />
      <Form.Input
        fluid
        icon='x'
        iconPosition='left'
        placeholder='Wrong answer'
        onChange={event =>
          setQuestion({
            ...question,
            wrong2: event.target.value
          })
        }
        value={question['wrong2']}
      />
      <Form.Input
        fluid
        icon='star'
        iconPosition='left'
        placeholder='Points'
        onChange={event =>
          setQuestion({
            ...question,
            points: Number(event.target.value)
          })
        }
        value={question['points']}
      />
      <br />
      <br />
      <Message
        error
        visible={error}
        onDismiss={() => {
          setError(false);
        }}
        content='The question has not been added!'
        header='Error at creating the new question!'
      />
      <Button
        secondary
        type='submit'
        onClick={() => {
          handleAddNewQuestion();
        }}
      >
        Add question
      </Button>
    </Form>
  );
};

export default AddQuestion;
