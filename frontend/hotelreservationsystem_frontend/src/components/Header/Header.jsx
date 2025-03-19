import classes from './header.module.css';
import { Link } from 'react-router'
import { useAuth } from "../../hooks/AuthContext";

export default function Header() {
    const { user, logout } = useAuth();

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
                                <li><button onClick={logout}>Logout</button></li>
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