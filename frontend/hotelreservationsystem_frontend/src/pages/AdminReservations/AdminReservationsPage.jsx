import { useState } from "react";
import classes from "./adminReservationPage.module.css";
import { getFilteredReservations } from "../../services/ReservationService";

export default function AdminReservationsPage() {
    const [reservations, setReservations] = useState([]);
    const [filters, setFilters] = useState({
        username: "",
        hotelName: "",
        startDate: "",
        endDate: "",
    });
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({ ...prev, [name]: value }));
    };

    const handleSearch = async (newPage = 0) => {
        try {
            const data = await getFilteredReservations(filters, newPage, 10);
            setReservations(data.content);
            setTotalPages(data.totalPages);
            setPage(newPage);
        } catch (err) {
            console.error("Failed to fetch reservations:", err.message);
        }
    };

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            handleSearch(newPage);
        }
    };

    return (
        <div className={classes.container}>
            <h2>All Reservations</h2>

            <form className={classes.filterForm} onSubmit={(e) => e.preventDefault()}>
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={filters.username}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="hotelName"
                    placeholder="Hotel Name"
                    value={filters.hotelName}
                    onChange={handleInputChange}
                />
                <input
                    type="date"
                    name="startDate"
                    value={filters.startDate}
                    onChange={handleInputChange}
                />
                <input
                    type="date"
                    name="endDate"
                    value={filters.endDate}
                    onChange={handleInputChange}
                />
                <button type="button" onClick={() => handleSearch(0)}>Search</button>
            </form>

            {reservations.length === 0 ? (
                <p>No reservations found.</p>
            ) : (
                <ul className={classes.reservationList}>
                    {reservations.map((res) => (
                        <li key={res.id} className={classes.reservationCard}>
                            <div><strong>User ID:</strong> {res.customerId}</div>
                            <div><strong>Hotel ID:</strong> {res.hotelId}</div>
                            <div><strong>Check-in:</strong> {res.checkInDate}</div>
                            <div><strong>Check-out:</strong> {res.checkOutDate}</div>
                            <div><strong>Total Cost:</strong> ${res.totalCost}</div>
                        </li>
                    ))}
                </ul>
            )}

            <div className={classes.pagination}>
                <button onClick={() => handlePageChange(page - 1)} disabled={page === 0}>Previous</button>
                <span>Page {page + 1} of {totalPages}</span>
                <button onClick={() => handlePageChange(page + 1)} disabled={page + 1 === totalPages}>Next</button>
            </div>
        </div>
    );
}