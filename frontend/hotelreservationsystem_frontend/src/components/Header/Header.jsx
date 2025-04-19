import classes from './header.module.css';
import { Link, useNavigate } from 'react-router'
import { useAuth } from "../../hooks/AuthContext";

export default function Header() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = async () => {
        await logout();
        navigate("/login");
    };

    return (
        <header className={classes.header}>
            <div className={classes.container}>
                <Link to='/' className={classes.logo}>
                    InnSight
                </Link>
                <nav>
                    <ul>
                        <li><Link to='/browser'>Hotels</Link></li>
                        {user ? (
                            <>
                                {user.role === "ROLE_ADMIN" ? (
                                    <li> <Link to='/admin'>Admin Panel</Link> </li>
                                ) : (
                                    <li> <Link to='/profile'>Profile</Link> </li>
                                )}

                                <li> <button onClick={handleLogout} className={classes.logoutButton}>Logout</button> </li>
                            </>
                        ) : (
                            <li> <Link to="/login">Login</Link> </li>
                        )}
                    </ul>
                </nav>
            </div>
        </header>
    )
}