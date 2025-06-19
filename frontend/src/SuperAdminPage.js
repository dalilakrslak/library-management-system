import React, { useEffect, useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from './utils/config';
import { logoutUser } from './AuthService';

const SuperAdminPage = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const handleLogout = () => {
        logoutUser(navigate);
    };

    const [branches, setBranches] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editingBranch, setEditingBranch] = useState(null);
    const [formData, setFormData] = useState({ name: '', contact: '', location: '' });

    useEffect(() => {
        const fetchLibraries = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await fetch(ROUTES.libraries, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                if (!response.ok) throw new Error('Failed to fetch libraries');

                const data = await response.json();

                const formatted = data.map(lib => ({
                    id: lib.id,
                    name: lib.name,
                    contact: lib.contact,
                    location: lib.address 
                }));

                setBranches(formatted);
            } catch (err) {
                console.error('Error fetching libraries:', err);
            }
        };

        fetchLibraries();
    }, []);

    const openModal = (branch = null) => {
        setEditingBranch(branch);
        setFormData(branch ? { ...branch } : { name: '', contact: '', location: '' });
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setEditingBranch(null);
    };

    const handleFormChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');

        try {
            if (editingBranch) {
            const response = await fetch(`${ROUTES.libraries}/${editingBranch.id}`, {
                method: 'PUT',
                headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({
                name: formData.name,
                address: formData.location,
                contact: formData.contact
                })
            });

            if (!response.ok) throw new Error('Failed to update library');

            const updated = await response.json();

            setBranches(branches.map(b =>
                b.id === updated.id
                ? { id: updated.id, name: updated.name, contact: updated.contact, location: updated.address }
                : b
            ));
            } else {
            const response = await fetch(ROUTES.libraries, {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
                },
                body: JSON.stringify({
                name: formData.name,
                address: formData.location,
                contact: formData.contact
                })
            });

            if (!response.ok) throw new Error('Failed to create library');

            const created = await response.json();

            setBranches([...branches, {
                id: created.id,
                name: created.name,
                contact: created.contact,
                location: created.address
            }]);
            }

            closeModal();
        } catch (err) {
            console.error('Error submitting library:', err);
        }
        };


    const [showConfirm, setShowConfirm] = useState(false);
    const [branchToDelete, setBranchToDelete] = useState(null);

    const confirmDelete = (id) => {
        setBranchToDelete(id);
        setShowConfirm(true);
    };

    const cancelDelete = () => {
        setShowConfirm(false);
        setBranchToDelete(null);
    };

    const proceedDelete = async () => {
        const token = localStorage.getItem('token');

        try {
            const response = await fetch(`${ROUTES.libraries}/${branchToDelete}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${token}`
            }
            });

            if (!response.ok) throw new Error('Failed to delete library');

            setBranches(branches.filter(b => b.id !== branchToDelete));
            cancelDelete();
        } catch (err) {
            console.error('Error deleting library:', err);
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
                    <h1>Library Management</h1>
                    <button className="add-btn" onClick={() => openModal()}>+ Add Library</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Contact</th>
                            <th>Location</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {branches.map(branch => (
                            <tr key={branch.id}>
                                <td>{branch.id}</td>
                                <td>{branch.name}</td>
                                <td>{branch.contact}</td>
                                <td>{branch.location}</td>
                                <td>
                                    <span onClick={() => openModal(branch)}>✏️</span>{' '}
                                    <span onClick={() => confirmDelete(branch.id)}>🗑️</span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                {showModal && (
                    <div className="modal-backdrop">
                        <div className="modal">
                            <h2>{editingBranch ? 'Edit Library' : 'Add Library'}</h2>
                            <form onSubmit={handleFormSubmit}>
                                <input
                                    name="name"
                                    type="text"
                                    placeholder="Name"
                                    value={formData.name}
                                    onChange={handleFormChange}
                                    required
                                />
                                <input
                                    name="contact"
                                    type="text"
                                    placeholder="Contact"
                                    value={formData.contact}
                                    onChange={handleFormChange}
                                    required
                                />
                                <input
                                    name="location"
                                    type="text"
                                    placeholder="Location"
                                    value={formData.location}
                                    onChange={handleFormChange}
                                    required
                                />
                                <div className="modal-actions">
                                    <button type="submit">{editingBranch ? 'Update' : 'Add'}</button>
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
                            <p>Are you sure you want to delete this branch?</p>
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

export default SuperAdminPage;
