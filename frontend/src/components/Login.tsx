import React, {useEffect} from 'react';
import {useLocation} from 'wouter';
import useUser from '../hooks/useUser';
import './Login.css';
import {Button} from 'primereact/button';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

interface LoginProps {
  onLogin?: () => void;
}

export default function Login({onLogin}: LoginProps) {
  const [, navigate] = useLocation();
  const {isLoginLoading, hasLoginError, login, isLogged, authService} = useUser();

  useEffect(() => {
    if (isLogged) {
      navigate('/');
      onLogin && onLogin();
    }
  }, [isLogged, navigate, onLogin]);

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    console.log('Login button clicked, calling authService.login()');
    login({}); // LoginOptions is optional or ignored by Auth0Provider
  };

  if (isLoginLoading || !authService) {
    return <div className="login-container" style={{ textAlign: 'center', padding: '50px' }}>
      <strong>Loading...</strong>
    </div>;
  }

  return (
    <div className="login-container" style={{ textAlign: 'center', padding: '50px' }}>
      {!isLogged && (
        <div className="provider-login">
          <h3>Welcome!</h3>
          <p>Please log in using {authService.providerName} to continue.</p>
          <Button 
            label={authService.loginLabel} 
            icon={authService.loginIcon}
            onClick={handleSubmit}
            className="p-button-lg" 
          />
        </div>
      )}
      {hasLoginError && <strong>Error authenticating with {authService.providerName}</strong>}
    </div>
  );
}
