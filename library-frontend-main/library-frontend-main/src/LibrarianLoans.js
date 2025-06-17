import React, { useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation, useNavigate } from 'react-router-dom';

const LibrarianLoans = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [showReturnConfirm, setShowReturnConfirm] = useState(false);
    const [loanToReturn, setLoanToReturn] = useState(null);


    const handleLogout = () => {
        localStorage.removeItem("token");
        sessionStorage.clear();
        navigate("/");
    };

    const [loans, setLoans] = useState([
        { id: 1, isbn: '978-0544003415', title: 'The Hobbit', user: 'John Doe', loanDate: '2025-06-01', dueDate: '2025-06-20' },
        { id: 2, isbn: '978-0061120084', title: 'To Kill a Mockingbird', user: 'Jane Smith', loanDate: '2025-06-10', dueDate: '2025-06-30' }
    ]);

    /*const returnBook = (id) => {
        setLoans(loans.filter(loan => loan.id !== id));
    };*/

    const confirmReturn = (loan) => {
        setLoanToReturn(loan);
        setShowReturnConfirm(true);
    };

    const cancelReturn = () => {
        setShowReturnConfirm(false);
        setLoanToReturn(null);
    };

    const proceedReturn = () => {
        setLoans(loans.filter(l => l.id !== loanToReturn.id)); // uklanja iz liste
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
                    <h1>Loaned Books</h1>
                </div>

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
