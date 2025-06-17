import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './SuperAdminPage.css';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const DashboardPage = () => {
  const location = useLocation();

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
        <p className="role-label">Hello, <strong>Superadmin</strong></p>
        <nav>
          <Link to="/dashboard" className={location.pathname === '/dashboard' ? 'active' : ''}>Dashboard</Link>
          <Link to="/books" className={location.pathname === '/books' ? 'active' : ''}>Books</Link>
          <Link to="/branches" className={location.pathname === '/branches' ? 'active' : ''}>Branches</Link>
          <Link to="/users" className={location.pathname === '/users' ? 'active' : ''}>Users</Link>
        </nav>
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

          <div className="transfers-box">
            <div className="transfers-header">
              <h3>Book Transfers</h3>
              <button className="add-btn">+ Request Transfer</button>
            </div>
            <ul className="transfer-list">
              <li>Matara → Malta <span>✔ Completed</span></li>
              <li>Malta → Radiceva <span>⏳ In Process</span></li>
              <li>Alipašina → Miss Irbina <span>✔ Completed</span></li>
              <li>Alipašina → Radiceva <span>⏳ In Process</span></li>
            </ul>
          </div>

          <div className="top-books-box">
            <h3>Most Borrowed Books</h3>
            <ul>
              <li>📖 Six of Crows — 150 times</li>
              <li>📖 It Ends with Us — 123 times</li>
              <li>📖 Normal People — 112 times</li>
            </ul>
          </div>

          <div className="books-stat-box">
            <h3>Books Statistic</h3>
            <ul>
              <li>📘 Steve Jobs — 10</li>
              <li>📘 Zlatan Ibrahimović — 11</li>
              <li>📘 Pride and Prejudice — 12</li>
              <li>📘 Lies — 15</li>
              <li>📘 Silence — 22</li>
              <li>📘 Prayer — 25</li>
            </ul>
          </div>
        </div>
      </main>
    </div>
  );
};

export default DashboardPage;
