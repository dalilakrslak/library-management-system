import React, { useEffect, useState } from 'react';
import { ROUTES } from './utils/config';
import { useNavigate } from 'react-router-dom';
import { logoutUser } from './AuthService';
import './UserActivityPage.css';

const UserActivityPage = () => {
  const [reservations, setReservations] = useState([]);
  const [loans, setLoans] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');
      const userId = localStorage.getItem('userId'); // pretpostavka da čuvaš userId

      try {
        const [resR, resL] = await Promise.all([
          fetch(`${ROUTES.reservation}/search?userId=${userId}`, {
            headers: { Authorization: `Bearer ${token}` }
          }),
          fetch(`${ROUTES.loan}/search?userId=${userId}`, {
            headers: { Authorization: `Bearer ${token}` }
          })
        ]);

        if (!resR.ok || !resL.ok) throw new Error('Failed to load user activity');

        const rData = resR.status === 204 ? [] : await resR.json();
        const lData = resL.status === 204 ? [] : await resL.json();

        setReservations(rData || []);
        setLoans(lData || []);
      } catch (err) {
        console.error(err);
      }
    };

    fetchData();
  }, []);

    const handleLogout = () => {
      logoutUser(navigate);
    };

  return (
    <div className="page">
      <div className="header-bar">
        <h1 className="title">My Activity</h1>
        <div className="button-group">
          <button className="nav-btn" onClick={() => navigate('/booksearch')}>📚 Books</button>
          <button className="logout-btn" onClick={handleLogout}>Log Out</button>
        </div>
      </div>

      <section>
        <h2>📌 My Reservations</h2>
        {reservations.length === 0 ? <p>No reservations.</p> :
          <ul>
            {reservations.map(r => (
              <li key={r.id}>
                ISBN: <strong>{r.bookVersion}</strong> | Reserved on: <em>{r.reservationDate}</em>
              </li>
            ))}
          </ul>
        }
      </section>

      <section>
        <h2>📖 My Loans</h2>
        {loans.length === 0 ? <p>No active loans.</p> :
          <ul>
            {loans.map(l => (
              <li key={l.id}>
                ISBN: <strong>{l.bookVersion}</strong> | Loaned: <em>{l.loanDate}</em> | Due: <em>{l.dueDate}</em> | Returned: <em>{l.returnDate || '-'}</em>
              </li>
            ))}
          </ul>
        }
      </section>
    </div>
  );
};

export default UserActivityPage;
