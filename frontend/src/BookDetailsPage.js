import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './BookDetailsPage.css';
import { ROUTES } from './utils/config';
import { fetchAuthorMap } from './utils/LovData';

const BookDetailsPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [availableCopies, setAvailableCopies] = useState(0);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);
  const [authorMap, setAuthorMap] = useState({});
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [showAlreadyReservedModal, setShowAlreadyReservedModal] = useState(false);

  const fetchVersions = async () => {
    const token = localStorage.getItem('token');
    const currentLibraryId = localStorage.getItem('libraryId');

    try {
      const res = await fetch(`${ROUTES.books}/version/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (!res.ok) throw new Error('Failed to fetch book versions');
      const versions = await res.json();

      const available = versions.filter(v =>
        !v.isCheckedOut &&
        !v.isReserved &&
        String(v.libraryId) === currentLibraryId
      );

      setAvailableCopies(available.length);
    } catch (error) {
      console.error('Error fetching versions:', error);
    } finally {
      setLoading(false);
    }
  };


  useEffect(() => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem("userId");

    const fetchBookDetails = async () => {
      try {
        const res = await fetch(`${ROUTES.books}/${id}`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        if (!res.ok) throw new Error('Failed to fetch book details');
        const data = await res.json();
        setBook(data);
      } catch (error) {
        console.error('Error fetching book:', error);
      }
    };

    const loadAuthors = async () => {
      try {
        const map = await fetchAuthorMap();
        setAuthorMap(map);
      } catch (err) {
        console.error('Error loading author map:', err);
      }
    };

    fetchBookDetails();
    fetchVersions();
    loadAuthors();
  }, [id]);

  const handleReserve = async () => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');

    try {
      const versionRes = await fetch(`${ROUTES.books}/version/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (!versionRes.ok) throw new Error('Failed to fetch book versions');

      const versions = await versionRes.json();

      const currentLibraryId = localStorage.getItem('libraryId');
        const availableVersion = versions.find(v =>
          !v.isCheckedOut &&
          !v.isReserved &&
          String(v.libraryId) === currentLibraryId
        );

      if (!availableVersion) {
        setMessage('ℹ️ No available copies for reservation.');
        return;
      }

      const reservationRes = await fetch(`${ROUTES.reservation}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          userId: parseInt(userId),
          bookVersion: availableVersion.isbn,
          reservationDate: new Date().toISOString().split('T')[0]
        })
      });

      if (!reservationRes.ok) throw new Error('Reservation failed');
      await new Promise(res => setTimeout(res, 500));
      await fetchVersions();
      setMessage('✅ You have successfully reserved this book. Please pick it up within the next 3 working days at your library.');
    } catch (error) {
      console.error('Error during reservation:', error);
      setMessage('❌ Reservation failed. Please try again.');
    }
  };


  const checkIfAlreadyReserved = async () => {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const currentLibraryId = localStorage.getItem('libraryId');

    try {
      const versionsRes = await fetch(`${ROUTES.books}/version/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (!versionsRes.ok) throw new Error('Failed to fetch book versions');
      const versions = await versionsRes.json();

      const availableVersions = versions.filter(v =>
        !v.isCheckedOut &&
        !v.isReserved &&
        String(v.libraryId) === currentLibraryId
      );

      if (availableVersions.length === 0) {
        setMessage('ℹ️ No available copies for reservation.');
        return;
      }

      const isbnsInThisLibrary = versions
        .filter(v => String(v.libraryId) === currentLibraryId)
        .map(v => v.isbn);

      const reservationsRes = await fetch(`${ROUTES.reservation}/search?userId=${userId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });

      if (!reservationsRes.ok && reservationsRes.status !== 204)
        throw new Error('Failed to fetch reservations');

      const reservations = reservationsRes.status === 204 ? [] : await reservationsRes.json();

      const alreadyReserved = reservations.some(r =>
        r?.bookVersion && isbnsInThisLibrary.includes(r.bookVersion)
      );

      if (alreadyReserved) {
        setShowAlreadyReservedModal(true);
      } else {
        setShowConfirmModal(true);
      }
    } catch (error) {
      console.error('Error checking reservation status:', error);
      setMessage('⚠️ Failed to check reservation status.');
    }
  };



  if (loading) return <p>Loading...</p>;
  if (!book) return <p>Book not found.</p>;

  return (
    <div className="details-page">
      <div className="book-cover-large">📖</div>
      <div className="book-info">
        <h1>{book.title}</h1>
        <h2>{authorMap[book.authorId] || 'Unknown Author'}</h2>
        <p className="rating">{'★'.repeat(book.rating || 3)}{'☆'.repeat(5 - (book.rating || 3))}</p>
        <p className="description">{book.description}</p>
        <p className="copies">
          Available copies: <strong>{availableCopies}</strong>
        </p>
        <div className="action-buttons">
          <button className="reserve-button" onClick={() => {checkIfAlreadyReserved();}}>
            RESERVE
          </button>
          <button className="back-button" onClick={() => navigate(-1)}>
            ← Back
          </button>
        </div>
        {message && <div className="reservation-message">{message}</div>}
      </div>
      {showConfirmModal && (
  <div className="modal-overlay">
    <div className="modal-content">
      <p>Are you sure you want to reserve this book?</p>
      <div className="modal-buttons">
        <button onClick={() => {
          setShowConfirmModal(false);
          handleReserve();
        }}>Yes</button>
        <button onClick={() => setShowConfirmModal(false)}>Cancel</button>
      </div>
    </div>
  </div>
)}

{showAlreadyReservedModal && (
  <div className="modal-overlay">
    <div className="modal-content">
      <p>You have already reserved this book. Cannot reserve it again.</p>
      <div className="modal-buttons">
        <button onClick={() => setShowAlreadyReservedModal(false)}>OK</button>
      </div>
    </div>
  </div>
)}

    </div>
  );
};

export default BookDetailsPage;
