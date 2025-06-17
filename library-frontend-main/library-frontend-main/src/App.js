import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import AuthPage from './AuthPage';
import SuperAdminPage from './SuperAdminPage';
import UsersPage from './UsersPage';
import BooksPage from './BooksPage';
import DashboardPage from './DashboardPage';
import AdminDashboard from './AdminDashboard';
import AdminBooks from './AdminBooks';
import LibrarianDashboard from './LibrarianDashboard';
import LibrarianBooks from './LibrarianBooks';
import LibrarianLoans from './LibrarianLoans';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LibrarianDashboard />} />
        <Route path="/login" element={<AuthPage />} />
        <Route path="/users" element={<UsersPage />} />
        <Route path="/branches" element={<SuperAdminPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/books" element={<BooksPage />} />

        <Route path="/admindashboard" element={<AdminDashboard />} />
        <Route path="/adminbooks" element={<AdminBooks />} />

        <Route path="/librariandashboard" element={<LibrarianDashboard />} />
        <Route path="/librarianbooks" element={<LibrarianBooks />} />
        <Route path="/librarianloans" element={<LibrarianLoans />} />
      </Routes>
    </Router>
  );
}

export default App;
