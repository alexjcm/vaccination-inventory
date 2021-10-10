import React, {useState} from 'react';
import {useLocation} from 'wouter';
import useUser from '../hooks/useUser';
import {useEffect} from 'react';
import {validateField} from '../utils/Validate';
import './Login.css';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';
//import {Message} from 'primereact/message';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

export default function Login({onLogin}) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [, navigate] = useLocation();
  const {isLoginLoading, hasLoginError, login, isLogged} = useUser();

  useEffect(() => {
    if (isLogged) {
      navigate('/');
      onLogin && onLogin();
    }
  }, [isLogged, navigate, onLogin]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateField(username, password)) {
      console.warn("Campos incompletos");
      return;
    }
    login({username, password});
  };

  return (
    <>
      {isLoginLoading && <strong>Checking credentials...</strong>}
      {!isLoginLoading && (
        <form className="form" onSubmit={handleSubmit}>
          <label>
            Username
            <InputText
              onChange={(e) => setUsername(e.target.value)}
              value={username}
              placeholder="Username"
            />
          </label>
          <label>
            Password
            <InputText
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
              placeholder="Password"
            />
          </label>
          <Button label="Login" className="p-mt-2" />
        </form>
      )}
      {hasLoginError && <strong>Credentials are invalid</strong>}
    </>
  );
}
