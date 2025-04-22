import { useEffect, useState } from "react";
import { useParams } from "react-router";
import classes from "./adminRoomsPage.module.css";
import { createRoom, deleteRoom, getRoomsByHotelId, updateRoom } from "../../services/HotelService";

export default function AdminRoomsPage() {
    const { hotelId } = useParams();
    const [rooms, setRooms] = useState([]);
    const [editingRoom, setEditingRoom] = useState(null);
    const [formData, setFormData] = useState({
        hotelId: parseInt(hotelId),
        type: "",
        maxGuests: 1,
        totalCount: 1,
        pricePerNight: 1
    });

    useEffect(() => {
        getRoomsByHotelId(hotelId).then(setRooms);
    }, [hotelId]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: name === "type" ? value : parseInt(value)
        }));
    };

    const handleEditClick = (room) => {
        setEditingRoom(room);
        setFormData({ ...room });
    };

    const handleSave = async () => {
        try {
            const updated = await updateRoom(editingRoom.id, formData);
            setRooms((prev) =>
                prev.map((r) => (r.id === updated.id ? updated : r))
            );
            setEditingRoom(null);
        } catch (err) {
            console.error("Error saving room:", err);
        }
    };

    const handleCreate = async () => {
        try {
            const newRoom = await createRoom(formData);
            setRooms((prev) => [...prev, newRoom]);
            setFormData({
                hotelId: parseInt(hotelId),
                type: "",
                maxGuests: 1,
                totalCount: 1,
                pricePerNight: 0
            });
        } catch (err) {
            console.error("Error creating room:", err);
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteRoom(id);
            setRooms((prev) => prev.filter((r) => r.id !== id));
        } catch (err) {
            console.error("Error deleting room:", err);
        }
    };

    return (
        <div className={classes.container}>
            <h2>Rooms of hotel Id: {hotelId}</h2>

            {rooms.length === 0 ? (
                <p className={classes.emptyMessage}>No rooms found for this hotel.</p>
            ) : (
                <ul className={classes.list}>
                    {rooms.map((room) => (
                        <li key={room.id} className={classes.roomCard}>
                            <div>
                                <strong>Type:</strong> {room.type}
                            </div>
                            <div>
                                <strong>Max Guests:</strong> {room.maxGuests}
                            </div>
                            <div>
                                <strong>Total number in the hotel:</strong> {room.totalCount}
                            </div>
                            <div>
                                <strong>Price/Night:</strong> ${room.pricePerNight}
                            </div>
                            <div className={classes.actions}>
                                <button onClick={() => handleEditClick(room)} className={classes.editButton}>
                                    Edit
                                </button>
                                <button onClick={() => handleDelete(room.id)} className={classes.deleteButton}>
                                    Delete
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            )}



            <div className={classes.editForm}>
                <h3>{editingRoom ? "Edit Room" : "Create New Room"}</h3>
                <label>Type:</label>
                <input name="type" value={formData.type} onChange={handleInputChange} />

                <label>Max Guests:</label>
                <input
                    name="maxGuests"
                    type="number"
                    min="1"
                    value={formData.maxGuests}
                    onChange={handleInputChange}
                />

                <label>Total Count:</label>
                <input
                    name="totalCount"
                    type="number"
                    min="1"
                    value={formData.totalCount}
                    onChange={handleInputChange}
                />

                <label>Price Per Night:</label>
                <input
                    name="pricePerNight"
                    type="number"
                    min="0"
                    value={formData.pricePerNight}
                    onChange={handleInputChange}
                />

                <div className={classes.formActions}>
                    {editingRoom ? (
                        <>
                            <button onClick={handleSave} className={classes.saveButton}>Save</button>
                            <button onClick={() => setEditingRoom(null)} className={classes.cancelButton}>Cancel</button>
                        </>
                    ) : (
                        <button onClick={handleCreate} className={classes.createButton}>Create Room</button>
                    )}
                </div>
            </div>
        </div>
    );
}