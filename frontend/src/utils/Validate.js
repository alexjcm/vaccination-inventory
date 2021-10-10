export const validateField = (username, password) => {
  if (username === '' || password === '') {
    return false;
  }

  return true;
};
