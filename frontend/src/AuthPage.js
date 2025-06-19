import React, { useState } from 'react';
import './AuthPage.css';
import { login } from './AuthService';
import { useNavigate } from 'react-router-dom';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [fullName, setFullName] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isLogin) {
      const result = await login(email, password);
      if (!result.success) {
        setError('Invalid credentials');
      } 
      else {
        const role = result.role;
        if (role === 'SUPERADMIN') {
          navigate('/superadmindashboard');
        } else if (role === 'ADMIN') {
          navigate('/admindashboard');
        } else if (role === 'LIBRARIAN') {
          navigate('/librariandashboard');
        } else {
          navigate('/booksearch');
        }
      }
    } else {
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>{isLogin ? 'Login' : 'Register'}</h2>
        <form onSubmit={handleSubmit}>
          <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
          <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required />
          {!isLogin && <input type="text" placeholder="Full Name" value={fullName} onChange={e => setFullName(e.target.value)} required />}
          <button type="submit">{isLogin ? 'Login' : 'Register'}</button>
        </form>
        {error && <p className="error-text">{error}</p>}
        <p onClick={() => setIsLogin(!isLogin)}>
          {isLogin ? "Don't have an account? Register" : "Already have an account? Login"}
        </p>
      </div>
    </div>
  );
};

export default AuthPage;
