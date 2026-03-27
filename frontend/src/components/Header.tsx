import React, {MouseEvent} from 'react';
import {useRoute, Link} from 'wouter';

import useUser from '../hooks/useUser';

import './Header.css';

import home from '../home.jpg';

export default function Header() {
  const {isLogged, logout, user} = useUser();
  const [match] = useRoute('/login');

  const handleClick = (e: MouseEvent) => {
    e.preventDefault();
    logout();
  };

  const renderLoginButtons = ({isLogged, user}: {isLogged: boolean, user?: any}) => {
    return isLogged ? (
      <div className="header-user-info">
        {user?.email && <span className="user-email">{user.email}</span>}
        <Link href="#" onClick={handleClick}>
          Logout
        </Link>
      </div>
    ) : (
      <div className="header-nav">
        <Link href="/login">Login</Link>
      </div>
    );
  };

  const content = match ? null : renderLoginButtons({isLogged, user});

  return (
    <header className="gf-header">
      <div className="header-left">
        <Link href="/">
          <img className="App-logo" alt="Home logo" src={home} />
        </Link>
      </div>
      {content}
    </header>
  );
}
