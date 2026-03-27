import React, {ReactNode, useMemo} from 'react';
import { AuthService } from '../security/types';

interface UserContextValue {
  authService: AuthService | null;
}

const Context = React.createContext<UserContextValue>({
  authService: null,
});

interface UserContextProviderProps {
  children: ReactNode;
  authService: AuthService;
}

export function UserContextProvider({children, authService}: UserContextProviderProps) {
  const value = useMemo(() => ({ authService }), [authService]);

  return (
    <Context.Provider value={value}>{children}</Context.Provider>
  );
}

export default Context;
