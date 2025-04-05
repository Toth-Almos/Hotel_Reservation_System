import { useEffect, useState } from "react";
import { getReviewsForHotel } from "../../services/ReviewService";
import PropTypes from 'prop-types';
import classes from "./hotelReviews.module.css";

export default function HotelReviews({ hotelId }) {
    const [reviews, setReviews] = useState([]);

    useEffect(() => {
        if (hotelId) {
            getReviewsForHotel(hotelId).then(setReviews).catch(console.error);
        }
    }, [hotelId]);

    if (!reviews.length) {
        return <p className={classes.noReviews}>No reviews yet for this hotel.</p>;
    }

    return (
        <div className={classes.reviewContainer}>
            <h2>Customer Reviews</h2>
            <ul className={classes.reviewList}>
                {reviews.map((review) => (
                    <li key={review.id} className={classes.reviewItem}>
                        <p className={classes.reviewer}><strong>{review.customerName}</strong></p>
                        <p className={classes.rating}>Rating: {Array(review.rating).fill("⭐").join("")}</p>
                        <p className={classes.comment}>{review.comment}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

HotelReviews.propTypes = {
    hotelId: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number
    ]).isRequired,
};