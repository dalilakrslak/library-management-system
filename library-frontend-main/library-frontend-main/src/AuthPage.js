import React, { useState } from 'react';
import './AuthPage.css';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>{isLogin ? 'Login' : 'Register'}</h2>
        <form>
          <input type="email" placeholder="Email" required />
          <input type="password" placeholder="Password" required />
          {!isLogin && <input type="text" placeholder="Full Name" required />}
          <button type="submit">{isLogin ? 'Login' : 'Register'}</button>
        </form>
        <p onClick={() => setIsLogin(!isLogin)}>
          {isLogin ? "Don't have an account? Register" : "Already have an account? Login"}
        </p>
      </div>
    </div>
  );
};

export default AuthPage;
