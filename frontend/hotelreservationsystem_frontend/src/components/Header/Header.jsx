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
                        <Link to='/browser'>Hotels</Link>
                        {user ? (
                            <>
                                <Link to='/profile'>Profile</Link>
                                <li><button onClick={handleLogout} className={classes.logoutButton}>Logout</button></li>
                            </>
                        ) : (
                            <Link to="/login">Login</Link>
                        )}
                    </ul>
                </nav>
            </div>
        </header>
    )
}