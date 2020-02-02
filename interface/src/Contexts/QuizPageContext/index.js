import React, { createContext, useState } from 'react';
import PropTypes from 'prop-types';

const Context = createContext();

const Provider = ({ children }) => {
	const [ quizResponse, setQuizResponse ] = useState({});

	const [ resultDetails, setResultDetails ] = useState({
		answers: [],
		guestName: '',
		testName: ''
	});
	const [ userDetailsFilled, setUserDetailsFilled ] = useState(false);

	const [ quizDetailsFilled, setQuizDetailsFilled ] = useState(false);
	const [ quizQuestions, setQuizQuestions ] = useState([]);
	const [ quizTitle, setQuizTitle ] = useState('');

	const [ isSolved, setIsSolved ] = useState(false);
	const [ checkingResults, setCheckingResults ] = useState(false);
	const [ results, setResults ] = useState({});
	const [ errorResults, setErrorResults ] = useState(false);
	const [ timeIsUp, setTimeIsUp ] = useState(false);

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
				isSolved,
				results,
				checkingResults,
				errorResults,
				timeIsUp,

				// METHODS
				setQuizResponse,
				setUserDetailsFilled,
				setQuizDetailsFilled,
				setQuizQuestions,
				setResultDetails,
				setQuizTitle,
				setIsSolved,
				setResults,
				setCheckingResults,
				setErrorResults,
				setTimeIsUp
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
