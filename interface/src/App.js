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

const App = () => {
  return (
    <AppProvider>
      {window.location.pathname !== '/home' && <MenuComponent />}

      <Switch>
        <Route exact path='/'>
          <Redirect to='/home' />
        </Route>
        <Route path='/home' exact component={LandingPage} />
        <Route path='/login' exact component={LoginPage} />
        <QuizProvider>
          <Route path='/update-profile' exact component={UpdateProfilePage} />
          <Route path='/create-quiz' exact component={CreateQuizPage} />
          <Route path='/user-quizzes' exact component={UserQuizzes} />
          <Route path='/solve-quiz' exact component={QuizzesPage} />
          <Route path='/quiz' exact component={QuizPage} />
        </QuizProvider>
      </Switch>
    </AppProvider>
  );
};

export default withRouter(App);
