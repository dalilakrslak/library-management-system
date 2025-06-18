import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './SuperAdminPage.css';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { useNavigate } from 'react-router-dom';
import LibrarianBooks from './LibrarianBooks';

ChartJS.register(ArcElement, Tooltip, Legend);

const LibrarianDashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    sessionStorage.clear();
    navigate("/login");
  };

  const [modalOpen, setModalOpen] = useState(false);
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    numberOfBooks: 1,
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

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Transfer Request Submitted:", formData);
    setModalOpen(false);  // Close the modal after submission
  };

  const pieData = {
    labels: ['Borrowed Books', 'Returned Books'],
    datasets: [
      {
        data: [1200, 900],
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
        <h1>Dashboard Statistics</h1>

        <div className="dashboard-grid">
          <div className="chart-container">
            <div className="pie-chart-box">
              <h3>Borrowed vs Returned Books</h3>
              <Pie data={pieData} options={pieOptions} />
            </div>
          </div>

          <div className="stats-box">
            <div className="stat-card">🏛️<span>10</span><p>Branches</p></div>
            <div className="stat-card">👤<span>150</span><p>Users</p></div>
            <div className="stat-card">📚<span>1500</span><p>Books</p></div>
          </div>

          {/* Widget 1: Completed and Ongoing Book Transfers */}
          <div className="transfers-box">
            <h3>Book Transfers</h3>
            <h4>Completed Transfers:</h4>
            <ul className="transfer-list">
              <li>Matara → Malta <span>✔ Completed</span></li>
              <li>Alipašina → Miss Irbina <span>✔ Completed</span></li>
            </ul>
            <h4>Ongoing Transfers:</h4>
            <ul className="transfer-list">
              <li>Malta → Radiceva <span>⏳ In Process</span></li>
              <li>Alipašina → Radiceva <span>⏳ In Process</span></li>
            </ul>
          </div>

          {/* Widget 2: Requested but Unavailable Books */}
          <div className="requested-transfer-box">
            <h3>Requested but Unavailable Books</h3>
            <ul className="transfer-list">
              <li>📚 Six of Crows <span>❌ Not Available</span></li>
              <li>📚 It Ends with Us <span>❌ Not Available</span></li>
              <li>📚 Normal People <span>❌ Not Available</span></li>
            </ul>
            <button className="add-btn" onClick={() => setModalOpen(true)}>+ Request Transfer</button>
          </div>

          {/* Modal for Request Transfer */}
          {modalOpen && (
            <div className="modal-backdrop">
              <div className="modal">
                <h2>Request Book Transfer</h2>
                <form onSubmit={handleSubmit}>
                  <div className="form-group">
                    <label>Title:</label>
                    <input
                      type="text"
                      name="title"
                      value={formData.title}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Author:</label>
                    <input
                      type="text"
                      name="author"
                      value={formData.author}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>Number of Books:</label>
                    <input
                      type="number"
                      name="numberOfBooks"
                      value={formData.numberOfBooks}
                      onChange={handleInputChange}
                      min="1"
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>From Branch:</label>
                    <input
                      type="text"
                      name="fromBranch"
                      value={formData.fromBranch}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label>To Branch:</label>
                    <input
                      type="text"
                      name="toBranch"
                      value={formData.toBranch}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="modal-actions">
                    <button type="submit">Submit Request</button>
                    <button type="button" onClick={() => setModalOpen(false)}>Close</button>
                  </div>
                </form>
              </div>
            </div>
          )}

          <div className="top-books-box">
            <h3>Most Borrowed Books</h3>
            <ul>
              <li>📖 Six of Crows — 150 times</li>
              <li>📖 It Ends with Us — 123 times</li>
              <li>📖 Normal People — 112 times</li>
            </ul>
          </div>
        </div>
      </main>
    </div>
  );
};

export default LibrarianDashboard;
