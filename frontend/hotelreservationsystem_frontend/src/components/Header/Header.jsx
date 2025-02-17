import classes from './header.module.css';
import { Link } from 'react-router'

export default function Header() {
    
    return (
        <header className={classes.header}>
            <div className={classes.container}>
                <Link to='/' className={classes.logo}>
                    InnSight
                </Link>
                <nav>   
                    <ul>
                        <Link to='/browser'>Hotels</Link>
                        <Link to="/login">Login</Link>
                    </ul>    
                </nav>
            </div>
        </header>
    )
}