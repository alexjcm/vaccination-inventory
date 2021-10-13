import {API_URL} from '../api/api';

export default function login({username, password}) {
  const options = {
    method: 'POST',
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    //credentials: 'include',
    body: new URLSearchParams({
      username,
      password,
    }),
  };

  return fetch(`${API_URL}/authenticate`, options)
    .then((res) => {
      if (!res.ok) throw new Error('Response is NOT ok');
      return res.json();
    })
    .then((res) => {
      const {token} = res;
      return token;
    });
}
