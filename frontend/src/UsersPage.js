import React, { useState, useEffect } from 'react';
import './SuperAdminPage.css';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from './utils/config';
import { logoutUser } from './AuthService';
import { fetchRoleMap, fetchLibraryMap } from './utils/LovData';

const UsersPage = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const handleLogout = () => {
        logoutUser(navigate);
    };

    const [users, setUsers] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [editingUser, setEditingUser] = useState(null);
    const [formData, setFormData] = useState({ firstName: '', lastName: '', email: '', phone: '', role: '', library: '' });
    const [roleMap, setRoleMap] = useState({});
    const [libraryMap, setLibraryMap] = useState({});

    useEffect(() => {
        const fetchUsers = async () => {
            const token = localStorage.getItem('token');

            try {
                const [userRes, roleMapData, libraryMapData] = await Promise.all([
                    fetch(ROUTES.users, {
                    headers: { Authorization: `Bearer ${token}` }
                    }),
                    fetchRoleMap(),
                    fetchLibraryMap()
                ]);

                if (!userRes.ok) throw new Error('Failed to fetch users');

                const data = await userRes.json();

                const formatted = data.map(u => ({
                    id: u.id,
                    firstName: u.firstName,
                    lastName: u.lastName,
                    email: u.email,
                    phone: u.phone,
                    role: u.roleId,   
                    library: u.libraryId
                }));

                setUsers(formatted);
                setRoleMap(roleMapData);
                setLibraryMap(libraryMapData);
            } catch (err) {
            console.error('Error fetching users:', err);
            }
        };

        fetchUsers();
    }, []);


    const openModal = (user = null) => {
        setEditingUser(user);
        setFormData(user
        ? {
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email,
            phone: user.phone,
            role: user.role,        
            library: user.library     
            }
        : {
            firstName: '',
            lastName: '',
            email: '',
            phone: '',
            role: '',
            library: ''
            }
        );
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setEditingUser(null);
    };

    const handleFormChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');

        try {
            if (editingUser) {
                const res = await fetch(`${ROUTES.users}/${editingUser.id}`, {
                    method: 'PUT',
                    headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        firstName: formData.firstName,
                        lastName: formData.lastName,
                        email: formData.email,
                        phone: formData.phone,
                        roleId: Number(formData.role),
                        libraryId: Number(formData.library)
                    })
                });

                if (!res.ok) throw new Error('Failed to update user');

                const updated = await res.json();

                setUsers(users.map(u =>
                u.id === updated.id
                    ? {
                        ...updated,
                        role: updated.roleId,
                        library: updated.libraryId
                    }
                    : u
                ));
            } 
            else {
                const res = await fetch(ROUTES.users, {
                    method: 'POST',
                    headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`
                    },
                    body: JSON.stringify({
                    firstName: formData.firstName,
                    lastName: formData.lastName,
                    email: formData.email,
                    phone: formData.phone,
                    roleId: Number(formData.role),
                    libraryId: Number(formData.library) 
                    })
                });

                if (!res.ok) throw new Error('Failed to create user');

                const created = await res.json();

                setUsers([...users, {
                    ...created,
                    role: created.roleId,
                    library: created.libraryId,
                    libraryName: libraryMap[created.libraryId],
                    roleName: roleMap[created.roleId]
                }]);
            }

            closeModal();
        } catch (err) {
            console.error('Error saving user:', err);
        }
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

    const proceedDelete = async () => {
  const token = localStorage.getItem('token');

  try {
    const res = await fetch(`${ROUTES.users}/${userToDelete}`, {
      method: 'DELETE',
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) throw new Error('Failed to delete user');

    setUsers(users.filter(u => u.id !== userToDelete));
    cancelDelete();
  } catch (err) {
    console.error('Error deleting user:', err);
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
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Library</th>
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
                                <td>{user.phone}</td>
                                <td>{user.libraryName || libraryMap[user.library] || user.library}</td>
                                <td>{user.roleName || roleMap[user.role] || user.role}</td>
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
                                <input name="phone" type="text" placeholder="Phone" value={formData.phone} onChange={handleFormChange} required />
                                <select name="role" value={formData.role} onChange={handleFormChange} required>
                                    <option value="">Select role</option>
                                    {Object.entries(roleMap).map(([id, name]) => (
                                        <option key={id} value={id}>{name}</option>
                                    ))}
                                </select>
                                <select name="library" value={formData.library} onChange={handleFormChange} required>
                                <option value="">Select library</option>
                                    {Object.entries(libraryMap).map(([id, name]) => (
                                        <option key={id} value={id}>{name}</option>
                                    ))}
                                </select>
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
