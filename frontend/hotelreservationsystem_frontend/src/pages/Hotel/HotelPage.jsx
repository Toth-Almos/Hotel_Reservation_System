import { useParams } from 'react-router';
import classes from './hotelPage.module.css'
import { useEffect, useState } from 'react';
import { getById } from '../../services/HotelService';
import { useNavigate } from 'react-router';
import HotelReviews from '../../components/HotelReviews/HotelReviews';
import ReviewForm from '../../components/ReviewForm/ReviewForm';

export default function HotelPage() {
    const [hotel, setHotel] = useState();
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        getById(id).then(setHotel);
    }, [id])

    const handleBookRoom = () => {
        navigate(`/reserve/${id}`, { state: { hotel } });
    }

    if (!hotel) return <p>Loading...</p>;

    return (
        <div className={classes.container}>
            {/* Banner Section */}
            <div className={classes.banner}>
                <img src={`/hotels/hotel_default.jpg`} alt={hotel.name} />
            </div>

            {/* Hotel Details */}
            <div className={classes.details}>
                <h1>{hotel.name}</h1>
                <p className={classes.stars}>{Array(hotel.star).fill("⭐").join("")}</p>
                <p className={classes.location}>{hotel.country}, {hotel.city} {hotel.address}</p>
            </div>

            {/* Rooms Section */}
            <div className={classes.rooms}>
                <h2>Available Rooms</h2>
                {hotel.rooms && hotel.rooms.length > 0 ? (
                    <ul className={classes.roomList}>
                        {hotel.rooms.map((room) => (
                            <li key={room.id} className={classes.roomItem}>
                                <img src={`/rooms/room_${room.type.toLowerCase()}.jpg`} alt={room.name} className={classes.roomImage} />
                                <div className={classes.roomDetails}>
                                    <h3>{room.type}</h3>
                                    <p>Capacity: {room.maxGuests} guests</p>
                                    <p>Price: {room.pricePerNight}€ / night</p>
                                    <button className={classes.bookRoom} onClick={handleBookRoom}>Book Room</button>
                                </div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No available rooms at the moment.</p>
                )}
            </div>

            <div>
                <HotelReviews hotelId={id} />
            </div>
            <div>
                <ReviewForm hotelId={hotel.id} />
            </div>
        </div>
    )
}