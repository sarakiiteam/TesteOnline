import React, { useContext, useState, useEffect } from 'react';

import WrapperComponent from '../../WrapperComponent/WrapperComponent';

import './CreateQuiz.css';

import { Card, Table } from 'semantic-ui-react';

const ResultsPage = () => {
	const [ results, setResults ] = useState([]);
	const [ testName, setTestName ] = useState('da-test');

	useEffect(() => {
		fetch(`http://localhost:8080/api/results/${testName}`)
			.then((data) => data.json())
			.then((parsedData) => {
				console.log(parsedData);
				setResults(parsedData['results']);
			})
			.catch((error) => console.log(error));
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
