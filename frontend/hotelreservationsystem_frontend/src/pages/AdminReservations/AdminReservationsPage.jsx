import { useState } from "react";
import classes from "./adminReservationPage.module.css";
import { deleteReservation, getFilteredReservations } from "../../services/ReservationService";

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
    const [editingReservation, setEditingReservation] = useState(null);
    const [editFormData, setEditFormData] = useState({
        customerId: "",
        hotelId: "",
        checkInDate: "",
        checkOutDate: "",
        totalCost: ""
    });

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

    const handleEditClick = (reservation) => {
        setEditingReservation(reservation);
        setEditFormData({
            customerId: reservation.customerId,
            hotelId: reservation.hotelId,
            checkInDate: reservation.checkInDate,
            checkOutDate: reservation.checkOutDate,
            totalCost: reservation.totalCost
        });
    };

    const handleEditInputChange = (e) => {
        const { name, value } = e.target;
        setEditFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleUpdate = async () => {
        try {
            //await updateReservation(editingReservation.id, editFormData);
            setEditingReservation(null);
            handleSearch(page);
        } catch (err) {
            console.error("Update failed:", err.message);
        }
    };

    const handleDelete = async (reservationId) => {
        const confirmed = window.confirm("Are you sure you want to delete this reservation?");
        if (!confirmed) return;

        try {
            await deleteReservation(reservationId);
            setReservations(prev => prev.filter(res => res.id !== reservationId));
        } catch (error) {
            console.error("Failed to delete reservation:", error.message);
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
                            <div className={classes.actions}>
                                <button className={classes.editButton} onClick={() => handleEditClick(res)}>Edit</button>
                                <button className={classes.deleteButton} onClick={() => handleDelete(res.id)}>Delete</button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}

            {editingReservation && (
                <div className={classes.modalOverlay}>
                    <div className={classes.modalContent}>
                        <h3>Edit Reservation</h3>
                        <label>User ID:</label>
                        <input name="customerId" value={editFormData.customerId} onChange={handleEditInputChange} />

                        <label>Hotel ID:</label>
                        <input name="hotelId" value={editFormData.hotelId} onChange={handleEditInputChange} />

                        <label>Check-in:</label>
                        <input type="date" name="checkInDate" value={editFormData.checkInDate} onChange={handleEditInputChange} />

                        <label>Check-out:</label>
                        <input type="date" name="checkOutDate" value={editFormData.checkOutDate} onChange={handleEditInputChange} />

                        <label>Total Cost:</label>
                        <input type="number" name="totalCost" value={editFormData.totalCost} onChange={handleEditInputChange} />

                        <div className={classes.modalActions}>
                            <button className={classes.saveButton} onClick={handleUpdate}>Save</button>
                            <button className={classes.cancelButton} onClick={() => setEditingReservation(null)}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}

            <div className={classes.pagination}>
                <button onClick={() => handlePageChange(page - 1)} disabled={page === 0}>Previous</button>
                <span>Page {page + 1} of {totalPages}</span>
                <button onClick={() => handlePageChange(page + 1)} disabled={page + 1 === totalPages}>Next</button>
            </div>
        </div>
    );
}