import React, { createContext, useState } from 'react';
import PropTypes from 'prop-types';

const Context = createContext();

const Provider = ({ children }) => {
  const [quizResponse, setQuizResponse] = useState({});

  const [resultDetails, setResultDetails] = useState({
    answers: [],
    guestName: '',
    testName: ''
  });
  const [userDetailsFilled, setUserDetailsFilled] = useState(false);

  const [quizDetailsFilled, setQuizDetailsFilled] = useState(false);
  const [quizQuestions, setQuizQuestions] = useState([
    {
      text: 'A fi sau a nu fi?',
      points: 100,
      correctAnswer: 'A fi',
      firstWrongAnswer: 'A nu fi',
      secondWrongAnswer: 'Alta intrebare'
    },
    {
      text: 'To be or not to be?',
      points: 150,
      correctAnswer: 'To be',
      firstWrongAnswer: 'Not to be',
      secondWrongAnswer: 'Is this english?'
    },
    {
      text: 'What does the fox say?',
      points: 2,
      correctAnswer: 'Ya ta ta ta ta ta ta',
      firstWrongAnswer: 'woof',
      secondWrongAnswer: 'meow'
    },
    {
      text: 'What does the fox say?',
      points: 2,
      correctAnswer: 'Ya ta ta ta ta ta ta',
      firstWrongAnswer: 'woof',
      secondWrongAnswer: 'meow'
    },
    {
      text: 'What does the fox say?',
      points: 2,
      correctAnswer: 'Ya ta ta ta ta ta ta',
      firstWrongAnswer: 'woof',
      secondWrongAnswer: 'meow'
    },
    {
      text: 'What does the fox say?',
      points: 2,
      correctAnswer: 'Ya ta ta ta ta ta ta',
      firstWrongAnswer: 'woof',
      secondWrongAnswer: 'meow'
    },
    {
      text: 'Danza kuduro?',
      points: 100,
      correctAnswer: 'Yo YO Yo',
      firstWrongAnswer: 'cucu bau',
      secondWrongAnswer: 'golaosd'
    }
  ]);

  const [quizTitle, setQuizTitle] = useState('');

  return (
    <Context.Provider
      value={{
        // VALUES
        quizResponse,
        userDetailsFilled,
        quizDetailsFilled,
        quizQuestions,
        resultDetails,
        quizTitle,

        // METHODS
        setQuizResponse,
        setUserDetailsFilled,
        setQuizDetailsFilled,
        setQuizQuestions,
        setResultDetails,
        setQuizTitle
      }}
    >
      {children}
    </Context.Provider>
  );
};

Provider.propTypes = {
  children: PropTypes.node
};

export { Context, Provider };
