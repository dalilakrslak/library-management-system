import React, { useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';

const SuperAdminPage = () => {
    const location = useLocation();

    const [branches, setBranches] = useState([
        { id: 1, name: 'BookWorm Mostar', contact: '0412410984', location: 'Mostar' },
        { id: 2, name: 'BookWorm Sarajevo', contact: '0337654321', location: 'Sarajevo' }
    ]);

    const [showModal, setShowModal] = useState(false);
    const [editingBranch, setEditingBranch] = useState(null);
    const [formData, setFormData] = useState({ name: '', contact: '', location: '' });

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

    const handleFormSubmit = (e) => {
        e.preventDefault();

        if (editingBranch) {
            setBranches(branches.map(b => b.id === editingBranch.id ? { ...formData, id: editingBranch.id } : b));
        } else {
            const newId = branches.length ? Math.max(...branches.map(b => b.id)) + 1 : 1;
            setBranches([...branches, { ...formData, id: newId }]);
        }

        closeModal();
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

    const proceedDelete = () => {
        setBranches(branches.filter(b => b.id !== branchToDelete));
        cancelDelete();
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
                <div className="header">
                    <h1>Branch Management</h1>
                    <button className="add-btn" onClick={() => openModal()}>+ Add Branch</button>
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
                            <h2>{editingBranch ? 'Edit Branch' : 'Add Branch'}</h2>
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
