import React, { createContext, useEffect, useState } from 'react';
import PropTypes from 'prop-types';

const Context = createContext();

const Provider = ({ children }) => {
	const [ quizDetailsFilled, setQuizDetailsFilled ] = useState(false);
	const [ quizQuestions, setQuizQuestions ] = useState([]);

	return (
		<Context.Provider
			value={{
				// VALUES
				quizDetailsFilled,
				quizQuestions,
				// METHODS
				setQuizDetailsFilled,
				setQuizQuestions
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
