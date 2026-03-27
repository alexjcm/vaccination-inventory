import {useContext, useCallback, useState} from 'react';
import Context from '../context/UserContext';
import { LoginOptions } from '../security/types';

export default function useUser() {
  const {authService} = useContext(Context);
  const [state, setState] = useState({loading: false, error: false});

  const login = useCallback(
    (credentials: LoginOptions) => {
      if (!authService) throw new Error('AuthService is not provided');
      setState({loading: true, error: false});
      authService.login(credentials)
        .then(() => {
          setState({loading: false, error: false});
        })
        .catch((err) => {
          setState({loading: false, error: true});
          console.error(err);
        });
    },
    [authService]
  );

  const logout = useCallback(() => {
    if (authService) authService.logout();
  }, [authService]);

  return {
    isLogged: authService ? authService.isAuthenticated() : false,
    isLoginLoading: state.loading,
    hasLoginError: state.error,
    login,
    logout,
    authService,
    user: authService?.user,
  };
}
