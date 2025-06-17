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
import BookSearchPage from './BookSearchPage';
import BookDetailsPage from './BookDetailsPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<AuthPage />} />
        <Route path="/superadminusers" element={<UsersPage />} />
        <Route path="/superadminbranches" element={<SuperAdminPage />} />
        <Route path="/superadmindashboard" element={<DashboardPage />} />
        <Route path="/superadminbooks" element={<BooksPage />} />
        <Route path="/booksearch" element={<BookSearchPage />} />
        <Route path="/books/:id" element={<BookDetailsPage />} />
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
