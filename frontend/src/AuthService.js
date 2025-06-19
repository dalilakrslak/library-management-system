import { ROUTES } from './utils/config';

export const login = async (email, password) => {
  try {
    const res = await fetch(ROUTES.login, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    if (!res.ok) throw new Error('Login failed');

    const data = await res.json();
    localStorage.setItem('token', data.token);
    localStorage.setItem('refreshToken', data.refreshToken);
    localStorage.setItem('userId', data.id);
    localStorage.setItem('firstName', data.firstName);
    localStorage.setItem('lastName', data.lastName);
    localStorage.setItem('email', data.email);
    localStorage.setItem('role', data.role);
    localStorage.setItem('libraryId', data.libraryId || '');
    localStorage.setItem('libraryName', data.libraryName || '');

    return { success: true, role: data.role };
  } catch (err) {
    console.error('Login error:', err);
    return { success: false, message: err.message };
  }
};

export const logoutUser = (navigate) => {
  localStorage.clear();
  sessionStorage.clear();
  navigate("/");
};
