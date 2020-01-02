import React, { createContext, useEffect, useState } from 'react';
import PropTypes from 'prop-types';

const Context = createContext();

const Provider = ({ children }) => {
	const [ deviceWidth, setDeviceWidth ] = useState(window.innerWidth);
	const [ deviceHeight, setDeviceHeight ] = useState(window.innerHeight);
	const [ selectedQuiz, setSelectedQuiz ] = useState(null);
	const [ hasAccount, setHasAccount ] = useState(true);

	useEffect(() => {
		window.addEventListener('resize', (data) => {
			const { target: { innerWidth, innerHeight } } = data;

			setDeviceWidth(innerWidth);
			setDeviceHeight(innerHeight);
		});
	}, []);

	return (
		<Context.Provider
			value={{
				// VALUES
				deviceHeight,
				deviceWidth,
				selectedQuiz,
				hasAccount,

				// METHODS
				setSelectedQuiz,
				setHasAccount
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
