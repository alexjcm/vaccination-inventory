import {API_URL} from '../api/api';

export default function users({token}) {
  const options = {
    method: 'GET',
    headers: {
      Authorization: 'Bearer ' + token,
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  };

  return fetch(`${API_URL}/users`, options)
    .then((res) => {
      if (!res.ok) throw new Error('Response is NOT ok');
      return res.json();
    })
    .then((res) => {
      const {listAllUsers} = res;
      return listAllUsers;
    });
}
