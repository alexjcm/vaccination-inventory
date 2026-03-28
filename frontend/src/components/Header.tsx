import React, {useContext} from 'react';
import {Link} from 'wouter';
import Context from '../context/UserContext';

import './Header.css';

const CORPORATE_LOGO = "https://cdn-icons-png.flaticon.com/512/2859/2859706.png";

export default function Header() {
  const { authService } = useContext(Context);
  const isLogged = authService?.isAuthenticated() ?? false;

  const handleLogout = (e: React.MouseEvent) => {
    e.preventDefault();
    authService?.logout();
  };

  const handleLogin = (e: React.MouseEvent) => {
    e.preventDefault();
    authService?.login();
  };

  return (
    <header className="corporate-header">
      <div className="corporate-container header-inner">
        <div className="header-left">
          <Link href="/">
            <div className="brand-wrapper">
              <img src={CORPORATE_LOGO} alt="MediStock" className="brand-logo" />
              <span className="brand-name font-brand font-bold text-navy">MediStock V.I.M.</span>
            </div>
          </Link>
        </div>
        
        <div className="header-right">
          {isLogged ? (
            <div className="user-profile">
              <button onClick={handleLogout} className="btn-corporate-outline btn-sm">
                Logout
              </button>
            </div>
          ) : (
            <div className="auth-actions">
              <button onClick={handleLogin} className="btn-corporate-primary btn-sm">
                Login to System
              </button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
