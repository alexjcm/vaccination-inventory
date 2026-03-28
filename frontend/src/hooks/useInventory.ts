import { useState, useEffect, useCallback, useContext } from 'react';
import Context from '../context/UserContext';
import { API_URL } from '../api/api';

export interface Vaccine {
  id: number | null;
  name: string;
  type?: string;
}

export interface User {
  id: number | null;
  identCard: number | null;
  firstName: string;
  lastName: string;
  email: string;
  isVaccinated: boolean;
  numberOfDoses: number;
  vaccine: Vaccine | null;
}

export const emptyUser: User = {
  id: null,
  identCard: null,
  firstName: '',
  lastName: '',
  email: '',
  isVaccinated: false,
  numberOfDoses: 0,
  vaccine: null
};

export const emptyVaccine: Vaccine = {
  id: null,
  name: '',
  type: ''
};

/**
 * useInventory Hook
 * Provides state and CRUD actions for both users and vaccines.
 * Handles Auth0 token acquisition and centralized error reporting in English.
 */
export function useInventory() {
  const { authService } = useContext(Context);
  const [users, setUsers] = useState<User[]>([]);
  const [vaccines, setVaccines] = useState<Vaccine[]>([]);
  const [loading, setLoading] = useState(true);
  const isAuthenticated = authService?.isAuthenticated() ?? false;

  const fetchData = useCallback(async () => {
    if (!authService || !isAuthenticated) return;
    setLoading(true);
    try {
      const token = await authService.getToken();
      const headers = { 'Authorization': `Bearer ${token}` };
      
      const [uRes, vRes] = await Promise.all([
        fetch(`${API_URL}/users`, { headers }),
        fetch(`${API_URL}/vaccines`, { headers })
      ]);
      
      if (!uRes.ok || !vRes.ok) throw new Error('System initialization failed: Data unreachable');
      
      const [uData, vData] = await Promise.all([uRes.json(), vRes.json()]);
      setUsers(uData);
      setVaccines(vData);
    } catch (err: any) {
      console.error('Core synchronizer error:', err);
      throw err;
    } finally {
      setLoading(false);
    }
  }, [authService, isAuthenticated]);

  useEffect(() => {
    if (isAuthenticated) {
      fetchData();
    }
  }, [isAuthenticated, fetchData]);

  const saveUser = async (user: User) => {
    const token = await authService?.getToken();
    const response = await fetch(`${API_URL}/users`, {
      method: user.id ? 'PUT' : 'POST',
      headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
      body: JSON.stringify(user)
    });
    if (!response.ok) throw new Error('Data persistence layer failed: Operation rejected');
    await fetchData();
  };

  const deleteUser = async (id: number) => {
    const token = await authService?.getToken();
    const res = await fetch(`${API_URL}/users/${id}`, { 
      method: 'DELETE', 
      headers: { 'Authorization': `Bearer ${token}` } 
    });
    if (!res.ok) throw new Error('Delete operation failed');
    await fetchData();
  };

  const saveVaccine = async (vaccine: Vaccine) => {
    const token = await authService?.getToken();
    const response = await fetch(`${API_URL}/vaccines`, {
      method: vaccine.id ? 'PUT' : 'POST',
      headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
      body: JSON.stringify(vaccine)
    });
    if (!response.ok) throw new Error('Catalog update failed');
    await fetchData();
  };

  const deleteVaccine = async (id: number) => {
    const token = await authService?.getToken();
    const res = await fetch(`${API_URL}/vaccines/${id}`, { 
      method: 'DELETE', 
      headers: { 'Authorization': `Bearer ${token}` } 
    });
    if (!res.ok) throw new Error('Delete operation failed');
    await fetchData();
  };

  return { users, vaccines, loading, fetchData, saveUser, deleteUser, saveVaccine, deleteVaccine };
}
