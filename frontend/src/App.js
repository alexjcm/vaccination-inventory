import React, {Suspense} from 'react';
import {Link, Route, Switch} from 'wouter';
import Header from './components/Header';
import Login from './pages/Login';
import ErrorPage from './pages/ErrorPage';

import {UserContextProvider} from './context/UserContext';

import home from './home.jpg';
import './App.css';

const HomePage = React.lazy(() => import('./pages/Home'));

export default function App() {
  return (
    <UserContextProvider>
      <div className="App">
        <Suspense fallback={null}>
          <section className="App-content">
            <Header />
            <Link to="/">
              <figure>
                <img className="App-logo" alt="Home logo" src={home} />
              </figure>
            </Link>
            <Switch>
              <Route component={HomePage} path="/" />
              <Route component={Login} path="/login" />
              <Route component={ErrorPage} path="/:rest*" />
            </Switch>
          </section>
        </Suspense>
      </div>
    </UserContextProvider>
  );
}
