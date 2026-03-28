import Login from '../components/Login';
import Header from '../components/Header';

export default function LoginPage() {
  return (
    <div className="login-page-wrapper minimalist-ui flex flex-column" style={{ minHeight: '100vh', background: 'var(--slate-50)' }}>
      <Header />
      <div className="flex-grow-1 flex align-items-center justify-content-center p-4">
        <div style={{ maxWidth: '480px', width: '100%' }}>
            <Login />
            <div className="text-center mt-6">
                <p className="text-xs text-slate-400 font-medium uppercase tracking-widest opacity-60">
                    Trusted Medical Supply Chain Network
                </p>
                <div className="flex justify-content-center gap-3 mt-4 opacity-30">
                    <i className="pi pi-verified text-xl"></i>
                    <i className="pi pi-lock text-xl"></i>
                    <i className="pi pi-globe text-xl"></i>
                </div>
            </div>
        </div>
      </div>
    </div>
  );
}
