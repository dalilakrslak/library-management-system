import React, { useState, useEffect } from 'react';
import './SuperAdminPage.css';
import './LibrarianLoans.css';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { logoutUser } from './AuthService';
import { ROUTES } from './utils/config';

const LibrarianLoans = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [showReturnConfirm, setShowReturnConfirm] = useState(false);
  const [loanToReturn, setLoanToReturn] = useState(null);
  const [activeTab, setActiveTab] = useState('borrowed');
  const [loans, setLoans] = useState([]);
  const [overdue, setOverdue] = useState([]);

  const handleLogout = () => logoutUser(navigate);

useEffect(() => {
  const token = localStorage.getItem('token');

  const fetchData = async () => {
    try {
      const loanRes = await fetch(ROUTES.loan, {
        headers: { Authorization: `Bearer ${token}` }
      });
      const loanData = await loanRes.json();

      const userRes = await fetch(ROUTES.users, {
        headers: { Authorization: `Bearer ${token}` }
      });
      const users = await userRes.json();

      const versionRes = await fetch(ROUTES.bookVersions, {
        headers: { Authorization: `Bearer ${token}` }
      });
      const versions = await versionRes.json();

      const bookRes = await fetch(ROUTES.books, {
        headers: { Authorization: `Bearer ${token}` }
      });
      const books = await bookRes.json();

      const enrichedLoans = loanData.map(loan => {
        const user = users.find(u => u.id === loan.userId);
        const fullName = user ? `${user.firstName} ${user.lastName}` : 'Unknown';

        const version = versions.find(v => v.isbn === loan.bookVersion);
        const book = version ? books.find(b => b.id === version.bookId) : null;
        const title = book ? book.title : 'Unknown';

        return {
          ...loan,
          user: fullName,
          title,
          isbn: loan.bookVersion // samo ako ti još negdje treba
        };
      });

      const today = new Date().toISOString().split('T')[0];

      setLoans(enrichedLoans.filter(l => !l.returnDate));
      setOverdue(
        enrichedLoans
          .filter(l => !l.returnDate && l.dueDate < today)
          .map(l => ({
            id: l.id,
            user: l.user,
            title: l.title,
            dueDate: l.dueDate,
            daysLate: Math.floor(
              (new Date(today) - new Date(l.dueDate)) / (1000 * 60 * 60 * 24)
            )
          }))
      );
    } catch (error) {
      console.error('Error fetching or processing loans:', error);
    }
  };

  fetchData();
}, []);


  const confirmReturn = (loan) => {
    setLoanToReturn(loan);
    setShowReturnConfirm(true);
  };

  const cancelReturn = () => {
    setShowReturnConfirm(false);
    setLoanToReturn(null);
  };

    const proceedReturn = async () => {
    const token = localStorage.getItem('token');
    const today = new Date().toISOString().split('T')[0]; 

    try {
        const updatedLoan = {
        ...loanToReturn,
        returnDate: today
        };

        await fetch(`${ROUTES.loan}/${loanToReturn.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(updatedLoan)
        });

        setLoans(loans.filter(l => l.id !== loanToReturn.id));
        setOverdue(overdue.filter(l => l.id !== loanToReturn.id));
    } catch (error) {
        console.error('Error updating return date:', error);
    }

    cancelReturn();
    };


  return (
    <div className="admin-container">
      <aside className="sidebar">
        <h2 className="logo">📚 BookWorm</h2>
        <p className="role-label">Hello, <strong>Librarian</strong></p>
        <nav>
          <Link to="/librariandashboard" className={location.pathname === '/librariandashboard' ? 'active' : ''}>Dashboard</Link>
          <Link to="/librarianbooks" className={location.pathname === '/librarianbooks' ? 'active' : ''}>Books</Link>
          <Link to="/librarianloans" className={location.pathname === '/librarianloans' ? 'active' : ''}>Loans</Link>
        </nav>
        <div className="logout-section">
          <button onClick={handleLogout} className="logout-link">🚪 Logout</button>
        </div>
      </aside>

      <main className="main-content">
        <div className="header">
          <h1>Loans</h1>
        </div>

        <div className="tabs">
          <button className={activeTab === 'borrowed' ? 'active' : ''} onClick={() => setActiveTab('borrowed')}>Borrowed Books</button>
          <button className={activeTab === 'overdue' ? 'active' : ''} onClick={() => setActiveTab('overdue')}>Overdue Borrowers</button>
        </div>

        {activeTab === 'borrowed' ? (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>ISBN</th>
                <th>Title</th>
                <th>User</th>
                <th>Loan Date</th>
                <th>Due Date</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {loans.map(loan => (
                <tr key={loan.id}>
                  <td>{loan.id}</td>
                  <td>{loan.isbn}</td>
                  <td>{loan.title}</td>
                  <td>{loan.user}</td>
                  <td>{loan.loanDate}</td>
                  <td>{loan.dueDate}</td>
                  <td><span onClick={() => confirmReturn(loan)}>↩️</span></td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>User</th>
                <th>Title</th>
                <th>Due Date</th>
                <th>Days Late</th>
              </tr>
            </thead>
            <tbody>
              {overdue.map(item => (
                <tr key={item.id}>
                  <td>{item.id}</td>
                  <td>{item.user}</td>
                  <td>{item.title}</td>
                  <td>{item.dueDate}</td>
                  <td>{item.daysLate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}

        {showReturnConfirm && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>Confirm Return</h2>
              <p>Are you sure the user has returned the book?</p>
              <div className="modal-actions">
                <button onClick={proceedReturn}>Yes, Return</button>
                <button onClick={cancelReturn}>Cancel</button>
              </div>
            </div>
          </div>
        )}
      </main>
    </div>
  );
};

export default LibrarianLoans;
