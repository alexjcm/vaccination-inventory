import React, {Suspense} from 'react';
import {Route, Switch} from 'wouter';
import Login from './pages/Login';
import ErrorPage from './pages/ErrorPage';

import {Auth0UserProvider} from './context/Auth0UserProvider';

const DashboardPage = React.lazy(() => import('./pages/Dashboard'));

export default function App() {
  return (
    <Auth0UserProvider>
      <div className="App">
        <Suspense fallback={null}>
          <section className="App-content">
            <Switch>
              <Route component={DashboardPage} path="/" />
              <Route component={Login} path="/login" />
              <Route component={ErrorPage} path="/:rest*" />
            </Switch>
          </section>
        </Suspense>
      </div>
    </Auth0UserProvider>
  );
}
