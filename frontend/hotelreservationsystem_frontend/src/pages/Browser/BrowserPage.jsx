import { getAll } from '../../services/HotelService';
import classes from './browserPage.module.css'
import { useEffect, useState } from 'react';
import { Link } from 'react-router'

export default function BrowserPage() {
    const [hotels, setHotels] = useState();

    useEffect(() => {
        getAll().then(setHotels)
    }, []);

    return (
        <div className={classes.container}>
            <h2>Browser Page</h2>
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