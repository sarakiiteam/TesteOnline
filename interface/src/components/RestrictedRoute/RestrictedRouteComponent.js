import React from 'react';
import PropTypes from 'prop-types';
import { Route, Redirect } from 'react-router-dom';
import { withRouter } from 'react-router';

const RestrictedRoute = ({ redirectTo, component: Component, ...otherProps }) => {
	const usernameLogged = sessionStorage.getItem('username');
	return (
		<Route
			{...otherProps}
			render={(props) =>
				usernameLogged ? (
					<Component {...props} />
				) : (
					<Redirect
						to={{
							pathname: redirectTo
						}}
					/>
				)}
		/>
	);
};

RestrictedRoute.propTypes = {
	redirectTo: PropTypes.string.isRequired,
	component: PropTypes.func.isRequired,
	location: PropTypes.object.isRequired
};

export default withRouter(RestrictedRoute);
