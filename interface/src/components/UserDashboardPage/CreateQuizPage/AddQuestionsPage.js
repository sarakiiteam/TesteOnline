import React, { useContext } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Button } from 'semantic-ui-react';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';
import Questions from './QuestionsComponent';
import AddQuestion from './AddQuestionComponent';

const AddQuestionsPage = () => {
  const quizContext = useContext(QuizContext);
  const { quizQuestions } = quizContext;

  return (
    <WrapperComponent>
      <Card.Group className='createQuizCards'>
        <Card className='quizQuestionsDiv'>
          <Card.Content>
            <Card.Header>{'Questions'}</Card.Header>
            <br />
            <Questions questions={quizQuestions} />
            <br />
            <Button>That's all</Button>
          </Card.Content>
        </Card>
        <Card className='addQuestionDiv'>
          <h3>Add question</h3>
          <AddQuestion />
        </Card>
      </Card.Group>
    </WrapperComponent>
  );
};

export default AddQuestionsPage;
