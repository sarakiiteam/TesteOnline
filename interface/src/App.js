import React from 'react';

import { Route, Switch, withRouter, Redirect } from 'react-router-dom';

import './App.css';
import LandingPage from './components/LandingPageComponent/LandingPageComponent';
import QuizzesPage from './components/QuizzesPage/QuizzesPageComponent';
import MenuComponent from './components/MenuComponent/MenuComponent';
import LoginPage from './components/LoginPage/LoginPage';

import { Provider as AppProvider } from './Contexts/AppContext/index';
import { Provider as QuizProvider } from './Contexts/QuizPageContext/index';

import QuizPage from './components/QuizPageComponent/QuizPageComponent';
import UpdateProfilePage from './components/UserDashboardPage/UpdateProfilePage';
import UserQuizzes from './components/UserDashboardPage/UserQuizzesDashboardPage';
import CreateQuizPage from './components/UserDashboardPage/CreateQuizPage/CreateQuizPage';
import RestrictedRoute from './components/RestrictedRoute/RestrictedRouteComponent';
import AddQuestionsPage from './components/UserDashboardPage/CreateQuizPage/AddQuestionsPage';
import ResultsPage from './components/UserDashboardPage/CreateQuizPage/ResultsPage';

const App = (props) => {
	return (
		<AppProvider {...props}>
			{window.location.pathname !== '/home' && <MenuComponent {...props} />}

			<Switch>
				<Route exact path="/">
					<Redirect to="/home" />
				</Route>
				<Route path="/home" exact component={LandingPage} />
				<Route path="/login" exact component={LoginPage} />
				<QuizProvider {...props}>
					{/* restricted routes, only if logged */}
					<RestrictedRoute exact path="/update-profile" component={UpdateProfilePage} redirectTo="/login" />
					<RestrictedRoute exact path="/create-quiz" component={CreateQuizPage} redirectTo="/login" />
					<RestrictedRoute exact path="/user-quizzes" component={UserQuizzes} redirectTo="/login" />
					<RestrictedRoute exact path="/quiz-results" component={ResultsPage} redirectTo="/login" />
					<RestrictedRoute exact path="/add-questions" component={AddQuestionsPage} redirectTo="/login" />
					{/* until here */}
					<Route path="/solve-quiz" exact component={QuizzesPage} />
					<Route path="/quiz" exact component={QuizPage} />
				</QuizProvider>
			</Switch>
		</AppProvider>
	);
};

export default withRouter(App);
