import React, { useState, useEffect } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { logoutUser } from './AuthService';
import { ROUTES } from './utils/config';
import Select from 'react-select';

const LibrarianBooks = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logoutUser(navigate);
  };

  const [books, setBooks] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [formData, setFormData] = useState({ isbn: '', bookId: null });
  const [bookOptions, setBookOptions] = useState([]);
  const [userOptions, setUserOptions] = useState([]);

  const [showConfirm, setShowConfirm] = useState(false);
  const [bookToDelete, setBookToDelete] = useState(null);

  const [showLoanModal, setShowLoanModal] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [loanData, setLoanData] = useState({ userId: null, loanDate: '', dueDate: '' });

  useEffect(() => {
    const fetchBooks = async () => {
      const token = localStorage.getItem('token');
      const libraryId = localStorage.getItem('libraryId');

      try {
        const res = await fetch(ROUTES.booksByLibrary(libraryId), {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!res.ok) throw new Error('Failed to fetch books');

        const data = await res.json();

        const formatted = data.map(book => ({
          id: book.bookId,
          isbn: book.isbn,
          title: book.title,
          author: book.authorFullName,
          genre: book.genreName,
          available: !book.isCheckedOut && !book.isReserved ? 'Yes' : 'No'
        }));

        setBooks(formatted);
        fetchAllBooks();
        fetchUsers();
      } catch (err) {
        console.error('Error fetching librarian books:', err);
      }
    };

    const fetchAllBooks = async () => {
      const token = localStorage.getItem('token');
      try {
        const res = await fetch(ROUTES.books, {
          headers: { Authorization: `Bearer ${token}` }
        });
        const data = await res.json();

        const options = data.map(book => ({
          value: book.id,
          label: book.title
        }));

        setBookOptions(options);
      } catch (error) {
        console.error('Error loading book options:', error);
      }
    };

    const fetchUsers = async () => {
      const token = localStorage.getItem('token');

      try {
        const res = await fetch(ROUTES.users, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!res.ok) throw new Error('Failed to fetch users');
        const data = await res.json();

        const filteredUsers = data
          .filter(user => user.roleId === 4)
          .map(user => ({
            value: user.id,
            label: `${user.firstName} ${user.lastName}`
          }));

        setUserOptions(filteredUsers);
      } catch (error) {
        console.error('Error loading user options:', error);
      }
    };

    fetchBooks();
  }, []);

  const openModal = (book = null) => {
    if (book) {
      const selectedOption = bookOptions.find(opt => opt.label === book.title);
      setEditingBook(book);
      setFormData({
        isbn: book.isbn,
        bookId: selectedOption ? selectedOption.value : null
      });
    } else {
      setEditingBook(null);
      setFormData({ isbn: '', bookId: null });
    }
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingBook(null);
  };

  const handleFormChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    const libraryId = localStorage.getItem('libraryId');

    const payload = {
      isbn: formData.isbn,
      bookId: formData.bookId,
      libraryId: parseInt(libraryId),
      isReserved: false,
      isCheckedOut: false
    };

    try {
      if (editingBook) {
        await fetch(`${ROUTES.bookVersions}/${editingBook.isbn}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        });
      } else {
        await fetch(`${ROUTES.bookVersions}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        });
      }
      window.location.reload();
    } catch (err) {
      console.error('Error saving book version:', err);
    }

    closeModal();
  };

  const confirmDelete = (isbn) => {
    setBookToDelete(isbn);
    setShowConfirm(true);
  };

  const cancelDelete = () => {
    setShowConfirm(false);
    setBookToDelete(null);
  };

  const proceedDelete = async () => {
    const token = localStorage.getItem('token');

    try {
      await fetch(`${ROUTES.bookVersions}/${bookToDelete}`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      setBooks(books.filter(b => b.isbn !== bookToDelete));
    } catch (err) {
      console.error('Error deleting book version:', err);
    }

    cancelDelete();
  };

  const openLoanModal = (book) => {
    if (book.available === 'No') {
      alert('This book is currently not available for loan.');
      return;
    }
    setSelectedBook(book);
    setLoanData({ userId: null, loanDate: '', dueDate: '' });
    setShowLoanModal(true);
  };

  const closeLoanModal = () => {
    setShowLoanModal(false);
    setSelectedBook(null);
  };

  const handleLoanChange = (e) => {
    setLoanData({ ...loanData, [e.target.name]: e.target.value });
  };

  const handleLoanUserChange = (selected) => {
    setLoanData({ ...loanData, userId: selected.value });
  };

  const handleLoanSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    const payload = {
      userId: loanData.userId,
      bookVersion: selectedBook.isbn,
      loanDate: loanData.loanDate,
      dueDate: loanData.dueDate,
      returnDate: null
    };

    try {
      await fetch(`${ROUTES.loan}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });
      closeLoanModal();
      window.location.reload();
    } catch (err) {
      console.error('Error creating loan:', err);
    }
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
              <tr key={book.isbn}>
                <td>{book.isbn}</td>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.genre}</td>
                <td>{book.available}</td>
                <td>
                  <span onClick={() => openModal(book)}>✏️</span>{' '}
                  <span onClick={() => confirmDelete(book.isbn)}>🗑️</span>{' '}
                  <span onClick={() => openLoanModal(book)}>📦</span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {showModal && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>{editingBook ? 'Edit Book' : 'Add Book'}</h2>
              <form onSubmit={handleFormSubmit}>
                <input
                  name="isbn"
                  type="text"
                  placeholder="ISBN"
                  value={formData.isbn}
                  onChange={handleFormChange}
                  required
                  disabled={!!editingBook}
                />
                <Select
                  className="select-field"
                  classNamePrefix="select"
                  name="user"
                  options={userOptions}
                  value={userOptions.find(opt => opt.value === loanData.user)}
                  onChange={selected => setLoanData({ ...loanData, user: selected.value })}
                  placeholder="Select user"
                  isSearchable
                />
                <div className="modal-actions">
                  <button type="submit">{editingBook ? 'Update' : 'Add'}</button>
                  <button type="button" onClick={closeModal}>Cancel</button>
                </div>
              </form>
            </div>
          </div>
        )}

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

        {showLoanModal && (
          <div className="modal-backdrop">
            <div className="modal">
              <h2>Loan Book: {selectedBook?.title}</h2>
              <form onSubmit={handleLoanSubmit}>
                <Select
                  className="select-field"
                  classNamePrefix="select"
                  name="userId"
                  options={userOptions}
                  value={userOptions.find(opt => opt.value === loanData.userId)}
                  onChange={handleLoanUserChange}
                  placeholder="Select user"
                  isSearchable
                />
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
