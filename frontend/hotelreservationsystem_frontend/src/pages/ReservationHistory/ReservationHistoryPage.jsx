import { useEffect, useState } from "react";
import { useAuth } from "../../hooks/AuthContext";
import { getReservationsForCustomer } from "../../services/ReservationService";
import classes from "./reservationHistoryPage.module.css";

export default function ReservationHistoryPage() {
    const { user } = useAuth();
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        if (user) {
            getReservationsForCustomer(user.id).then(setReservations);
        }
    }, [user])

    return (
        <div className={classes.container}>
            <h1 className={classes.title}>My Reservation History</h1>

            {reservations.length === 0 ? (
                <p className={classes.noReservations}>You have no past reservations.</p>
            ) : (
                <ul className={classes.list}>
                    {reservations.map((reservation) => (
                        <li key={reservation.id} className={classes.reservationCard}>
                            <h2>{reservation.hotelName}</h2>

                            <p><strong>Reservation date:</strong> {reservation.reservationDate}</p>
                            <p><strong>Check-in:</strong> {reservation.checkInDate}</p>
                            <p><strong>Check-out:</strong> {reservation.checkOutDate}</p>
                            <p><strong>Total Cost:</strong> {reservation.totalCost.toFixed(2)}€</p>
                            <div className={classes.rooms}>
                                <p><strong>Rooms:</strong></p>
                                <ul>
                                    {reservation.reservationItems.map((item, index) => (
                                        <li key={index}>
                                            {item.roomType} - {item.numberOfRoomsReserved} room(s)
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}