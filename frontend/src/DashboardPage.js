import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './SuperAdminPage.css';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { useNavigate } from 'react-router-dom';
import { logoutUser } from './AuthService';
import { ROUTES } from './utils/config';

ChartJS.register(ArcElement, Tooltip, Legend);

const DashboardPage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    logoutUser(navigate);
  };

  const [transfers, setTransfers] = useState([]);
  const [librariesMap, setLibrariesMap] = useState({});
  const [libraries, setLibraries] = useState([]);
  const [books, setBooks] = useState([]);
  const [allBooks, setAllBooks] = useState([]);
  const [totalStats, setTotalStats] = useState({
    librariesCount: 0,
    usersCount: 0,
    booksCount: 0,
  });

  const [pieChartData, setPieChartData] = useState({
    borrowed: 0,
    available: 0,
  });

  const [selectedBookId, setSelectedBookId] = useState('');
  const [bookAvailability, setBookAvailability] = useState([]);
  const [availableBookVersions, setAvailableBookVersions] = useState([]);
  const [selectedLibraryId, setSelectedLibraryId] = useState('');


  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');

      const [transfersRes, librariesRes, booksRes] = await Promise.all([
        fetch(ROUTES.transfer, { headers: { Authorization: `Bearer ${token}` } }),
        fetch(ROUTES.libraries, { headers: { Authorization: `Bearer ${token}` } }),
        fetch(ROUTES.bookVersions, { headers: { Authorization: `Bearer ${token}` } }),
      ]);

      const transfersData = await transfersRes.json();
      const librariesData = await librariesRes.json();
      const booksData = await booksRes.json();

      const libMap = {};
      librariesData.forEach(lib => {
        libMap[lib.id] = lib.name;
      });

      setTransfers(transfersData);
      setLibrariesMap(libMap);
      setLibraries(librariesData);
      setBooks(booksData.filter(book => book.availableCopies > 0));
    };


    const fetchBooks = async () => {
      const token = localStorage.getItem('token');
      try {
        const res = await fetch(ROUTES.books, {
          headers: { Authorization: `Bearer ${token}` }
        });
        const data = await res.json();
        setAllBooks(data);
      } catch (error) {
        console.error('Error loading books:', error);
      }
    };

    const fetchStats = async () => {
      const token = localStorage.getItem('token');

      try {
        const [bookVersionsRes, usersRes, librariesRes] = await Promise.all([
          fetch(ROUTES.bookVersions, { headers: { Authorization: `Bearer ${token}` } }),
          fetch(ROUTES.users, { headers: { Authorization: `Bearer ${token}` } }),
          fetch(ROUTES.libraries, { headers: { Authorization: `Bearer ${token}` } }),
        ]);

        const bookVersions = await bookVersionsRes.json();
        const users = await usersRes.json();
        const libraries = await librariesRes.json();

        setTotalStats({
          librariesCount: libraries.length,
          usersCount: users.length,
          booksCount: bookVersions.length,
        });

        const borrowedCount = bookVersions.filter(book => book.isCheckedOut).length;
        const availableCount = bookVersions.filter(book => !book.isCheckedOut).length;

        setPieChartData({
          borrowed: borrowedCount,
          available: availableCount,
        });
      } catch (error) {
        console.error('Error loading statistics:', error);
      }
    };

    fetchStats();
    fetchBooks();
    fetchData();
  }, []);

  const completedTransfers = transfers
    .filter(t => t.transferDate)
    .map(t => `${librariesMap[t.libraryFrom]} → ${librariesMap[t.libraryTo]} ✔ Completed`);

  const ongoingTransfers = transfers
    .filter(t => !t.transferDate)
    .map(t => `${librariesMap[t.libraryFrom]} → ${librariesMap[t.libraryTo]} ⏳ In Process`);

  const [modalOpen, setModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    bookIsbn: '',
    fromBranch: '',
    toBranch: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    console.log("SUBMIT handler triggered!");

    e.preventDefault();

    const token = localStorage.getItem('token');

    const transferPayload = {
      bookVersion: formData.bookIsbn,
      libraryFrom: parseInt(formData.fromBranch),
      libraryTo: parseInt(formData.toBranch),
      transferDate: null, 
    };

    try {
      const res = await fetch(ROUTES.transfer, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(transferPayload),
      });

      if (res.ok) {
        const result = await res.json();
        console.log('✅ Transfer successfully created:', result);
        alert('Transfer created successfully!');
        window.location.reload();
        setModalOpen(false);
      } else {
        const errMsg = await res.text();
        console.error('❌ Transfer creation failed:', errMsg);
        alert('Failed to create transfer');
      }
    } catch (error) {
      console.error('🚨 Error during transfer creation:', error);
      alert('Error occurred while creating transfer');
    }
  };

  const pieData = {
    labels: ['Borrowed Books', 'Available Books'],
    datasets: [
      {
        data: [pieChartData.borrowed, pieChartData.available],
        backgroundColor: ['#007bff', '#28a745'],
        borderColor: ['#fff', '#fff'],
        borderWidth: 1,
      },
    ],
  };

  const pieOptions = {
    plugins: {
      legend: {
        position: 'bottom',
      },
    },
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
        <h1>Dashboard Statistics</h1>

        <div className="dashboard-grid">
          <div className="chart-container">
            <div className="pie-chart-box">
              <h3>Borrowed vs Returned Books</h3>
              <Pie data={pieData} options={pieOptions} />
            </div>
          </div>

          <div className="stats-box">
            <div className="stat-card">🏛️<span>{totalStats.librariesCount}</span><p>Libraries</p></div>
            <div className="stat-card">👤<span>{totalStats.usersCount}</span><p>Users</p></div>
            <div className="stat-card">📚<span>{totalStats.booksCount}</span><p>Books</p></div>
          </div>

          <div className="transfers-box">
            <h3>Book Transfers</h3>
            <h4>Completed Transfers:</h4>
            <ul className="transfer-list">
              {completedTransfers.map((t, i) => <li key={i}>{t}</li>)}
            </ul>
            <h4>Ongoing Transfers:</h4>
            <ul className="transfer-list">
              {ongoingTransfers.map((t, i) => <li key={i}>{t}</li>)}
            </ul>
          </div>

          <div className="requested-transfer-box">
            <h3>New Transfer</h3>
            <button className="add-btn" onClick={() => setModalOpen(true)}>+ Request Transfer</button>
          </div>

          {modalOpen && (
            <div className="modal-backdrop">
              <div className="modal">
                <h2>Request Book Transfer</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                      <select
                        value={selectedBookId}
                        onChange={e => setSelectedBookId(e.target.value)}
                        required
                      >
                        <option value="">Select a book</option>
                        {allBooks.map(book => (
                          <option key={book.id} value={book.id}>
                            {book.title}
                          </option>
                        ))}
                      </select>

                      <button
                        type="button"
                        className="check-availability-btn"
                        onClick={async () => {
                          const token = localStorage.getItem('token');
                          try {
                            const res = await fetch(`${ROUTES.availability}/${selectedBookId}`, {
                              headers: { Authorization: `Bearer ${token}` },
                            });
                            const data = await res.json();
                            setBookAvailability(data);
                          } catch (err) {
                            console.error('Failed to fetch availability:', err);
                          }
                        }}
                      >
                        🔍 Check Availability
                      </button>
                    </div>


                  {bookAvailability.length > 0 && (
                    <div className="availability-list">
                      <h4>Availability by Library</h4>
                      <ul>
                        {bookAvailability.map((entry, idx) => (
                          <li key={idx}>
                            <strong>{entry.libraryName}</strong>: 
                            Available: {entry.availableCount}, 
                            Reserved: {entry.reservedCount}, 
                            Checked Out: {entry.checkedOutCount}, 
                            In Transfer: {entry.transferCount}, 
                            Total: {entry.totalCount}
                          </li>
                        ))}
                      </ul>
                    </div>
                  )}
                  {availableBookVersions.length === 0 && (
                    <p style={{ color: '#a00', padding: '10px' }}>
                      ⚠️ No available (unreserved and unchecked) copies of this book in selected library.
                    </p>
                  )}

                  {bookAvailability.length > 0 && (
                    <div className="form-group">
                      <label>Select Library to Transfer From:</label>
                      <select
                        name="fromBranch"
                        value={formData.fromBranch}
                        onChange={async (e) => {
                          const libraryId = e.target.value;
                          setSelectedLibraryId(libraryId);
                          setFormData(prev => ({ ...prev, fromBranch: libraryId }));

                          console.log(libraryId)
                          const token = localStorage.getItem('token');
                          try {
                            const res = await fetch(`${ROUTES.booksByLibrary(libraryId)}`, {
                              headers: { Authorization: `Bearer ${token}` },
                            });
                            const data = await res.json();
                            console.log(data)

                            const versions = data.filter(v => v.bookId === Number(selectedBookId));

                            console.log(versions)
                            const availableVersions = versions.filter(v =>
                              !v.isCheckedOut && !v.isReserved
                            );
                            setAvailableBookVersions(availableVersions);
                          } catch (err) {
                            console.error('Error fetching book versions:', err);
                          }
                        }}
                        required
                      >
                        <option value="">Select Library</option>
                        {bookAvailability
                          .filter(entry => entry.availableCount > 0)
                          .map((entry, idx) => (
                            <option key={idx} value={entry.libraryId}>{entry.libraryName}</option>
                        ))}
                      </select>

                    </div>
                  )}
                  {bookAvailability.length > 0 && (
                    <div className="form-group">
                      <label>Select Library to Transfer To:</label>
                      <select
                        name="toBranch"
                        value={formData.toBranch}
                        onChange={handleInputChange}
                        required
                      >
                        <option value="">Select Library</option>
                        {libraries
                          .filter(lib => lib.id.toString() !== selectedLibraryId)
                          .map(lib => (
                            <option key={lib.id} value={lib.id}>{lib.name}</option>
                          ))}
                      </select>
                    </div>
                  )}

                  {availableBookVersions.length > 0 && (
                    <div className="form-group">
                      <label>Select ISBN for Transfer:</label>
                      <select name="bookIsbn" value={formData.bookIsbn} onChange={handleInputChange} required>
                        <option value="">Select ISBN</option>
                        {availableBookVersions.map((bv, idx) => (
                          <option key={idx} value={bv.isbn}>{bv.isbn}</option>
                        ))}
                      </select>
                    </div>
                  )}
                  {formData.bookIsbn && formData.fromBranch && formData.toBranch ? (
                    <div className="modal-actions">
                      <button type="submit">Submit Request</button>
                      <button type="button" onClick={() => setModalOpen(false)}>Close</button>
                    </div>
                  ) : (
                    <div className="modal-actions">
                      <button type="button" className="disabled-btn" disabled>Submit Request</button>
                      <button type="button" onClick={() => setModalOpen(false)}>Close</button>
                    </div>
                  )}

                </form>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};


export default DashboardPage;
