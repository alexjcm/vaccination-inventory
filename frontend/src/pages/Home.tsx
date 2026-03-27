import { useContext, useEffect, useState } from 'react';
import Context from '../context/UserContext';
import { API_URL } from '../api/api';

interface Vaccine {
  id: number;
  name: string;
}

export default function Home() {
  const { authService } = useContext(Context);
  const [vaccines, setVaccines] = useState<Vaccine[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const isAuthenticated = authService?.isAuthenticated() ?? false;

  useEffect(() => {
    if (!authService || !isAuthenticated) {
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const token = await authService.getToken();

        const headers: HeadersInit = {
          'Content-Type': 'application/json',
          ...(token ? { Authorization: `Bearer ${token}` } : {}),
        };

        const response = await fetch(`${API_URL}/vaccines`, { headers });

        if (response.status === 401 || response.status === 403) {
          setError("No tienes permisos para ver estos datos.");
          return;
        }

        if (!response.ok) throw new Error('Failed to fetch data');

        const data = await response.json();
        setVaccines(data);
        setError(null);
      } catch (err: any) {
        console.error(err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [authService, isAuthenticated]);

  // ✅ Simplificado
  if (!isAuthenticated) {
    return (
      <>
        <header className="o-header">
          <h1>Vaccination Inventory</h1>
        </header>
        <div className="App-main">
          <div className="empty-state">
            <h2>Welcome to Vaccination Inventory</h2>
            <p>Please log in to view the secure inventory data.</p>
          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <header className="o-header">
        <h1>Vaccination Inventory</h1>
      </header>
      <div className="App-wrapper">
        <div className="App-main">
          {error ? (
            <div className="error-alert">{error}</div>
          ) : vaccines.length > 0 ? (
            <div className="inventory-grid">
              {vaccines.map(v => (
                <div key={v.id} className="inventory-card glass">
                  <h3>{v.name}</h3>
                  <p><strong>ID:</strong> {v.id}</p>
                </div>
              ))}
            </div>
          ) : (
            <div className="empty-state">No vaccination data available.</div>
          )}
        </div>
      </div>
    </>
  );
}
