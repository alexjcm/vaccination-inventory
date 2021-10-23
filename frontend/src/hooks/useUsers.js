import {useState, useEffect} from 'react';

import usersService from '../services/users';

export default function useUsers({token}) {
  const [loading, setLoading] = useState(false);
  const [users, setUsers] = useState(null);

  useEffect(() => {
    setLoading(true);
    usersService({token})
      .then((data) => {
        setLoading(false);
        setUsers(data);
        console.log(data);
      })
      .catch((err) => {
        setLoading(false);
        console.error(err);
      });
  }, [setUsers]);

  return {loading, users};
}
