import React, {useEffect} from 'react';
import {useLocation} from 'wouter';
import useUser from '../hooks/useUser';
import {Button} from 'primereact/button';

export default function Login() {
  const [, navigate] = useLocation();
  const {isLoginLoading, hasLoginError, login, isLogged, authService} = useUser();

  useEffect(() => {
    if (isLogged) {
      navigate('/');
    }
  }, [isLogged, navigate]);

  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    login({}); 
  };

  if (isLoginLoading || !authService) {
    return (
      <div className="flex flex-column align-items-center gap-4 py-8">
        <i className="pi pi-spin pi-spinner text-4xl text-navy opacity-20"></i>
        <span className="text-slate-400 font-medium">Establishing Secure Connection...</span>
      </div>
    );
  }

  return (
    <div className="login-card card-corporate flex flex-column align-items-center p-6 gap-4">
      {!isLogged && (
        <>
          <div className="flex flex-column align-items-center gap-2 mb-4">
            <h3 className="text-2xl font-bold text-navy m-0">Corporate Access</h3>
            <p className="text-slate-500 text-center text-sm">Please authenticate with your {authService.providerName} credentials to access the inventory.</p>
          </div>
          
          <Button 
            label={`Log in with ${authService.providerName}`} 
            icon="pi pi-shield"
            onClick={handleSubmit}
            className="btn-corporate-primary w-full py-3" 
          />
          
          <p className="text-xs text-slate-400 mt-4 text-center">
            By logging in, you agree to our Terms of Service and Privacy Policy for medical data management.
          </p>
        </>
      )}
      
      {hasLoginError && (
        <div className="p-3 bg-red-50 border-red-100 border-round w-full text-center">
            <span className="text-red-700 text-xs font-semibold">Authentication protocol failed. Please try again or contact IT support.</span>
        </div>
      )}
    </div>
  );
}
