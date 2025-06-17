// BookDetailsPage.jsx
import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './BookDetailsPage.css';

const booksData = [
  {
    id: 1,
    title: 'Steve Jobs',
    author: 'Walter Isaacson',
    description: 'Biography of Steve Jobs, the co-founder of Apple.',
    genre: 'Biography',
    rating: 4,
    cover: '📘',
    availableCopies: 5
  },
  {
    id: 2,
    title: 'Radical',
    author: 'David Platt',
    description: 'Call to radical Christian living.',
    genre: 'Religion',
    rating: 3,
    cover: '📕',
    availableCopies: 0
  },
  {
    id: 3,
    title: "Ender's Game",
    author: 'Orson Scott Card',
    description: 'Young boy trained to fight in an intergalactic war.',
    genre: 'Science Fiction',
    rating: 5,
    cover: '📗',
    availableCopies: 3
  },
  {
    id: 4,
    title: 'The Hobbit',
    author: 'J.R.R. Tolkien',
    description: 'Adventure of Bilbo Baggins in Middle-earth.',
    genre: 'Fantasy',
    rating: 5,
    cover: '📙',
    availableCopies: 2
  },
  {
    id: 5,
    title: 'The Coral Island',
    author: 'R.M. Ballantyne',
    description: 'Classic adventure novel set on a desert island.',
    genre: 'Adventure',
    rating: 4,
    cover: '📒',
    availableCopies: 1
  }
];

const BookDetailsPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [message, setMessage] = useState('');
  const book = booksData.find((b) => b.id === parseInt(id));

  if (!book) {
    return <p>Book not found.</p>;
  }

  const handleReserve = () => {
    if (book.availableCopies > 0) {
      setMessage(
        '✅ You have successfully reserved this book. Please pick it up within the next 3 working days at your library.'
      );
    } else {
      setMessage(
        'ℹ️ This book is currently unavailable. We have notified your librarian and you will be informed as soon as the book is returned.'
      );
    }
  };

  return (
    <div className="details-page">
      <div className="book-cover-large">{book.cover}</div>
      <div className="book-info">
        <h1>{book.title}</h1>
        <h2>{book.author}</h2>
        <p className="rating">{'★'.repeat(book.rating)}{'☆'.repeat(5 - book.rating)}</p>
        <p className="description">{book.description}</p>
        <p className="copies">
          Available copies: <strong>{book.availableCopies}</strong>
        </p>
        <div className="action-buttons">
          <button className="reserve-button" onClick={handleReserve}>
            RESERVE
          </button>
          <button className="back-button" onClick={() => navigate(-1)}>
            ← Back
          </button>
        </div>
        {message && <div className="reservation-message">{message}</div>}
      </div>
    </div>
  );
};

export default BookDetailsPage;