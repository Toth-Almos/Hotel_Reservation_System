import { useEffect, useState } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { deleteReservation, getActiveReservationsForCustomer } from "../../services/ReservationService";
import classes from "./reservationCancelPage.module.css";

export default function ReservationCancelPage() {
    const { user } = useAuth();
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        if (user) {
            getActiveReservationsForCustomer(user.id).then(setReservations);
        }
    }, [user])

    const handleCancelReservation = (reservationId) => {
        const confirmCancel = window.confirm("Are you sure you want to cancel this reservation? This is a irreversible!");
        if (confirmCancel) {
            deleteReservation(reservationId)
                .then(() => {
                    setReservations(reservations.filter(reservation => reservation.id !== reservationId));
                    alert("Reservation cancelled successfully.");
                })
                .catch(error => {
                    console.error("Error cancelling reservation:", error);
                    alert("There was an error cancelling the reservation. Please try again.");
                });
        }
    }

    return (
        <div className={classes.container}>
            <h1 className={classes.title}>Your Active Reservations</h1>

            {reservations.length === 0 ? (
                <p className={classes.noReservations}>You don not have any active reservations.</p>
            ) : (
                <ul className={classes.reservationList}>
                    {reservations.map((reservation) => (
                        <li key={reservation.id} className={classes.reservationItem}>
                            <p><strong>Reservation date:</strong> {reservation.reservationDate}</p>
                            <p><strong>Check-in:</strong> {reservation.checkInDate}</p>
                            <p><strong>Check-out:</strong> {reservation.checkOutDate}</p>
                            <p><strong>Total Cost:</strong> {reservation.totalCost.toFixed(2)}€</p>
                            <p><strong>Payment Method:</strong> {reservation.payment.method} </p>
                            <p><strong>Payment Status:</strong> {reservation.payment.status} </p>
                            <button className={classes.cancelButton} onClick={() => handleCancelReservation(reservation.id)}>
                                Cancel Reservation
                            </button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}