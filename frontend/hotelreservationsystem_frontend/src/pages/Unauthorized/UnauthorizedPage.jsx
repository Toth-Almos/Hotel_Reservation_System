import classes from './UnauthorizedPage.module.css';

export default function UnauthorizedPage() {
    return (
        <div className={classes.container}>
            <h2 className={classes.title}>Unauthorized</h2>
            <p className={classes.message}>You do not have permission to view this page.</p>
        </div>
    );
}