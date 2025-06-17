// BookSearchPage.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './BookSearchPage.css';

const booksData = [
  { id: 1, title: 'Steve Jobs', author: 'Walter Isaacson', genre: 'Biography', available: true },
  { id: 2, title: 'Radical', author: 'David Platt', genre: 'Religion', available: false },
  { id: 3, title: "Ender's Game", author: 'Orson Scott Card', genre: 'Science Fiction', available: true },
  { id: 4, title: 'The Hobbit', author: 'J.R.R. Tolkien', genre: 'Fantasy', available: true },
  { id: 5, title: 'The Coral Island', author: 'R.M. Ballantyne', genre: 'Adventure', available: true },
];

const BookSearchPage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [genreFilter, setGenreFilter] = useState('');
  const navigate = useNavigate();

  const filteredBooks = booksData.filter((book) => {
    const matchesSearch =
      book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.author.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesGenre = genreFilter ? book.genre === genreFilter : true;
    return matchesSearch && matchesGenre;
  });

  const handleBookClick = (bookId) => {
    navigate(`/books/${bookId}`);
  };

  const handleLogout = () => {
    navigate('/login');
  };

  return (
    <div className="page">
      <div className="header-bar">
        <h1 className="title">Explore Books</h1>
        <button className="logout-btn" onClick={handleLogout}>Log Out</button>
      </div>

      <div className="filters">
        <input
          type="text"
          placeholder="🔍 Search by title or author..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />
        <select
          value={genreFilter}
          onChange={(e) => setGenreFilter(e.target.value)}
          className="select-input"
        >
          <option value="">All Genres</option>
          <option value="Biography">Biography</option>
          <option value="Religion">Religion</option>
          <option value="Science Fiction">Science Fiction</option>
          <option value="Fantasy">Fantasy</option>
          <option value="Adventure">Adventure</option>
        </select>
      </div>

      <div className="book-list">
        {filteredBooks.map((book) => (
          <div key={book.id} className="book-card" onClick={() => handleBookClick(book.id)}>
            <div className="book-cover">📖</div>
            <h3 className="book-title">{book.title}</h3>
            <p className="book-author">by {book.author}</p>
            <p className="book-genre">🎨 {book.genre}</p>
            <p className={`book-status ${book.available ? 'available' : 'unavailable'}`}>
              {book.available ? '✅ Available' : '❌ Unavailable'}
            </p>
          </div>
        ))}
        {filteredBooks.length === 0 && <p className="no-books">No books found.</p>}
      </div>
    </div>
  );
};

export default BookSearchPage;
