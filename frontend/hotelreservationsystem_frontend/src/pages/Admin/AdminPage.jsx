import classes from './AdminPage.module.css';

export default function AdminPage() {
    return (
        <div className={classes.container}>
            <h1 className={classes.title}>Admin Dashboard</h1>
            <div className={classes.buttonContainer}>
                <button className={classes.button}>Hotels and Rooms</button>
                <button className={classes.button}>Reservations</button>
            </div>
        </div>
    );
}