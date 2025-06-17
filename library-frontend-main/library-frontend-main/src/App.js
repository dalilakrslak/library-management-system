import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import AuthPage from './AuthPage';
import SuperAdminPage from './SuperAdminPage';
import UsersPage from './UsersPage';
import BooksPage from './BooksPage';
import DashboardPage from './DashboardPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SuperAdminPage />} />
        <Route path="/login" element={<AuthPage />} />
        <Route path="/users" element={<UsersPage />} />
        <Route path="/branches" element={<SuperAdminPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />

        <Route path="/books" element={<BooksPage />} />
      </Routes>
    </Router>
  );
}

export default App;
