import Login from '../components/Login';

export default function LoginPage() {
  return (
    <div className="login-page-container" style={{ 
      display: 'flex', 
      flexDirection: 'column', 
      alignItems: 'center', 
      justifyContent: 'center',
      minHeight: '60vh',
      padding: '2rem'
    }}>
      <h2 style={{ marginBottom: '2rem', color: '#9933ff' }}>Auth0 Authentication</h2>
      <Login />
    </div>
  );
}
