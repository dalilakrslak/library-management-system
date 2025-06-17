import React, { useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';

const BooksPage = () => {
  const location = useLocation();

  const [books, setBooks] = useState([
    { id: 1, isbn: '978-0544003415', title: 'The Hobbit', author: 'J.R.R. Tolkien', genre: 'Fantasy', available: 'Yes' },
    { id: 2, isbn: '978-0061120084', title: 'To Kill a Mockingbird', author: 'Harper Lee', genre: 'Drama', available: 'No' }
  ]);

  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [formData, setFormData] = useState({ isbn: '', title: '', author: '', genre: '', available: '' });

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

  const [showConfirm, setShowConfirm] = useState(false);
  const [bookToDelete, setBookToDelete] = useState(null);

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

  return (
    <div className="admin-container">
      <aside className="sidebar">
        <h2 className="logo">📚 BookWorm</h2>
        <p className="role-label">Hello, <strong>Superadmin</strong></p>
        <nav>
          <Link to="/dashboard" className={location.pathname === '/dashboard' ? 'active' : ''}>Dashboard</Link>
          <Link to="/books" className={location.pathname === '/books' ? 'active' : ''}>Books</Link>
          <Link to="/branches" className={location.pathname === '/branches' ? 'active' : ''}>Branches</Link>
          <Link to="/users" className={location.pathname === '/users' ? 'active' : ''}>Users</Link>
        </nav>
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
                  <span onClick={() => openModal(book)}>✏️</span>{' '}
                  <span onClick={() => confirmDelete(book.id)}>🗑️</span>
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

export default BooksPage;
