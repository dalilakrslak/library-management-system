import React, { useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation, useNavigate } from 'react-router-dom';

const LibrarianBooks = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    sessionStorage.clear();
    navigate("/");
  };

  const [books, setBooks] = useState([
    { id: 1, isbn: '978-0544003415', title: 'The Hobbit', author: 'J.R.R. Tolkien', genre: 'Fantasy', available: 'Yes' },
    { id: 2, isbn: '978-0061120084', title: 'To Kill a Mockingbird', author: 'Harper Lee', genre: 'Drama', available: 'No' }
  ]);

  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [formData, setFormData] = useState({ isbn: '', title: '', author: '', genre: '', available: '' });

  const [showConfirm, setShowConfirm] = useState(false);
  const [bookToDelete, setBookToDelete] = useState(null);

  const [showLoanModal, setShowLoanModal] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [loanData, setLoanData] = useState({ user: '', loanDate: '', dueDate: '' });

  const openModal = (book = null) => {
    setEditingBook(book);
    setFormData(book ? { ...book } : { isbn: '', title: '', author: '', genre: '', available: '' });
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingBook(null);
  };

  const handleFormChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    if (editingBook) {
      setBooks(books.map(b => b.id === editingBook.id ? { ...formData, id: editingBook.id } : b));
    } else {
      const newId = books.length ? Math.max(...books.map(b => b.id)) + 1 : 1;
      setBooks([...books, { ...formData, id: newId }]);
    }
    closeModal();
  };

  const confirmDelete = (id) => {
    setBookToDelete(id);
    setShowConfirm(true);
  };

  const cancelDelete = () => {
    setShowConfirm(false);
    setBookToDelete(null);
  };

  const proceedDelete = () => {
    setBooks(books.filter(b => b.id !== bookToDelete));
    cancelDelete();
  };

  const openLoanModal = (book) => {
    setSelectedBook(book);
    setLoanData({ user: '', loanDate: '', dueDate: '' });
    setShowLoanModal(true);
  };

  const closeLoanModal = () => {
    setShowLoanModal(false);
    setSelectedBook(null);
  };

  const handleLoanChange = (e) => {
    setLoanData({ ...loanData, [e.target.name]: e.target.value });
  };

  const handleLoanSubmit = (e) => {
    e.preventDefault();
    console.log("Loaned Book:", selectedBook, loanData);
    // Ovdje možeš napraviti API poziv
    closeLoanModal();
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
          <h1>Book Management</h1>
          <button className="add-btn" onClick={() => openModal()}>+ Add Book</button>
        </div>

        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>ISBN</th>
              <th>Title</th>
              <th>Author</th>
              <th>Genre</th>
              <th>Available</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {books.map(book => (
              <tr key={book.id}>
                <td>{book.id}</td>
                <td>{book.isbn}</td>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.genre}</td>
                <td>{book.available}</td>
                <td>
                  <span onClick={() => openModal(book)}>✏️</span>
                  <span onClick={() => confirmDelete(book.id)}>🗑️</span>
                  <span onClick={() => openLoanModal(book)}>📦</span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Add/Edit Book Modal */}
        {showModal && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>{editingBook ? 'Edit Book' : 'Add Book'}</h2>
              <form onSubmit={handleFormSubmit}>
                <input name="isbn" type="text" placeholder="ISBN" value={formData.isbn} onChange={handleFormChange} required />
                <input name="title" type="text" placeholder="Title" value={formData.title} onChange={handleFormChange} required />
                <input name="author" type="text" placeholder="Author" value={formData.author} onChange={handleFormChange} required />
                <input name="genre" type="text" placeholder="Genre" value={formData.genre} onChange={handleFormChange} required />
                <input name="available" type="text" placeholder="Yes/No" value={formData.available} onChange={handleFormChange} required />
                <div className="modal-actions">
                  <button type="submit">{editingBook ? 'Update' : 'Add'}</button>
                  <button type="button" onClick={closeModal}>Cancel</button>
                </div>
              </form>
            </div>
          </div>
        )}

        {/* Confirm Delete Modal */}
        {showConfirm && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>Confirm Deletion</h2>
              <p>Are you sure you want to delete this book?</p>
              <div className="modal-actions">
                <button onClick={proceedDelete}>Yes, Delete</button>
                <button onClick={cancelDelete}>Cancel</button>
              </div>
            </div>
          </div>
        )}

        {/* Loan Book Modal */}
        {showLoanModal && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>Loan Book: {selectedBook?.title}</h2>
              <form onSubmit={handleLoanSubmit}>
                <input name="user" type="text" placeholder="User ID or Name" value={loanData.user} onChange={handleLoanChange} required />
                <input name="loanDate" type="date" value={loanData.loanDate} onChange={handleLoanChange} required />
                <input name="dueDate" type="date" value={loanData.dueDate} onChange={handleLoanChange} required />
                <div className="modal-actions">
                  <button type="submit">Loan</button>
                  <button type="button" onClick={closeLoanModal}>Cancel</button>
                </div>
              </form>
            </div>
          </div>
        )}
      </main>
    </div>
  );
};

export default LibrarianBooks;
