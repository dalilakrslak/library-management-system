import React from 'react';
import './HomePage.css';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="homepage-container">
      <header className="homepage-header">
        <h1 className="logo">📚 BookWorm Library</h1>
        <nav>
          <Link to="/login" className="login-link">Login</Link>
        </nav>
      </header>

      <main className="hero">
        <h2>Welcome to BookWorm</h2>
        <p>Your digital gateway to a world of stories and knowledge.</p>
      </main>

      <footer className="homepage-footer">
        <p>&copy; {new Date().getFullYear()} BookWorm Library. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default HomePage;
