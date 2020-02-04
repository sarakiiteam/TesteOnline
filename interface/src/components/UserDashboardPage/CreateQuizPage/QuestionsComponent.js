import React from 'react';
import PropTypes from 'prop-types';

import { Divider } from 'semantic-ui-react';

import './CreateQuiz.css';

const Questions = ({ questions }) => (
  <div className='questionsDiv'>
    {questions.map((question, index) => (
      <div className='question' key={index}>
        <h5>
          {index + 1}.) &nbsp;
          {question['question']} - <i>{question['points']} points</i>
        </h5>
        <h6 />
        <ol>
          <li className='correctAnswer'>{question['answer']}</li>
          <li>{question['wrong1']}</li>
          <li>{question['wrong2']}</li>
        </ol>
        <Divider />
      </div>
    ))}
  </div>
);

Questions.propTypes = {
  questions: PropTypes.array.isRequired
};

export default Questions;
