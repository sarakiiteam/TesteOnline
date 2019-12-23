import React from 'react';

import { Route, Switch, withRouter, Redirect } from 'react-router-dom';

import './App.css';
import LandingPage from './components/LandingPageComponent/LandingPageComponent';
import QuizzesPage from './components/QuizzesPage/QuizzesPageComponent';
import MenuComponent from './components/MenuComponent/MenuComponent';
import LoginComponent from './components/LoginComponent/LoginComponent';

import { Provider as AppProvider } from './Contexts/AppContext/index';
import { Provider as QuizProvider } from './Contexts/QuizPageContext/index';
import QuizPage from './components/QuizPageComponent/QuizPageComponent';

const App = () => {
  return (
    <AppProvider>
      {window.location.pathname !== '/home' && <MenuComponent />}

      <Switch>
        <Route exact path='/'>
          <Redirect to='/home' />
        </Route>

        <Route path='/home' component={LandingPage} />

        <Route path='/login' component={LoginComponent} />

        <Route path='/create-quiz' />

        <QuizProvider>
          <Route path='/solve-quiz' component={QuizzesPage} />

          <Route path='/quiz' component={QuizPage} />
        </QuizProvider>
      </Switch>
    </AppProvider>
  );
};

export default withRouter(App);
