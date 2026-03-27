import React, {Suspense} from 'react';
import {Route, Switch} from 'wouter';
import Header from './components/Header';
import Login from './pages/Login';
import ErrorPage from './pages/ErrorPage';

import {Auth0UserProvider} from './context/Auth0UserProvider';

const HomePage = React.lazy(() => import('./pages/Home'));

export default function App() {
  console.log('Rendering App component');
  return (
    <Auth0UserProvider>
      <div className="App">
        <Suspense fallback={null}>
          <section className="App-content">
            <Header />
            <Switch>
              <Route component={HomePage} path="/" />
              <Route component={Login} path="/login" />
              <Route component={ErrorPage} path="/:rest*" />
            </Switch>
          </section>
        </Suspense>
      </div>
    </Auth0UserProvider>
  );
}
