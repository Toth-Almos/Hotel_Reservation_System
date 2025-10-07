import { getFilteredHotels } from '../../services/HotelService';
import classes from './browserPage.module.css'
import { useState } from 'react';
import { Link } from 'react-router'

export default function BrowserPage() {
    const [hotels, setHotels] = useState();
    const [filters, setFilters] = useState({ name: '', country: '', star: '' });
    const [sortBy, setSortBy] = useState("name");
    const [sortDirection, setSortDirection] = useState("asc");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleSearch = async () => {
        try {
            const data = await getFilteredHotels(filters, 0, 10, sortBy, sortDirection);
            setHotels(data.content || []);
        } catch (error) {
            console.error("Error searching hotels:", error.message);
            setHotels([]);
        }
    };

    const handleReset = async () => {
        setFilters({ name: '', country: '', star: '' });
        setSortBy("name");
        setSortDirection("asc");
        const data = await getFilteredHotels({}, 0, 10, "name", "asc");
        setHotels(data.content || []);
    };

    return (
        <div className={classes.container}>
            <h2>Browser Page</h2>

            <div className={classes.searchSection}>
                <input name="name" placeholder="Name" value={filters.name} onChange={handleChange} />
                <input name="country" placeholder="Country" value={filters.country} onChange={handleChange} />
                <input name="star" type="number" placeholder="Star" min="1" max="5" value={filters.star} onChange={handleChange} />

                <select value={sortBy} onChange={(e) => setSortBy(e.target.value)}>
                    <option value="name">Name</option>
                    <option value="country">Country</option>
                    <option value="star">Star</option>
                </select>

                <select value={sortDirection} onChange={(e) => setSortDirection(e.target.value)}>
                    <option value="asc">Ascending</option>
                    <option value="desc">Descending</option>
                </select>

                <button onClick={handleSearch}>Search</button>
                <button onClick={handleReset}>Reset Filters</button>
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
        </div >
    )
}