export interface LoginOptions {
  username?: string;
  password?: string;
}

export interface AuthService {
  providerName: string;
  loginLabel: string;
  loginIcon?: string;
  login(credentials: LoginOptions): Promise<string>;
  logout(): void;
  getToken: () => Promise<string | null>;
  isAuthenticated(): boolean;
  user?: any;
}
