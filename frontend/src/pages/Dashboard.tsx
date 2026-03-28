import { useContext } from 'react';
import Context from '../context/UserContext';
import LandingView from '../components/LandingView';
import DashboardView from '../components/DashboardView';

export default function Dashboard() {
  const { authService } = useContext(Context);
  const isAuthenticated = authService?.isAuthenticated() ?? false;

  return (
    <div className="App-wrapper minimalist-ui">
      {isAuthenticated ? <DashboardView /> : <LandingView />}
    </div>
  );
}
