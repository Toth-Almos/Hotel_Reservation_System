import classes from './AdminPage.module.css';
import { useNavigate } from 'react-router'

export default function AdminPage() {
    const navigate = useNavigate();

    const handleNavigate = (path) => {
        navigate("/" + path);
    };

    return (
        <div className={classes.container}>
            <h1 className={classes.title}>Admin Dashboard</h1>
            <div className={classes.buttonContainer}>
                <button onClick={() => handleNavigate("admin-hotels")} className={classes.button}>Hotels and Rooms</button>
                <button onClick={() => handleNavigate("admin-reservations")} className={classes.button}>Reservations</button>
            </div>
        </div>
    );
}