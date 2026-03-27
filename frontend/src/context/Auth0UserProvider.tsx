import { useMemo, ReactNode, useState, useEffect } from 'react';
import { Auth0Provider, useAuth0 } from '@auth0/auth0-react';
import Context from './UserContext';
import { AuthService } from '../security/types';

const Auth0Bridge = ({ children }: { children: ReactNode }) => {
  const { loginWithRedirect, logout, isAuthenticated, getAccessTokenSilently, isLoading, user } = useAuth0();
  const [timedOut, setTimedOut] = useState(false);

  console.log('Auth0Bridge Render:', { isLoading, isAuthenticated, timedOut, user: !!user });

  useEffect(() => {
    const timer = setTimeout(() => {
      if (isLoading) {
        console.warn("Auth0 initialization timed out");
        setTimedOut(true);
      }
    }, 5000);
    return () => clearTimeout(timer);
  }, [isLoading]);

  const authService: AuthService = useMemo(() => ({
    providerName: 'Auth0',
    loginLabel: 'Login with Auth0',
    loginIcon: 'pi pi-sign-in',
    login: async () => {
      console.log('Auth0: loginWithRedirect starting...');
      try {
        await loginWithRedirect();
        console.log('Auth0: loginWithRedirect call complete (redirection should happen now)');
      } catch (error) {
        console.error('Auth0: loginWithRedirect error', error);
      }
      return ''; 
    },
    logout: () => logout({ logoutParams: { returnTo: window.location.origin } }),
    getToken: async () => {
      if (!isAuthenticated) return null;
      
      try {
        return await getAccessTokenSilently({
          authorizationParams: {
            audience: import.meta.env.VITE_AUTH0_AUDIENCE
          }
        });
      } catch (error) {
        if ((error as any)?.error !== 'login_required') {
          console.error("Error getting token", error);
        }
        return null;
      }
    },
    isAuthenticated: () => isAuthenticated,
    user,
  }), [loginWithRedirect, logout, isAuthenticated, user, getAccessTokenSilently]);

  if (isLoading && !timedOut) return <div className="App-main">Loading Auth0 authentication...</div>;

  if (timedOut && !isAuthenticated) {
      return (
          <div className="App-main">
              <div className="error-alert">
                  <h3>Auth0 Handshake Timeout</h3>
                  <p>The authentication service is taking longer than expected to initialize. This could be due to a network issue or missing configuration.</p>
                  <button onClick={() => window.location.reload()} className="p-button">Retry</button>
              </div>
          </div>
      );
  }

  return <Context.Provider value={{ authService }}>{children}</Context.Provider>;
};

export const Auth0UserProvider = ({ children }: { children: ReactNode }) => {
  const domain = import.meta.env.VITE_AUTH0_DOMAIN;
  const clientId = import.meta.env.VITE_AUTH0_CLIENT_ID;
  const audience = import.meta.env.VITE_AUTH0_AUDIENCE;

  if (!domain || !clientId || domain.includes('your-tenant')) {
      return (
          <div style={{ padding: '20px', color: 'red', border: '1px solid red' }}>
            <h2>Auth0 Configuration Missing</h2>
            <p>Please configure <code>VITE_AUTH0_DOMAIN</code> and <code>VITE_AUTH0_CLIENT_ID</code> in your <code>.env</code> file.</p>
          </div>
      );
  }

  return (
    <Auth0Provider
      domain={domain}
      clientId={clientId}
      authorizationParams={{
        redirect_uri: window.location.origin,
        audience: audience,
        scope: "openid profile email"
      }}
    >
      <Auth0Bridge>{children}</Auth0Bridge>
    </Auth0Provider>
  );
};
