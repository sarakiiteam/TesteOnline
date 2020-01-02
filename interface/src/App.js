import React from 'react';

import { Route, Switch, withRouter, Redirect } from 'react-router-dom';

import './App.css';
import LandingPage from './components/LandingPageComponent/LandingPageComponent';
import QuizzesPage from './components/QuizzesPage/QuizzesPageComponent';
import MenuComponent from './components/MenuComponent/MenuComponent';
import LoginPage from './components/LoginPage/LoginPage';

import { Provider as AppProvider } from './Contexts/AppContext/index';
import { Provider as QuizProvider } from './Contexts/QuizPageContext/index';
import { Provider as DashboardProvider } from './Contexts/UserDashboardContext/index';

import QuizPage from './components/QuizPageComponent/QuizPageComponent';
import UpdateProfilePage from './components/UserDashboardPage/UpdateProfilePage';
import UserQuizzes from './components/UserDashboardPage/UserQuizzesDashboardPage';
import QuizDetails from './components/UserDashboardPage/CreateQuizPage/QuizDetailsComponent';
import CreateQuizPage from './components/UserDashboardPage/CreateQuizPage/CreateQuizPage';

const App = () => {
	return (
		<AppProvider>
			{window.location.pathname !== '/home' && <MenuComponent />}

			<Switch>
				<Route exact path="/">
					<Redirect to="/home" />
				</Route>
				<Route path="/home" component={LandingPage} />
				<Route path="/login" component={LoginPage} />
				// restricted routes
				<DashboardProvider>
					<Route path="/update-profile" component={UpdateProfilePage} />
					<Route path="/create-quiz" component={CreateQuizPage} />
					<Route path="/user-quizzes" component={UserQuizzes} />
				</DashboardProvider>
				<QuizProvider>
					<Route path="/solve-quiz" component={QuizzesPage} />
					<Route path="/quiz" component={QuizPage} />
				</QuizProvider>
			</Switch>
		</AppProvider>
	);
};

export default withRouter(App);
