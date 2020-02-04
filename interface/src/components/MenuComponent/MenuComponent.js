import React, { useState, useContext } from 'react';
import { Menu, Icon } from 'semantic-ui-react';
import { Link } from 'react-router-dom';

import logo from './justLogo.png';

import { Context as ApplicationContext } from '../../Contexts/AppContext';
import { Context as QuizContext } from '../../Contexts/QuizPageContext';

const MenuComponent = () => {
  const appContext = useContext(ApplicationContext);
  const quizContext = useContext(QuizContext);

  const { history } = appContext;
  const { setQuizQuestions } = quizContext;

  const [activeItem, setActiveItem] = useState('');

  return (
    <Menu inverted fixed='top' borderless>
      <Link to='/home'>
        <Menu.Item name='Home' color='green'>
          <img src={logo} alt='logo' />
        </Menu.Item>
      </Link>
      {sessionStorage.getItem('username') && (
        <Menu.Menu position='right'>
          <Menu.Item
            name='create quiz'
            active={activeItem === 'create quiz'}
            onClick={(e, { name }) => {
              setActiveItem(name);
              history.push('/create-quiz');
            }}
          />
          <Menu.Item
            name='solve quiz'
            active={activeItem === 'solve quiz'}
            onClick={(e, { name }) => {
              setActiveItem(name);
              history.push('/solve-quiz');
            }}
          />
          <Menu.Item
            name='my quizzes'
            active={activeItem === 'my quizzes'}
            onClick={(e, { name }) => {
              setActiveItem(name);
              history.push('/user-quizzes');
            }}
          />
          <Menu.Item
            name='logout'
            active={activeItem === 'logout'}
            onClick={(e, { name }) => {
              setActiveItem(name);
              sessionStorage.clear();
              history.push('/login');
            }}
          />
          <Menu.Item
            name='user-profile'
            active={activeItem === 'user-profile'}
            onClick={(e, { name }) => {
              setActiveItem(name);
              history.push('/update-profile');
            }}
          >
            <Icon name='user outline' />
          </Menu.Item>
        </Menu.Menu>
      )}
    </Menu>
  );
};

export default MenuComponent;
