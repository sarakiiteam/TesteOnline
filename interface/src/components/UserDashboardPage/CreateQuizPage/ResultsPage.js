import React, { useContext, useState, useEffect } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Table } from 'semantic-ui-react';

import { Context as QuizContext } from '../../../Contexts/QuizPageContext';

const ResultsPage = () => {
	const quizContext = useContext(QuizContext);
	const [ results, setResults ] = useState([
		{
			guestName: 'eduard',
			guestPoints: 1752,
			correctAnswers: 2
		},
		{
			guestName: 'mariano',
			guestPoints: 1755,
			correctAnswers: 5
		},
		{
			guestName: 'IONUTAO',
			guestPoints: 1755,
			correctAnswers: 5
		},
		{
			guestName: 'gigel',
			guestPoints: 1755,
			correctAnswers: 5
		},
		{
			guestName: 'sorinho',
			guestPoints: 1755,
			correctAnswers: 5
		}
	]);

	useEffect(() => {
		// call after results
		// fill results list
	}, []);

	return (
		<WrapperComponent>
			<Card className="resultsDiv">
				<Card.Content>
					<Card.Header>{'Test Results'}</Card.Header>
					<br />
					<Table basic="very" celled>
						<Table.Header>
							<Table.Row>
								<Table.HeaderCell>Guest Name</Table.HeaderCell>
								<Table.HeaderCell>No. Correct Answers</Table.HeaderCell>
								<Table.HeaderCell>Points</Table.HeaderCell>
							</Table.Row>
						</Table.Header>

						<Table.Body>
							{results.map((result, index) => (
								<Table.Row key={index}>
									<Table.Cell>{result['guestName']}</Table.Cell>
									<Table.Cell>{result['correctAnswers']}</Table.Cell>
									<Table.Cell>{result['guestPoints']}</Table.Cell>
								</Table.Row>
							))}
						</Table.Body>
					</Table>
				</Card.Content>
			</Card>
		</WrapperComponent>
	);
};

export default ResultsPage;
