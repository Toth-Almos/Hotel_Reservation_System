import { useEffect, useState } from "react";
import { getAll, updateHotel } from "../../services/HotelService";
import classes from "./adminHotelPage.module.css";
import { Link } from "react-router";

export default function AdminHotelPage() {
    const [hotels, setHotels] = useState([]);
    const [editingHotel, setEditingHotel] = useState(null);
    const [formData, setFormData] = useState({
        name: "",
        country: "",
        city: "",
        address: "",
        star: 1
    });

    useEffect(() => {
        getAll().then(setHotels)
    }, []);

    const handleEditClick = (hotel) => {
        setEditingHotel(hotel);
        setFormData(hotel);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === "star" ? parseInt(value) : value
        }));
    };

    const handleSave = async () => {
        try {
            const updatedHotel = await updateHotel(editingHotel.id, formData);
            setHotels(hotels.map(h => h.id === updatedHotel.id ? updatedHotel : h));
            setEditingHotel(null);
        } catch (error) {
            console.error("Error updating hotel:", error);
        }
    };

    return (
        <div className={classes.container}>
            <h2>Hotel Page for Admin</h2>
            <ul className={classes.list}>
                {hotels.map(hotel => (
                    <li key={hotel.id}>
                        <div className={classes.content}>
                            <div className={classes.hotelNameStar}>
                                <p>{hotel.name}</p>
                                <p className={classes.hotelStar}>{Array(hotel.star).fill("⭐").join("")}</p>
                            </div>
                            <div className={classes.hotelLocation}>
                                <p>{hotel.country}</p>
                                <p>{hotel.city}</p>
                            </div>
                            <div className={classes.editButtonHolder}>
                                <button onClick={() => handleEditClick(hotel)} className={classes.editButton}>Edit Hotel</button>
                                <Link to={`/admin-rooms/${hotel.id}`}>
                                    <button className={classes.roomButton}>View Rooms</button>
                                </Link>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>

            {editingHotel && (
                <div className={classes.modalOverlay}>
                    <div className={classes.editForm}>
                        <h3>Editing: {editingHotel.name}</h3>
                        <label>Name:</label>
                        <input name="name" value={formData.name} onChange={handleInputChange} />

                        <label>Country:</label>
                        <input name="country" value={formData.country} onChange={handleInputChange} />

                        <label>City:</label>
                        <input name="city" value={formData.city} onChange={handleInputChange} />

                        <label>Address:</label>
                        <input name="country" value={formData.address} onChange={handleInputChange} />

                        <label>Star Rating:</label>
                        <input type="number" name="star" min="1" max="5" value={formData.star} onChange={handleInputChange} />

                        <div className={classes.formActions}>
                            <button onClick={handleSave} className={classes.saveButton}>Save</button>
                            <button onClick={() => setEditingHotel(null)} className={classes.cancelButton}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}