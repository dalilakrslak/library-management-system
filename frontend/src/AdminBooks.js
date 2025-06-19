import React, { useState, useEffect } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { logoutUser } from './AuthService';
import { ROUTES } from './utils/config';
import Select from 'react-select';

const AdminBooks = () => {
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

        setBooks(formatted);
        fetchAllBooks();
      } catch (err) {
        console.error('Error fetching admin books:', err);
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
      } 
      else {
        
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


  const [showConfirm, setShowConfirm] = useState(false);
  const [bookToDelete, setBookToDelete] = useState(null);

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

  return (
    <div className="admin-container">
      <aside className="sidebar">
        <h2 className="logo">📚 BookWorm</h2>
        <p className="role-label">Hello, <strong>Admin</strong></p>
        <nav>
          <Link to="/admindashboard" className={location.pathname === '/admindashboard' ? 'active' : ''}>Dashboard</Link>
          <Link to="/adminbooks" className={location.pathname === '/adminbooks' ? 'active' : ''}>Books</Link>
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
                  <span onClick={() => confirmDelete(book.isbn)}>🗑️</span>
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
                  name="bookId"
                  options={bookOptions}
                  value={bookOptions.find(opt => opt.value === formData.bookId)}
                  onChange={selected => setFormData({ ...formData, bookId: selected.value })}
                  placeholder="Select or search book"
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
      </main>
    </div>
  );
};

export default AdminBooks;
