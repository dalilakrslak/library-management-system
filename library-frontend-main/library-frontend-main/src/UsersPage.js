import React, { useState } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';

const UsersPage = () => {
    const location = useLocation();

    const [users, setUsers] = useState([
        { id: 1, firstName: 'Amir', lastName: 'Kovač', email: 'amir@gmail.com', password: '****', phone: '061111111', role: 'korisnik' },
        { id: 2, firstName: 'Sara', lastName: 'Hadžić', email: 'sara@gmail.com', password: '****', phone: '062222222', role: 'bibliotekar' },
    ]);

    const [showModal, setShowModal] = useState(false);
    const [editingUser, setEditingUser] = useState(null);
    const [formData, setFormData] = useState({ firstName: '', lastName: '', email: '', password: '', phone: '', role: '' });

    const openModal = (user = null) => {
        setEditingUser(user);
        setFormData(user ? { ...user } : { firstName: '', lastName: '', email: '', password: '', phone: '', role: '' });
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setEditingUser(null);
    };

    const handleFormChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleFormSubmit = (e) => {
        e.preventDefault();

        if (editingUser) {
            setUsers(users.map(u => u.id === editingUser.id ? { ...formData, id: editingUser.id } : u));
        } else {
            const newId = users.length ? Math.max(...users.map(u => u.id)) + 1 : 1;
            setUsers([...users, { ...formData, id: newId }]);
        }

        closeModal();
    };

    const [showConfirm, setShowConfirm] = useState(false);
    const [userToDelete, setUserToDelete] = useState(null);

    const confirmDelete = (id) => {
        setUserToDelete(id);
        setShowConfirm(true);
    };

    const cancelDelete = () => {
        setShowConfirm(false);
        setUserToDelete(null);
    };

    const proceedDelete = () => {
        setUsers(users.filter(u => u.id !== userToDelete));
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
                    <h1>User Management</h1>
                    <button className="add-btn" onClick={() => openModal()}>+ Add User</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {users.map(user => (
                            <tr key={user.id}>
                                <td>{user.id}</td>
                                <td>{user.firstName}</td>
                                <td>{user.lastName}</td>
                                <td>{user.email}</td>
                                <td>{user.password}</td>
                                <td>{user.phone}</td>
                                <td>{user.role}</td>
                                <td>
                                    <span onClick={() => openModal(user)}>✏️</span>{' '}
                                    <span onClick={() => confirmDelete(user.id)}>🗑️</span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                {showModal && (
                    <div className="modal-backdrop">
                        <div className="modal">
                            <h2>{editingUser ? 'Edit User' : 'Add User'}</h2>
                            <form onSubmit={handleFormSubmit}>
                                <input name="firstName" type="text" placeholder="First Name" value={formData.firstName} onChange={handleFormChange} required />
                                <input name="lastName" type="text" placeholder="Last Name" value={formData.lastName} onChange={handleFormChange} required />
                                <input name="email" type="email" placeholder="Email" value={formData.email} onChange={handleFormChange} required />
                                <input name="password" type="text" placeholder="Password" value={formData.password} onChange={handleFormChange} required />
                                <input name="phone" type="text" placeholder="Phone" value={formData.phone} onChange={handleFormChange} required />
                                <input name="role" type="text" placeholder="Role" value={formData.role} onChange={handleFormChange} required />
                                <div className="modal-actions">
                                    <button type="submit">{editingUser ? 'Update' : 'Add'}</button>
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
                            <p>Are you sure you want to delete this user?</p>
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

export default UsersPage;
