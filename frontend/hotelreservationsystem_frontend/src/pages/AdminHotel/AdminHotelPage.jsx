import { useEffect, useState } from "react";
import { createHotel, deleteHotel, getAll, getFilteredHotels, updateHotel } from "../../services/HotelService";
import classes from "./adminHotelPage.module.css";
import { Link } from "react-router";

export default function AdminHotelPage() {
    const [hotels, setHotels] = useState([]);
    const [editingHotel, setEditingHotel] = useState(null);
    const [filters, setFilters] = useState({ name: '', country: '', star: '' });
    const [formData, setFormData] = useState({
        name: "",
        country: "",
        city: "",
        address: "",
        star: 1
    });
    const [newHotelData, setNewHotelData] = useState({
        name: "",
        country: "",
        city: "",
        address: "",
        star: 1
    });

    useEffect(() => {
        getAll()
            .then(data => {
                setHotels(Array.isArray(data) ? data : []);
            })
            .catch(error => {
                console.error("Error fetching hotels:", error);
                setHotels([]);
            });
    }, []);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleSearch = async () => {
        try {
            const data = await getFilteredHotels(filters);
            setHotels(data.content || []);
        } catch (error) {
            console.error("Error searching hotels:", error.message);
            setHotels([]);
        }
    };

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

    const handleNewHotelInputChange = (e) => {
        const { name, value } = e.target;
        setNewHotelData(prev => ({
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

    const handleDelete = async () => {
        const response = await deleteHotel(editingHotel.id);
        alert(response);
        setHotels(hotels.filter(h => h.id !== editingHotel.id));
        setEditingHotel(null);
    }

    const handleCreate = async () => {
        try {
            const createdHotel = await createHotel(newHotelData);
            setHotels(prev => [...prev, createdHotel]);
            setNewHotelData({
                name: "",
                country: "",
                city: "",
                address: "",
                star: 1
            });
        } catch (error) {
            console.error("Error creating hotel:", error);
        }
    };

    return (
        <div className={classes.container}>
            <h2>Hotel Page for Admin</h2>

            {/* Create Hotel Form */}
            <div className={classes.newHotelForm}>
                <h3>Create New Hotel</h3>
                <label>Name:</label>
                <input name="name" value={newHotelData.name} onChange={handleNewHotelInputChange} />

                <label>Country:</label>
                <input name="country" value={newHotelData.country} onChange={handleNewHotelInputChange} />

                <label>City:</label>
                <input name="city" value={newHotelData.city} onChange={handleNewHotelInputChange} />

                <label>Address:</label>
                <input name="address" value={newHotelData.address} onChange={handleNewHotelInputChange} />

                <label>Star Rating:</label>
                <input type="number" name="star" min="1" max="5" value={newHotelData.star} onChange={handleNewHotelInputChange} />

                <button onClick={handleCreate} className={classes.createButton}>Create Hotel</button>
            </div>

            {/* Filter Bar */}
            <div className={classes.searchSection}>
                <input name="name" placeholder="Name" value={filters.name} onChange={handleFilterChange} />
                <input name="country" placeholder="Country" value={filters.country} onChange={handleFilterChange} />
                <input name="star" type="number" placeholder="Star" min="1" max="5" value={filters.star} onChange={handleFilterChange} />
                <button onClick={handleSearch}>Search</button>
            </div>

            {/* Hotel List */}
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
                                <button onClick={() => handleEditClick(hotel)} className={classes.editButton}>Edit</button>
                                <Link to={`/admin-rooms/${hotel.id}`}>
                                    <button className={classes.roomButton}>View Rooms</button>
                                </Link>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>

            {/* Edit Hotel Form */}
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
                        <input name="address" value={formData.address} onChange={handleInputChange} />

                        <label>Star Rating:</label>
                        <input type="number" name="star" min="1" max="5" value={formData.star} onChange={handleInputChange} />

                        <div className={classes.formActions}>
                            <button onClick={handleSave} className={classes.saveButton}>Save</button>
                            <button onClick={() => setEditingHotel(null)} className={classes.cancelButton}>Cancel</button>
                            <button onClick={handleDelete} className={classes.deleteButton}>Delete</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}