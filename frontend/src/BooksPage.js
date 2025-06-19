import React, { useState, useEffect } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from './utils/config';
import { logoutUser } from './AuthService';
import { fetchAuthorMap, fetchGenreMap } from './utils/LovData';
import Select from 'react-select';

const BooksPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logoutUser(navigate);
  };

  const [books, setBooks] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [editingBook, setEditingBook] = useState(null);
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    genre: '',
    description: '',
    publicationYear: '',
    language: '',
    pageCount: '',
    authorId: '',
    genreId: ''
  });
  const [authorMap, setAuthorMap] = useState({});
  const [genreMap, setGenreMap] = useState({});

  const authorOptions = Object.entries(authorMap).map(([id, name]) => ({
    value: id,
    label: name
  }));

  const genreOptions = Object.entries(genreMap).map(([id, name]) => ({
    value: id,
    label: name
  }));

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');
      try {
        const [booksRes, authors, genres] = await Promise.all([
          fetch(ROUTES.books, {
            headers: { Authorization: `Bearer ${token}` }
          }),
          fetchAuthorMap(),
          fetchGenreMap()
        ]);

        if (!booksRes.ok) throw new Error('Failed to fetch books');
        const booksData = await booksRes.json();

        setBooks(booksData);
        setAuthorMap(authors);
        setGenreMap(genres);
      } catch (err) {
        console.error('Error fetching data:', err);
      }
    };

    fetchData();
  }, []);

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

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');

    const bookPayload = {
      title: formData.title,
      description: formData.description,
      publicationYear: parseInt(formData.publicationYear),
      language: formData.language,
      pageCount: parseInt(formData.pageCount),
      authorId: parseInt(formData.authorId),
      genreId: parseInt(formData.genreId),
    };

    try {
      if (editingBook) {
        const res = await fetch(`${ROUTES.books}/${editingBook.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(bookPayload)
        });
        if (!res.ok) throw new Error('Failed to update book');
      } else {
        const res = await fetch(ROUTES.books, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify(bookPayload)
        });
        if (!res.ok) throw new Error('Failed to create book');
      }
      window.location.reload();
    } catch (err) {
      console.error('Error saving book:', err);
    }
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

  const proceedDelete = async () => {
    const token = localStorage.getItem('token');
    try {
      const res = await fetch(`${ROUTES.books}/${bookToDelete}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` }
      });
      if (!res.ok) throw new Error('Failed to delete book');
      setBooks(books.filter(b => b.id !== bookToDelete));
      cancelDelete();
    } catch (err) {
      console.error('Error deleting book:', err);
    }
  };


  return (
    <div className="admin-container">
      <aside className="sidebar">
        <h2 className="logo">📚 BookWorm</h2>
        <p className="role-label">Hello, <strong>Superadmin</strong></p>
        <nav>
          <Link to="/superadmindashboard" className={location.pathname === '/superadmindashboard' ? 'active' : ''}>Dashboard</Link>
          <Link to="/superadminbooks" className={location.pathname === '/superadminbooks' ? 'active' : ''}>Books</Link>
          <Link to="/superadminbranches" className={location.pathname === '/superadminbranches' ? 'active' : ''}>Libraries</Link>
          <Link to="/superadminusers" className={location.pathname === '/superadminusers' ? 'active' : ''}>Users</Link>
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
              <th>Title</th>
              <th>Description</th>
              <th>Language</th>
              <th>Year</th>
              <th>Pages</th>
              <th>Author</th>
              <th>Genre</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {books.map(book => (
              <tr key={book.id}>
                <td>{book.id}</td>
                <td>{book.title}</td>
                <td>{book.description}</td>
                <td>{book.language}</td>
                <td>{book.publicationYear}</td>
                <td>{book.pageCount}</td>
                <td>{authorMap[book.authorId] || book.authorId}</td>
                <td>{genreMap[book.genreId] || book.genreId}</td>
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
                <input name="title" placeholder="Title" value={formData.title} onChange={handleFormChange} required />
                <input name="description" placeholder="Description" value={formData.description} onChange={handleFormChange} required />
                <input name="publicationYear" type="number" placeholder="Year" value={formData.publicationYear} onChange={handleFormChange} required />
                <input name="language" placeholder="Language" value={formData.language} onChange={handleFormChange} required />
                <input name="pageCount" type="number" placeholder="Page Count" value={formData.pageCount} onChange={handleFormChange} required />
                <Select
                  className="select-field"
                  classNamePrefix="select"
                  name="authorId"
                  options={authorOptions}
                  value={authorOptions.find(opt => opt.value === String(formData.authorId))}
                  onChange={selected => setFormData({ ...formData, authorId: selected.value })}
                  placeholder="Select or search author"
                  isSearchable
                />
                <Select
                  className="select-field"
                  classNamePrefix="select"
                  name="genreId"
                  options={genreOptions}
                  value={genreOptions.find(opt => opt.value === String(formData.genreId))}
                  onChange={selected => setFormData({ ...formData, genreId: selected.value })}
                  placeholder="Select or search genre"
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

export default BooksPage;
