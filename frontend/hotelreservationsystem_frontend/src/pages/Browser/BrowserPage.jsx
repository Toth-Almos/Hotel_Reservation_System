import { getFilteredHotels } from '../../services/HotelService';
import classes from './browserPage.module.css'
import { useState } from 'react';
import { Link } from 'react-router'

export default function BrowserPage() {
    const [hotels, setHotels] = useState();
    const [filters, setFilters] = useState({ name: '', country: '', star: '' });

    const handleChange = (e) => {
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

    return (
        <div className={classes.container}>
            <h2>Browser Page</h2>

            <div className={classes.searchSection}>
                <input name="name" placeholder="Name" value={filters.name} onChange={handleChange} />
                <input name="country" placeholder="Country" value={filters.country} onChange={handleChange} />
                <input name="star" type="number" placeholder="Star" min="1" max="5" value={filters.star} onChange={handleChange} />
                <button onClick={handleSearch}>Search</button>
            </div>

            <ul className={classes.list}>
                {hotels && hotels.map(hotel => (
                    <li key={hotel.id}>
                        <Link to={`/hotel/${hotel.id}`}>
                            <img className={classes.image} src={`/hotels/hotel_default.jpg`} alt={hotel.name} />

                            <div className={classes.content}>
                                <div className={classes["hotel-name-star"]}>
                                    <p>{hotel.name}</p>
                                    <p className={classes["hotel-star"]}>{Array(hotel.star).fill("⭐").join("")}</p>
                                </div>
                                <div className={classes["hotel-location"]}>
                                    <p>{hotel.country}</p>
                                    <p>{hotel.city}</p>
                                </div>
                            </div>
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    )
}