import React, { useContext, useState, useEffect } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

import { Card, Table, Loader } from 'semantic-ui-react';

const ResultsPage = () => {
  const [results, setResults] = useState([]);
  const [quizName, setQuizName] = useState('da-test');

  const quizContext = useContext(QuizContext);
  const { quizTitle } = quizContext;

  useEffect(() => {
    fetch(`http://localhost:8080/api/results/${quizTitle}`)
      .then(data => data.json())
      .then(parsedData => {
        setResults(parsedData['results']);
      })
      .catch(error => console.log(error));
  }, []);

  return (
    <WrapperComponent>
      <Card className='resultsDiv'>
        <Card.Content>
          <Card.Header>{'Quiz Results'}</Card.Header>
          <br />
          <Table basic='very' celled>
            <Table.Header>
              <Table.Row>
                <Table.HeaderCell>Guest Name</Table.HeaderCell>
                <Table.HeaderCell>No. Correct Answers</Table.HeaderCell>
                <Table.HeaderCell>Points</Table.HeaderCell>
              </Table.Row>
            </Table.Header>

            <Table.Body>
              {results ? (
                results.map((result, index) => (
                  <Table.Row key={index}>
                    <Table.Cell>{result['guestName']}</Table.Cell>
                    <Table.Cell>{result['correctAnswers']}</Table.Cell>
                    <Table.Cell>{result['guestPoints']}</Table.Cell>
                  </Table.Row>
                ))
              ) : (
                <Loader active inline='centered' />
              )}
            </Table.Body>
          </Table>
        </Card.Content>
      </Card>
    </WrapperComponent>
  );
};

export default ResultsPage;
