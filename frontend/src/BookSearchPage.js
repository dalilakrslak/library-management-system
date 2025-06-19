// BookSearchPage.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './BookSearchPage.css';
import { ROUTES } from './utils/config';
import { logoutUser } from './AuthService';

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
  const [genres, setGenres] = useState([]);
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBooks = async () => {
      const token = localStorage.getItem('token');

      try {
        const res = await fetch(ROUTES.books, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!res.ok) throw new Error('Failed to fetch books');

        const data = await res.json();

        const booksWithAvailability = await Promise.all(
          data.map(async (book) => {
            const available = await checkAvailability(book.id);
            return { ...book, available };
          })
        );

        setBooks(booksWithAvailability);
      } catch (error) {
        console.error('Error loading books:', error);
      }
    };

    fetchBooks();
  }, []);

  useEffect(() => {
    const fetchGenres = async () => {
      const token = localStorage.getItem('token');

      try {
        const res = await fetch(ROUTES.genres, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!res.ok) throw new Error('Failed to fetch genres');

        const data = await res.json();
        setGenres(data);
      } catch (error) {
        console.error('Error fetching genres:', error);
      }
    };

    fetchGenres();
  }, []);

  useEffect(() => {
    const fetchAuthors = async () => {
      const token = localStorage.getItem('token');

      try {
        const res = await fetch(ROUTES.authors, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!res.ok) throw new Error('Failed to fetch authors');

        const data = await res.json();
        setAuthors(data);
      } catch (error) {
        console.error('Error fetching authors:', error);
      }
    };

    fetchAuthors();
  }, []);

  const getGenreName = (genreId) => {
    const genre = genres.find(g => g.id === genreId);
    return genre ? genre.name : 'Unknown Genre';
  };

  const filteredBooks = books.filter((book) => {
    const matchesSearch =
      book.title?.toLowerCase().includes(searchTerm.toLowerCase()) ||
      book.author?.toLowerCase().includes(searchTerm.toLowerCase());
      const matchesGenre = genreFilter ? getGenreName(book.genreId) === genreFilter : true;
    return matchesSearch && matchesGenre;
  });


  const getAuthorName = (authorId) => {
    const author = authors.find(a => a.id === authorId);
    return author ? `${author.firstName} ${author.lastName}` : 'Unknown Author';
  };

  const handleBookClick = (bookId) => {
    navigate(`/books/${bookId}`);
  };

  const handleLogout = () => {
    logoutUser(navigate);
  };

  const checkAvailability = async (bookId) => {
    const token = localStorage.getItem('token');

    try {
      const res = await fetch(`${ROUTES.books}/version/${bookId}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (!res.ok) throw new Error('Failed to fetch versions');

      const versions = await res.json();
      return versions.some(v => !v.isCheckedOut && !v.isReserved);
    } catch (error) {
      console.error(`Error checking availability for book ${bookId}:`, error);
      return false;
    }
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
        {genres.map((genre) => (
          <option key={genre.id} value={genre.name}>
            {genre.name}
          </option>
        ))}

        </select>
      </div>

      <div className="book-list">
        {filteredBooks.map((book) => (
          <div key={book.id} className="book-card" onClick={() => handleBookClick(book.id)}>
            <div className="book-cover">📖</div>
            <h3 className="book-title">{book.title}</h3>
            <p className="book-author">by {getAuthorName(book.authorId)}</p>
            <p className="book-genre">🎨 {getGenreName(book.genreId)}</p>
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
